<?php
// 项目总入口 index.php，汇总所有测试访问链接
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PHP Vulnerability Demo - Main Entry</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 40px auto; padding: 20px; }
        h1 { color: #c0392b; border-bottom: 2px solid #c0392b; padding-bottom: 10px; }
        h2 { color: #2c3e50; margin-top: 30px; }
        .vuln-card { background: #f8f9fa; border-left: 4px solid #e74c3c; padding: 15px 20px; margin: 15px 0; border-radius: 4px; }
        .vuln-card h3 { margin: 0 0 10px 0; color: #e74c3c; }
        ul { line-height: 2; }
        a { color: #2980b9; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .note { background: #fff3cd; padding: 10px 15px; border-left: 4px solid #ffc107; margin: 20px 0; border-radius: 4px; }
        code { background: #2c3e50; color: #f1c40f; padding: 2px 6px; border-radius: 3px; font-family: Consolas, monospace; }
    </style>
</head>
<body>
    <h1>PHP Vulnerability Demo - Main Entry</h1>

    <div class="note">
        <strong>WARNING:</strong> This project contains intentional security vulnerabilities for security testing tools
        (SAST, DAST). Do not deploy this code in any production environment.
    </div>

    <h2>Project Directory Tree</h2>
    <pre>
phpvulnweb/
├─ index.php                # Main entry, links to all test cases
├─ FuncCall.php             # Cross-file function call test
├─ common/
│  ├─ VulnParent.php        # Common parent class, all vulnerability methods
│  └─ VulnChild.php         # Child class, inherits VulnParent
├─ interface/
│  └─ VulnInterface.php     # Interface definition
├─ cmd_exec/                # Command Execution
│  ├─ SubClassTest.php      # Subclass inheritance test
│  └─ InterfaceImpl.php     # Interface implementation test
├─ code_exec/               # Code Execution
│  ├─ SubClassTest.php      # Subclass inheritance test
│  └─ InterfaceImpl.php     # Interface implementation test
└─ ssrf/                    # SSRF
   ├─ SubClassTest.php      # Subclass inheritance test
   └─ InterfaceImpl.php     # Interface implementation test
    </pre>

    <h2>Command Execution (cmd_exec)</h2>
    <div class="vuln-card">
        <h3>Command Execution Vulnerability</h3>
        <ul>
            <li><a href="cmd_exec/SubClassTest.php?cmd=whoami">cmd_exec/SubClassTest.php?cmd=whoami</a> - Subclass test (external data)</li>
            <li><a href="cmd_exec/SubClassTest.php">cmd_exec/SubClassTest.php</a> - Subclass test (hardcoded only)</li>
            <li><a href="cmd_exec/InterfaceImpl.php?cmd=whoami">cmd_exec/InterfaceImpl.php?cmd=whoami</a> - Interface implementation test (external data)</li>
            <li><a href="cmd_exec/InterfaceImpl.php">cmd_exec/InterfaceImpl.php</a> - Interface implementation test (hardcoded only)</li>
        </ul>
    </div>

    <h2>Code Execution (code_exec)</h2>
    <div class="vuln-card">
        <h3>Code Execution Vulnerability</h3>
        <ul>
            <li><a href="code_exec/SubClassTest.php?code=phpinfo();">code_exec/SubClassTest.php?code=phpinfo();</a> - Subclass test (external data)</li>
            <li><a href="code_exec/SubClassTest.php">code_exec/SubClassTest.php</a> - Subclass test (hardcoded only)</li>
            <li><a href="code_exec/InterfaceImpl.php?code=phpinfo();">code_exec/InterfaceImpl.php?code=phpinfo();</a> - Interface implementation test (external data)</li>
            <li><a href="code_exec/InterfaceImpl.php">code_exec/InterfaceImpl.php</a> - Interface implementation test (hardcoded only)</li>
        </ul>
    </div>

    <h2>SSRF (ssrf)</h2>
    <div class="vuln-card">
        <h3>SSRF Vulnerability</h3>
        <ul>
            <li><a href="ssrf/SubClassTest.php?url=http://127.0.0.1/">ssrf/SubClassTest.php?url=http://127.0.0.1/</a> - Subclass test (external data)</li>
            <li><a href="ssrf/SubClassTest.php">ssrf/SubClassTest.php</a> - Subclass test (hardcoded only)</li>
            <li><a href="ssrf/InterfaceImpl.php?url=http://127.0.0.1/">ssrf/InterfaceImpl.php?url=http://127.0.0.1/</a> - Interface implementation test (external data)</li>
            <li><a href="ssrf/InterfaceImpl.php">ssrf/InterfaceImpl.php</a> - Interface implementation test (hardcoded only)</li>
        </ul>
    </div>

    <h2>Cross File Function Call (FuncCall)</h2>
    <div class="vuln-card">
        <h3>Cross-File Function Test</h3>
        <ul>
            <li><a href="FuncCall.php?mode=cmd&payload=whoami">FuncCall.php?mode=cmd&payload=whoami</a> - Cross-file command execution</li>
            <li><a href="FuncCall.php?mode=code&payload=phpinfo();">FuncCall.php?mode=code&payload=phpinfo();</a> - Cross-file code execution</li>
            <li><a href="FuncCall.php?mode=ssrf&payload=http://127.0.0.1/">FuncCall.php?mode=ssrf&payload=http://127.0.0.1/</a> - Cross-file SSRF</li>
            <li><a href="FuncCall.php">FuncCall.php</a> - Cross-file hardcoded test (no external data)</li>
        </ul>
    </div>

    <h2>Call Chain Summary</h2>
    <div class="vuln-card">
        <h3>Multi-Layer Call Chains</h3>
        <ul>
            <li><strong>Subclass chain:</strong> External $_GET → SubClassTest.php → VulnChild.method → VulnParent.{vulnMethod}</li>
            <li><strong>Interface chain:</strong> External $_GET → InterfaceImpl.php → {ImplClass}.execute → VulnParent.{vulnMethod}</li>
            <li><strong>Cross-file chain:</strong> External $_GET → FuncCall.php → crossFile{Xxx}Func → VulnChild.method → VulnParent.{vulnMethod}</li>
            <li><strong>Hardcoded chain:</strong> (No external data) → Entry file → Class method → VulnParent.{vulnMethod}</li>
        </ul>
    </div>
</body>
</html>
