use reqwest;
use ureq;
use std::io::Read;

pub struct CustomValue;

impl<'de> serde::Deserialize<'de> for CustomValue {
    fn deserialize<D>(deserializer: D) -> Result<Self, D::Error>
    where D: serde::Deserializer<'de> {
        let _ = <serde_json::Value as serde::Deserialize>::deserialize(deserializer)?;
        Ok(CustomValue)
    }
}

pub fn reqwest_blocking(reqwestBlockingInput: &str) -> Result<String, reqwest::Error> {
    let response = reqwest::blocking::get(reqwestBlockingInput)?;
    let reqwestBlockingOut = response.text()?;
    Ok(reqwestBlockingOut)
}

pub fn reqwest_blocking_bytes(reqwestBlockingBytesInput: &str) -> Result<Vec<u8>, reqwest::Error> {
    let response = reqwest::blocking::get(reqwestBlockingBytesInput)?;
    let reqwestBlockingBytesOut = response.bytes()?;
    Ok(reqwestBlockingBytesOut.to_vec())
}

pub fn reqwest_blocking_result(reqwestBlockingResultInput: &str) {
    let reqwestBlockingResultOut = reqwest::blocking::get(reqwestBlockingResultInput);
    drop(reqwestBlockingResultOut);
}

pub fn reqwest_blocking_unknown(reqwestBlockingUnknownInput: reqwest::blocking::Response) -> Result<String, reqwest::Error> {
    let reqwestBlockingUnknownOut = reqwestBlockingUnknownInput.text()?;
    Ok(reqwestBlockingUnknownOut)
}

pub async fn reqwest_bytes(reqwestBytesInput: &str) -> Result<Vec<u8>, reqwest::Error> {
    let response = reqwest::get(reqwestBytesInput).await?;
    let reqwestBytesOut = response.bytes().await?;
    Ok(reqwestBytesOut.to_vec())
}

pub async fn reqwest_json(reqwestJsonInput: &str) -> Result<serde_json::Value, reqwest::Error> {
    let response = reqwest::get(reqwestJsonInput).await?;
    let reqwestJsonOut = response.json::<serde_json::Value>().await?;
    Ok(reqwestJsonOut)
}

pub fn reqwest_blocking_json(reqwestBlockingJsonInput: &str) -> Result<serde_json::Value, reqwest::Error> {
    let response = reqwest::blocking::get(reqwestBlockingJsonInput)?;
    let reqwestBlockingJsonOut = response.json::<serde_json::Value>()?;
    Ok(reqwestBlockingJsonOut)
}

pub async fn reqwest_custom_json(reqwestCustomJsonInput: &str) -> Result<CustomValue, reqwest::Error> {
    let response = reqwest::get(reqwestCustomJsonInput).await?;
    let reqwestCustomJsonOut = response.json::<CustomValue>().await?;
    Ok(reqwestCustomJsonOut)
}

pub async fn reqwest_json_field(reqwestJsonFieldInput: &str) -> Result<serde_json::Value, reqwest::Error> {
    let response = reqwest::get(reqwestJsonFieldInput).await?;
    let reqwestJsonDecoded = response.json::<serde_json::Value>().await?;
    let reqwestJsonFieldOut = reqwestJsonDecoded["item"].clone();
    Ok(reqwestJsonFieldOut)
}

pub fn reqwest_pending(reqwestPendingInput: &str) {
    let reqwestPendingOut = reqwest::get(reqwestPendingInput);
    drop(reqwestPendingOut);
}

pub async fn reqwest_result(reqwestResultInput: &str) {
    let reqwestResultOut = reqwest::get(reqwestResultInput).await;
    drop(reqwestResultOut);
}

pub async fn reqwest_unknown(reqwestUnknownInput: reqwest::Response) -> Result<String, reqwest::Error> {
    let reqwestUnknownOut = reqwestUnknownInput.text().await?;
    Ok(reqwestUnknownOut)
}

pub fn ureq_result(ureqResultInput: &str) {
    let request = ureq::get(ureqResultInput);
    let ureqResultOut = request.call();
    drop(ureqResultOut);
}

pub fn ureq_unknown(ureqUnknownInput: ureq::Response) -> Result<String, std::io::Error> {
    let ureqUnknownOut = ureqUnknownInput.into_string()?;
    Ok(ureqUnknownOut)
}

pub fn ureq_reader(ureqReaderInput: &str) -> Result<Box<dyn std::io::Read + Send + Sync>, Box<dyn std::error::Error>> {
    let request = ureq::get(ureqReaderInput);
    let response = request.call()?;
    let ureqReaderOut = response.into_reader();
    Ok(ureqReaderOut)
}

pub fn ureq_unknown_reader(ureqUnknownReaderInput: ureq::Response) -> Box<dyn std::io::Read + Send + Sync> {
    let ureqUnknownReaderOut = ureqUnknownReaderInput.into_reader();
    ureqUnknownReaderOut
}

pub fn ureq_reader_chunk(ureqChunkInput: &str, ureqChunkOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let request = ureq::get(ureqChunkInput);
    let response = request.call()?;
    let mut ureqChunkReader = response.into_reader();
    let mut buffer = ureqChunkOld.to_vec();
    let ureqChunkBefore = buffer[0];
    let unrelated = vec![0u8; 32];
    let _ = ureqChunkReader.read(&mut buffer)?;
    let ureqChunkOut = buffer;
    let ureqChunkOther = unrelated;
    drop((ureqChunkBefore, ureqChunkOther));
    Ok(ureqChunkOut)
}

