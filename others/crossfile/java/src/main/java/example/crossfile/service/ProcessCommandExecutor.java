package example.crossfile.service;

// ProcessCommandExecutor implements the interface with ProcessBuilder.
public class ProcessCommandExecutor implements CommandExecutor {
    @Override
    public void execute(String command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("sh", "-c", command);
        builder.start();
    }
}
