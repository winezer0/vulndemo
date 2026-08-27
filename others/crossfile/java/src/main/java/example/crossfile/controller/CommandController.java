package example.crossfile.controller;

import example.crossfile.input.RequestInput;
import example.crossfile.service.CommandExecutor;
import example.crossfile.service.CommandService;
import example.crossfile.service.ProcessCommandExecutor;

// CommandController models a common Java web entry point.
public class CommandController {
    private final CommandService service = new CommandService();

    public void handle(String rawInput) throws Exception {
        String command = RequestInput.fromRequest(rawInput);
        service.executeRuntime(command);
        service.executeRuntimeThroughLocal(command);
        service.executeProcessBuilder(command);

        CommandExecutor executor = new ProcessCommandExecutor();
        executor.execute(command);
    }
}