// ureq_reader_append preserves the existing buffer while appending the response body.
pub fn ureq_reader_append(ureqAppendInput: &str, ureqAppendOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqAppendReader = ureq::get(ureqAppendInput).call()?.into_reader();
    let mut buffer = ureqAppendOld.to_vec();
    let ureqAppendBefore = buffer[0];
    let unrelated = vec![0u8; 8];
    let _ = ureqAppendReader.read_to_end(&mut buffer)?;
    let ureqAppendOut = buffer;
    let ureqAppendOther = unrelated;
    drop((ureqAppendBefore, ureqAppendOther));
    Ok(ureqAppendOut)
}

pub fn ureq_reader_slice(ureqSliceInput: &str, ureqSliceOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let request = ureq::get(ureqSliceInput);
    let response = request.call()?;
    let mut ureqSliceReader = response.into_reader();
    let mut buffer = ureqSliceOld.to_vec();
    let _ = ureqSliceReader.read(&mut buffer[2..])?;
    let ureqSliceOut = buffer;
    Ok(ureqSliceOut)
}

pub fn ureq_reader_parentheses(ureqParenInput: &str, ureqParenOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqParenReader = ureq::get(ureqParenInput).call()?.into_reader();
    let mut buffer = ureqParenOld.to_vec();
    let _ = ureqParenReader.read((&mut (buffer)))?;
    let ureqParenOut = buffer;
    Ok(ureqParenOut)
}

pub fn ureq_reader_reborrow(ureqBorrowInput: &str, ureqBorrowOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqBorrowReader = ureq::get(ureqBorrowInput).call()?.into_reader();
    let mut buffer = ureqBorrowOld.to_vec();
    let alias = &mut buffer;
    let _ = ureqBorrowReader.read(&mut *alias)?;
    let ureqBorrowOut = buffer;
    Ok(ureqBorrowOut)
}

pub fn ureq_reader_borrow_argument(ureqBorrowArgInput: &str, ureqBorrowArgOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqBorrowArgReader = ureq::get(ureqBorrowArgInput).call()?.into_reader();
    let mut buffer = ureqBorrowArgOld.to_vec();
    let alias = &mut buffer;
    let _ = ureqBorrowArgReader.read(alias)?;
    let ureqBorrowArgOut = buffer;
    Ok(ureqBorrowArgOut)
}

pub fn ureq_reader_parameter(ureqParamReader: &mut dyn std::io::Read, ureqParamBuffer: &mut [u8]) {
    let _ = ureqParamReader.read(ureqParamBuffer);
    let ureqParamOut = ureqParamBuffer;
    let _ = ureqParamOut;
}

pub fn ureq_reader_forward(ureqForwardReader: &mut dyn std::io::Read, ureqForwardBuffer: &mut [u8]) {
    ureq_reader_parameter(ureqForwardReader, ureqForwardBuffer);
    let ureqForwardOut = ureqForwardBuffer;
    let _ = ureqForwardOut;
}

pub fn ureq_reader_slice_alias(ureqSliceAliasInput: &str, ureqSliceAliasOld: &[u8]) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqSliceAliasReader = ureq::get(ureqSliceAliasInput).call()?.into_reader();
    let mut buffer = ureqSliceAliasOld.to_vec();
    let ureqSliceAliasBefore = buffer[0];
    let alias = &mut buffer[2..];
    let _ = ureqSliceAliasReader.read(alias)?;
    let ureqSliceAliasOut = buffer;
    let _ = ureqSliceAliasBefore;
    Ok(ureqSliceAliasOut)
}

pub fn ureq_reader_conditional_slice(ureqConditionalInput: &str, ureqConditionalOld: &[u8], enabled: bool) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
    let mut ureqConditionalReader = ureq::get(ureqConditionalInput).call()?.into_reader();
    let mut buffer = ureqConditionalOld.to_vec();
    if enabled {
        let alias = &mut buffer[2..];
        let _ = ureqConditionalReader.read(alias)?;
    }
    let ureqConditionalOut = buffer;
    Ok(ureqConditionalOut)
}

pub fn ureq_reader_conditional_reassign(ureqChoiceInput: &str, enabled: bool) -> Result<(Vec<u8>, Vec<u8>), Box<dyn std::error::Error>> {
    let mut ureqChoiceReader = ureq::get(ureqChoiceInput).call()?.into_reader();
    let mut first = b"first-buffer-original-contents!".to_vec();
    let mut second = b"second-buffer-original-contents".to_vec();
    let unrelated = b"unrelated-buffer".to_vec();
    let mut alias = &mut first[1..];
    if enabled {
        alias = &mut second[2..];
    }
    let _ = ureqChoiceReader.read(alias)?;
    let ureqChoiceFirstOut = first;
    let ureqChoiceSecondOut = second;
    let ureqChoiceOther = unrelated;
    drop(ureqChoiceOther);
    Ok((ureqChoiceFirstOut, ureqChoiceSecondOut))
}

pub fn unknown_writable_target(unknownWritableReader: &mut dyn std::io::Read, mut target: Box<[u8]>) -> Box<[u8]> {
    let _ = unknownWritableReader.read(&mut *target);
    let unknownWritableOut = target;
    unknownWritableOut
}
