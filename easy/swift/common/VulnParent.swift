import Foundation

class VulnParent {
    func exec(_ command: String) {
        let process = Process()
        process.executableURL = URL(fileURLWithPath: "/bin/sh")
        process.arguments = ["-c", command]
        try? process.run()
    }
}
