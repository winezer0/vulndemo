def run_command(command: str):
    return system(command)


class Runner:
    def execute(self, command: str):
        return command


def run_typed(runner: Runner, command: str):
    return runner.execute(command)
