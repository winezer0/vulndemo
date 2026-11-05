use std::error::Error;
use std::io::{Read, Write};
use std::net::TcpListener;
use std::thread;
use std::time::Duration;

#[path = "../../../rust/clients.rs"]
mod clients;
#[path = "../../../rust/precision.rs"]
mod precision;

// serve returns fixed content through twenty-four independent loopback connections.
fn serve(listener: TcpListener) -> std::io::Result<()> {
    for connection in listener.incoming().take(24) {
        let mut connection = connection?;
        connection.set_read_timeout(Some(Duration::from_secs(5)))?;
        let mut request = [0; 4096];
        let read = connection.read(&mut request)?;
        let request = String::from_utf8_lossy(&request[..read]);
        let failure = request.contains(" /error ");
        let status = if failure { "500 Internal Server Error" } else { "201 Created" };
        let body = if request.contains(" /json ") { "{\"item\":\"fixed-rust-body\"}" } else { "fixed-rust-body" };
        write!(connection, "HTTP/1.1 {status}\r\nContent-Length: {}\r\nConnection: close\r\n\r\n{body}", body.len())?;
    }
    Ok(())
}

// main exercises actual Result, body, partial read and HTTP error semantics.
fn main() -> Result<(), Box<dyn Error>> {
    let listener = TcpListener::bind("127.0.0.1:0")?;
    let base = format!("http://{}", listener.local_addr()?);
    let server = thread::spawn(move || serve(listener));
    let client = reqwest::blocking::Client::builder().timeout(Duration::from_secs(5)).build()?;
    let response = client.get(format!("{base}/fixed")).send()?;
    assert_eq!(response.status(), 201);
    assert_eq!(response.text()?, "fixed-rust-body");
    let bytes = client.get(format!("{base}/fixed")).send()?.bytes()?;
    assert_eq!(bytes.as_ref(), b"fixed-rust-body");
    let response = ureq::get(&format!("{base}/fixed")).call()?;
    assert_eq!(response.into_string()?, "fixed-rust-body");
    let mut reader = ureq::get(&format!("{base}/fixed")).call()?.into_reader();
    let mut prefix = [0; 5];
    reader.read_exact(&mut prefix)?;
    assert_eq!(&prefix, b"fixed");
    let failed = client.get(format!("{base}/error")).send()?;
    assert!(failed.error_for_status().is_err());
    assert!(matches!(ureq::get(&format!("{base}/error")).call(), Err(ureq::Error::Status(500, _))));
    let pending = reqwest::get(format!("{base}/not-executed"));
    drop(pending);
    let runtime = tokio::runtime::Builder::new_current_thread().enable_all().build()?;
    let asynchronous: Result<String, reqwest::Error> = runtime.block_on(async {
        reqwest::get(format!("{base}/fixed")).await?.text().await
    });
    assert_eq!(asynchronous?, "fixed-rust-body");
    let async_bytes = runtime.block_on(async {
        reqwest::get(format!("{base}/fixed")).await?.bytes().await
    })?;
    assert_eq!(async_bytes.as_ref(), b"fixed-rust-body");
    let blocking_json: serde_json::Value = reqwest::blocking::get(format!("{base}/json"))?.json()?;
    assert_eq!(blocking_json["item"], "fixed-rust-body");
    let async_json: serde_json::Value = runtime.block_on(async {
        reqwest::get(format!("{base}/json")).await?.json().await
    })?;
    assert_eq!(async_json["item"], "fixed-rust-body");
    runtime.block_on(precision::reqwest_custom_json(&format!("{base}/json")))?;
    let selected = runtime.block_on(precision::reqwest_json_field(&format!("{base}/json")))?;
    assert_eq!(selected, "fixed-rust-body");
    let chunk = precision::ureq_reader_chunk(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?;
    assert_eq!(&chunk[..15], b"fixed-rust-body");
    assert_eq!(&chunk[15..], &b"previous-contents-are-retained!!"[15..]);
    let append = precision::ureq_reader_append(&format!("{base}/fixed"), b"old")?;
    assert_eq!(append, b"oldfixed-rust-body");
    let slice = precision::ureq_reader_slice(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?;
    assert_eq!(&slice[..2], b"pr");
    assert_eq!(&slice[2..17], b"fixed-rust-body");
    assert_eq!(&slice[17..], &b"previous-contents-are-retained!!"[17..]);
    for body in [
        precision::ureq_reader_parentheses(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?,
        precision::ureq_reader_reborrow(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?,
        precision::ureq_reader_borrow_argument(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?,
    ] {
        assert_eq!(&body[..15], b"fixed-rust-body");
        assert_eq!(&body[15..], &b"previous-contents-are-retained!!"[15..]);
    }
    let mut param_reader = ureq::get(&format!("{base}/fixed")).call()?.into_reader();
    let mut param_buffer = b"previous-contents-are-retained!!".to_vec();
    precision::ureq_reader_parameter(&mut param_reader, &mut param_buffer);
    assert_eq!(&param_buffer[..15], b"fixed-rust-body");
    assert_eq!(&param_buffer[15..], &b"previous-contents-are-retained!!"[15..]);
    let mut forward_reader = ureq::get(&format!("{base}/fixed")).call()?.into_reader();
    let mut forward_buffer = b"previous-contents-are-retained!!".to_vec();
    precision::ureq_reader_forward(&mut forward_reader, &mut forward_buffer);
    assert_eq!(&forward_buffer[..15], b"fixed-rust-body");
    assert_eq!(&forward_buffer[15..], &b"previous-contents-are-retained!!"[15..]);
    let slice_alias = precision::ureq_reader_slice_alias(&format!("{base}/fixed"), b"previous-contents-are-retained!!")?;
    assert_eq!(&slice_alias[..2], b"pr");
    assert_eq!(&slice_alias[2..17], b"fixed-rust-body");
    assert_eq!(&slice_alias[17..], &b"previous-contents-are-retained!!"[17..]);
    let conditional = precision::ureq_reader_conditional_slice(&format!("{base}/fixed"), b"previous-contents-are-retained!!", true)?;
    assert_eq!(&conditional[..2], b"pr");
    assert_eq!(&conditional[2..17], b"fixed-rust-body");
    assert_eq!(&conditional[17..], &b"previous-contents-are-retained!!"[17..]);
    let (first, second) = precision::ureq_reader_conditional_reassign(&format!("{base}/fixed"), false)?;
    assert_eq!(&first[1..16], b"fixed-rust-body");
    assert_eq!(second, b"second-buffer-original-contents");
    let (first, second) = precision::ureq_reader_conditional_reassign(&format!("{base}/fixed"), true)?;
    assert_eq!(first, b"first-buffer-original-contents!");
    assert_eq!(&second[2..17], b"fixed-rust-body");
    server.join().map_err(|_| "loopback server panicked")??;
    println!("reqwest=0.12.9; ureq=2.12.1; tokio=1.41.1; serde_json=1.0.154; cases=26; status=passed");
    Ok(())
}
