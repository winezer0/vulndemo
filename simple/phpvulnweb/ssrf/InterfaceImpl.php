<?php
// SSRF - 接口实现类调用测试文件
// 调用链：外部请求参数(url) → InterfaceImpl.php → VulnInterface 实现类 → VulnParent.fetchUrl

require_once __DIR__ . '/../common/VulnParent.php';
require_once __DIR__ . '/../interface/VulnInterface.php';

// SSRF 接口实现类，同时继承 VulnParent，实现「接口 + 类继承」组合调用
class SsrfImpl extends VulnParent implements VulnInterface
{
    // 实现接口 execute 方法：接收外部可控参数 $payload 触发 SSRF
    public function execute($payload)
    {
        $this->fetchUrl($payload);
    }

    // 实现接口 executeFixed 方法：内置固定参数触发 SSRF
    public function executeFixed()
    {
        $this->fetchUrlHardcoded();
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SSRF - Interface Implementation Test</title>
</head>
<body>
    <h1>SSRF - Interface Implementation Test</h1>
    <p>Usage: ?url=http://127.0.0.1/</p>
    <hr>
    <?php
    $impl = new SsrfImpl();
    if (isset($_GET['url']) && !empty($_GET['url'])) {
        // 调用链：InterfaceImpl.php → SsrfImpl.execute → VulnParent.fetchUrl（漏洞点）
        $impl->execute($_GET['url']);
    } else {
        echo "<p>No url parameter provided. Running hardcoded test...</p>";
    }
    $impl->executeFixed();
    ?>
</body>
</html>
