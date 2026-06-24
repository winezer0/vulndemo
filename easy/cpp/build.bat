@echo off
g++ main.cpp FuncCall.cpp common/VulnParent.cpp cmd_exec/InterfaceImpl.cpp -o vuln_cpp.exe
echo Build complete. Run vuln_cpp.exe to test.
