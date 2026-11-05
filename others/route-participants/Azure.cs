using Microsoft.Azure.Functions.Worker;

class Functions {
    public string Serve([HttpTrigger(AuthorizationLevel.Function, "get", Route = "azure")] object req) => "ok";
}
