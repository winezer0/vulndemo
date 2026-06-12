<?php
// 命令执行 - 接口实现类调用测试文件
// 调用链：外部请求参数(cmd) → InterfaceImpl.php → VulnInterface 实现类 → VulnParent.execCommand

require_once __DIR__ . '/../common/VulnParent.php';
require_once __DIR__ . '/../interface/VulnInterface.php';

// 命令执行接口实现类，同时继承 VulnParent，实现「接口 + 类继承」组合调用
class CmdExecImpl extends VulnParent implements VulnInterface
{
    // 实现接口 execute 方法：接收外部可控参数 $payload 触发命令执行
    public function execute($payload)
    {
        $this->execCommand($payload);
    }

    // 实现接口 executeFixed 方法：内置固定参数触发命令执行
    public function executeFixed()
    {
        $this->execCommandHardcoded();
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Command Execution - Interface Implementation Test</title>
</head>
<body>
    <h1>Command Execution - Interface Implementation Test</h1>
    <p>Usage: ?cmd=whoami</p>
    <hr>
    <?php
    $impl = new CmdExecImpl();
    if (isset($_GET['cmd']) && !empty($_GET['cmd'])) {
        // 调用链：InterfaceImpl.php → CmdExecImpl.execute → VulnParent.execCommand（漏洞点）
        $impl->execute($_GET['cmd']);
    } else {
        echo "<p>No cmd parameter provided. Running hardcoded test...</p>";
    }
    $impl->executeFixed();
    ?>
</body>
</html>
