@echo off
gcc main.c func_call.c common/vuln_parent.c cmd_exec/interface_impl.c -o vuln_c.exe
echo Build complete. Run vuln_c.exe to test.
