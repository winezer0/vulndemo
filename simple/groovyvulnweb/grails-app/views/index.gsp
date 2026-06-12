<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Groovy漏洞测试平台</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        h2 { color: #666; margin-top: 30px; }
        .section { margin-bottom: 30px; padding: 15px; border: 1px solid #ddd; border-radius: 5px; }
        .test-link { display: block; margin: 5px 0; padding: 8px; background: #f5f5f5; text-decoration: none; color: #333; border-radius: 3px; }
        .test-link:hover { background: #e0e0e0; }
        .vuln-type { color: #d32f2f; font-weight: bold; }
        .test-desc { color: #666; font-style: italic; margin-bottom: 10px; }
    </style>
</head>
<body>
    <h1>Groovy漏洞测试平台</h1>
    <p>本平台包含命令执行、代码执行、SSRF三类高危漏洞测试用例</p>
    
    <div class="section">
        <h2>一、命令执行漏洞测试 (Command Execution)</h2>
        <p class="test-desc">使用 String.execute() 或 Process 执行系统命令</p>
        
        <h3>1. 子类继承测试</h3>
        <a href="/cmdExec/subClassTest" class="test-link">子类继承测试 (外部参数)</a>
        
        <h3>2. 特质混入测试</h3>
        <a href="/cmdExec/traitImplTest" class="test-link">特质混入测试 (外部参数)</a>
        
        <h3>3. 直接调用测试</h3>
        <a href="/cmdExec/directTest" class="test-link">直接调用测试 (外部参数)</a>
    </div>
    
    <div class="section">
        <h2>二、代码执行漏洞测试 (Code Execution)</h2>
        <p class="test-desc">使用 Eval.me() 执行 Groovy 代码</p>
        
        <h3>1. 子类继承测试</h3>
        <a href="/codeExec/subClassTest" class="test-link">子类继承测试 (外部参数)</a>
        
        <h3>2. 特质混入测试</h3>
        <a href="/codeExec/traitImplTest" class="test-link">特质混入测试 (外部参数)</a>
        
        <h3>3. 直接调用测试</h3>
        <a href="/codeExec/directTest" class="test-link">直接调用测试 (外部参数)</a>
    </div>
    
    <div class="section">
        <h2>三、SSRF漏洞测试 (Server-Side Request Forgery)</h2>
        <p class="test-desc">使用 URL/HttpURLConnection 访问内部资源</p>
        
        <h3>1. 子类继承测试</h3>
        <a href="/ssrf/subClassTest" class="test-link">子类继承测试 (外部参数)</a>
        
        <h3>2. 特质混入测试</h3>
        <a href="/ssrf/traitImplTest" class="test-link">特质混入测试 (外部参数)</a>
        
        <h3>3. 直接调用测试</h3>
        <a href="/ssrf/directTest" class="test-link">直接调用测试 (外部参数)</a>
    </div>
    
    <div class="section">
        <h2>四、跨文件调用测试</h2>
        <p class="test-desc">测试跨文件函数调用、类实例调用等场景</p>
        <a href="/funcCall/test" class="test-link">跨文件调用测试</a>
    </div>
    
    <div class="section">
        <h2>五、测试说明</h2>
        <ul>
            <li><strong>外部参数测试</strong>：通过 URL 参数传入恶意数据，测试数据流跟踪能力</li>
            <li><strong>硬编码测试</strong>：漏洞点使用固定参数，测试静态分析能力</li>
            <li><strong>子类继承测试</strong>：通过类继承关系形成调用链</li>
            <li><strong>特质混入测试</strong>：通过 Groovy Trait 形成调用链</li>
            <li><strong>跨文件调用测试</strong>：通过跨文件函数/类调用形成调用链</li>
        </ul>
    </div>
</body>
</html>
