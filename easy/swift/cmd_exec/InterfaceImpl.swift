final class InterfaceImpl: VulnChild, VulnRunner {
    func run(_ command: String) {
        exec(command)
    }
}
