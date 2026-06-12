<?php
// 代码执行 - 接口实现类调用测试文件
// 调用链：外部请求参数(code) → InterfaceImpl.php → VulnInterface 实现类 → VulnParent.execCode

require_once __DIR__ . '/../common/VulnParent.php';
require_once __DIR__ . '/../interface/VulnInterface.php';

// 代码执行接口实现类，同时继承 VulnParent，实现「接口 + 类继承」组合调用
class CodeExecImpl extends VulnParent implements VulnInterface
{
    // 实现接口 execute 方法：接收外部可控参数 $payload 触发代码执行
    public function execute($payload)
    {
        $this->execCode($payload);
    }

    // 实现接口 executeFixed 方法：内置固定参数触发代码执行
    public function executeFixed()
    {
        $this->execCodeHardcoded();
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Code Execution - Interface Implementation Test</title>
</head>
<body>
    <h1>Code Execution - Interface Implementation Test</h1>
    <p>Usage: ?code=phpinfo();</p>
    <hr>
    <?php
    $impl = new CodeExecImpl();
    if (isset($_GET['code']) && !empty($_GET['code'])) {
        // 调用链：InterfaceImpl.php → CodeExecImpl.execute → VulnParent.execCode（漏洞点）
        $impl->execute($_GET['code']);
    } else {
        echo "<p>No code parameter provided. Running hardcoded test...</p>";
    }
    $impl->executeFixed();
    ?>
</body>
</html>
