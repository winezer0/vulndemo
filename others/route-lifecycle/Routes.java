import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

class Routes {
    @GetMapping("/java")
    public String users() { return "ok"; }
}

class Config {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Auth());
    }
}
