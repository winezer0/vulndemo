<?php

// 漏洞统一接口定义，所有接口实现类必须实现以下方法
interface VulnInterface
{
    // 外部可控参数调用，参数 $payload 为外部传入的恶意载荷
    public function execute($payload);

    // 硬编码无外部数据源调用，内部固定参数触发漏洞
    public function executeFixed();
}
