<?php
// 通用跨文件函数调用文件，提供跨文件调用的漏洞测试场景
// 包含：跨文件函数调用、跨文件类实例与方法调用、硬编码漏洞测试

require_once __DIR__ . '/common/VulnChild.php';

// 跨文件函数：通过调用子类 VulnChild 触发命令执行
function crossFileCommandExec($cmd)
{
    $child = new VulnChild();
    $child->runCommand($cmd);
}

// 跨文件函数：通过调用子类 VulnChild 触发代码执行
function crossFileCodeExec($code)
{
    $child = new VulnChild();
    $child->runCode($code);
}

// 跨文件函数：通过调用子类 VulnChild 触发 SSRF
function crossFileSsrf($url)
{
    $child = new VulnChild();
    $child->runFetch($url);
}

// 跨文件函数：硬编码触发所有漏洞类型（无外部数据源）
function crossFileHardcodedTest()
{
    $child = new VulnChild();
    $child->runCommandFixed();
    $child->runCodeFixed();
    $child->runFetchFixed();
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FuncCall - Cross File Function Test</title>
</head>
<body>
    <h1>FuncCall - Cross File Function Test</h1>
    <p>Usage for external data source test:</p>
    <ul>
        <li>Command Execution: ?mode=cmd&payload=whoami</li>
        <li>Code Execution: ?mode=code&payload=phpinfo();</li>
        <li>SSRF: ?mode=ssrf&payload=http://127.0.0.1/</li>
    </ul>
    <hr>
    <?php
    if (isset($_GET['mode']) && isset($_GET['payload']) && !empty($_GET['payload'])) {
        $mode = $_GET['mode'];
        $payload = $_GET['payload'];
        switch ($mode) {
            case 'cmd':
                crossFileCommandExec($payload);
                break;
            case 'code':
                crossFileCodeExec($payload);
                break;
            case 'ssrf':
                crossFileSsrf($payload);
                break;
            default:
                echo "<p>Invalid mode. Use cmd, code, or ssrf.</p>";
        }
    } else {
        echo "<p>No mode/payload provided. Running hardcoded tests...</p>";
    }

    echo "<hr><h2>Hardcoded Tests (No External Data Source)</h2>";
    crossFileHardcodedTest();
    ?>
</body>
</html>
