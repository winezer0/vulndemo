using Microsoft.Azure.Functions.Worker;

public class Functions {
    public string First([HttpTrigger(AuthorizationLevel.Function, "get", Route = "first")] object req) => ""; public string Second([HttpTrigger(AuthorizationLevel.Function, "post", Route = "second")] object req) => "";
}
