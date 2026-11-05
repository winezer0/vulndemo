use reqwest;
use ureq;

pub async fn reqwest_body(reqwestInput: &str) -> Result<String, reqwest::Error> {
    let response = reqwest::get(reqwestInput).await?;
    let reqwestOut = response.text().await?;
    Ok(reqwestOut)
}

pub fn ureq_body(ureqInput: &str) -> Result<String, Box<dyn std::error::Error>> {
    let request = ureq::get(ureqInput);
    let response = request.call()?;
    let ureqOut = response.into_string()?;
    Ok(ureqOut)
}
