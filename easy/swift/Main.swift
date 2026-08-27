import Foundation

let command = CommandLine.arguments.dropFirst().first ?? "calc"
FuncCall.runByInterface(command)
