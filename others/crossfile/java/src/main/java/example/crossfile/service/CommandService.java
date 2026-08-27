package example.crossfile.service;

// CommandService contains common Java command execution sinks.
public class CommandService {
    public void executeRuntime(String command) throws Exception {
        Runtime.getRuntime().exec(command);
    }

    public void executeRuntimeThroughLocal(String command) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(command);
    }

    public void executeProcessBuilder(String command) throws Exception {
        new ProcessBuilder("sh", "-c", command).start();
    }
}
