<?php
// 命令执行 - 纯子类继承调用测试文件
// 调用链：外部请求参数(cmd) → SubClassTest.php → VulnChild → VulnParent.execCommand

require_once __DIR__ . '/../common/VulnChild.php';
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Command Execution - Subclass Test</title>
</head>
<body>
    <h1>Command Execution - Subclass Test</h1>
    <p>Usage: ?cmd=whoami</p>
    <hr>
    <?php
    // 从外部可控数据源场景：通过 $_GET 接收前端传入参数
    $child = new VulnChild();
    if (isset($_GET['cmd']) && !empty($_GET['cmd'])) {
        // 调用链：SubClassTest.php → VulnChild.runCommand → VulnParent.execCommand（漏洞点）
        $child->runCommand($_GET['cmd']);
    } else {
        echo "<p>No cmd parameter provided. Running hardcoded test...</p>";
    }

    // 硬编码无外部数据源场景：内置固定参数触发
    $child->runCommandFixed();
    ?>
</body>
</html>
