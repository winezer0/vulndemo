package demo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;
class RequestCache { String getHeader(String key) { return key; } }
class FakeRestTemplate { Object getForObject(String path, Class<?> type) { return null; } }
class Handler {
 Object run(HttpServletRequest request, RestTemplate restTemplate, RequestCache requestCache, FakeRestTemplate fakeRestTemplate) {
  String a = request.getHeader("X");
  String b = requestCache.getHeader("X");
  Object c = restTemplate.getForObject("/ok", Object.class);
  return fakeRestTemplate.getForObject("/bad", Object.class);
 }
}
