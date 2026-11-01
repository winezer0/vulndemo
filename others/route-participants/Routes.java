import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class Routes {
    @GetMapping("/java")
    public String serve() { return "ok"; }
}
