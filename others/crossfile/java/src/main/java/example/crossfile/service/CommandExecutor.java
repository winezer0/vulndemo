package example.crossfile.service;

// CommandExecutor abstracts command execution for interface-dispatch checks.
public interface CommandExecutor {
    void execute(String command) throws Exception;
}
