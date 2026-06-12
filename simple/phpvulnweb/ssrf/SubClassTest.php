<?php
// SSRF - 纯子类继承调用测试文件
// 调用链：外部请求参数(url) → SubClassTest.php → VulnChild → VulnParent.fetchUrl

require_once __DIR__ . '/../common/VulnChild.php';
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SSRF - Subclass Test</title>
</head>
<body>
    <h1>SSRF - Subclass Test</h1>
    <p>Usage: ?url=http://127.0.0.1/</p>
    <hr>
    <?php
    // 从外部可控数据源场景：通过 $_GET 接收前端传入参数
    $child = new VulnChild();
    if (isset($_GET['url']) && !empty($_GET['url'])) {
        // 调用链：SubClassTest.php → VulnChild.runFetch → VulnParent.fetchUrl（漏洞点）
        $child->runFetch($_GET['url']);
    } else {
        echo "<p>No url parameter provided. Running hardcoded test...</p>";
    }

    // 硬编码无外部数据源场景：内置固定参数触发
    $child->runFetchFixed();
    ?>
</body>
</html>
