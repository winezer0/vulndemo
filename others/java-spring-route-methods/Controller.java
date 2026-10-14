import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api")
class Controller {
    @RequestMapping(path = "/items/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    String handle() { return "ok"; }

    @RequestMapping(path = DYNAMIC_PATH, method = RequestMethod.GET)
    String dynamic() { return "unknown"; }
}
