import Foundation

enum FuncCall {
    static func runByChild(_ command: String) {
        let child = VulnChild()
        child.exec(command)
    }

    static func runByInterface(_ command: String) {
        let runner: VulnRunner = InterfaceImpl()
        runner.run(command)
    }
}
