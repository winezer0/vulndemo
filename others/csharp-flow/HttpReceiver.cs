class Fake { public void GetAsync(string value) {} }
class HttpEvidence {
    void Run(Fake fake, System.Net.Http.HttpClient client, string url) {
        fake.GetAsync(url);
        client.GetAsync(url);
    }
}
class FakeQuery { public void RunQuery(string value) {} public void Query(string value) {} }
class HttpRequest { public void Query(string value) {} }
class QueryEvidence { void Run(FakeQuery fake, HttpRequest request, string key) {
    fake.RunQuery(key);
    fake.Query(key);
    request.Query(key);
} }
class FakeResponse { public void WriteAsync(string value) {} public void Ok(string value) {} }
class HttpResponse { public void WriteAsync(string value) {} }
class ControllerBase { public void Ok(string value) {} }
class ResponseEvidence { void Run(FakeResponse fake, HttpResponse response, ControllerBase controller, string value) {
    fake.WriteAsync(value);
    fake.Ok(value);
    response.WriteAsync(value);
    controller.Ok(value);
} }
class FakeBinder { public void Bind(string value) {} public void TryParse(string value) {} }
class ModelBinder { public void Bind(string value) {} }
class BindingEvidence { void Run(FakeBinder fake, ModelBinder binder, string value) {
    fake.Bind(value);
    fake.TryParse(value);
    binder.Bind(value);
} }
class PlainInline { public void Ok(string value) {} public void Run(string value) { Ok(value); } }
class Controller { public void Ok(string value) {} public void Run(string value) { Ok(value); } }
