<?php
// 代码执行 - 纯子类继承调用测试文件
// 调用链：外部请求参数(code) → SubClassTest.php → VulnChild → VulnParent.execCode

require_once __DIR__ . '/../common/VulnChild.php';
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Code Execution - Subclass Test</title>
</head>
<body>
    <h1>Code Execution - Subclass Test</h1>
    <p>Usage: ?code=phpinfo();</p>
    <hr>
    <?php
    // 从外部可控数据源场景：通过 $_GET 接收前端传入参数
    $child = new VulnChild();
    if (isset($_GET['code']) && !empty($_GET['code'])) {
        // 调用链：SubClassTest.php → VulnChild.runCode → VulnParent.execCode（漏洞点）
        $child->runCode($_GET['code']);
    } else {
        echo "<p>No code parameter provided. Running hardcoded test...</p>";
    }

    // 硬编码无外部数据源场景：内置固定参数触发
    $child->runCodeFixed();
    ?>
</body>
</html>
