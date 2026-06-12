package testdata.java.cmd_exec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testdata.java.FuncCall;

@RestController
public class CmdController {
    private final FuncCall funcCall = new FuncCall();

    @GetMapping("/exec")
    public String exec(@RequestParam String cmd, @RequestParam(defaultValue = "child") String mode) {
        if ("impl".equals(mode)) {
            return funcCall.runByInterface(cmd);
        }
        return funcCall.runByChild(cmd);
    }
}
