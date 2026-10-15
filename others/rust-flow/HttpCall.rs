fn run(url: &str) {
    reqwest::get(url);
    fake::get(url);
    queryLogger.get(url);
    pathfinder.get(url);
    jsonClient.send(url);
    reqwestClient.get(url);
}
