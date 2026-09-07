export function runCommand(command: string) {
  return exec(command);
}

export class Runner {
  execute(command: string) {
    return command;
  }
}

export function runTyped(runner: Runner, command: string) {
  return runner.execute(command);
}
