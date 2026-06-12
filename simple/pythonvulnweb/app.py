from flask import Flask, request
from cmd_exec.sub_class_test import CmdExecSubClassTest
from cmd_exec.interface_impl import CmdExecInterfaceImpl
from code_exec.sub_class_test import CodeExecSubClassTest
from code_exec.interface_impl import CodeExecInterfaceImpl
from ssrf.sub_class_test import SsrfSubClassTest
from ssrf.interface_impl import SsrfInterfaceImpl
from func_call import vuln_func_call, hardcoded_vuln, code_exec_vuln, ssrf_vuln

app = Flask(__name__)

@app.route('/')
def index():
    return '''
    <h1>Python Vulnerability Test Dashboard</h1>
    <h2>命令执行漏洞 (Command Execution)</h2>
    <ul>
        <li><a href="/cmd_exec/sub_class?cmd=whoami">SubClass Test (外部参数)</a></li>
        <li><a href="/cmd_exec/sub_class/hardcoded">SubClass Test (硬编码)</a></li>
        <li><a href="/cmd_exec/interface_impl?cmd=whoami">Interface Impl Test (外部参数)</a></li>
        <li><a href="/cmd_exec/interface_impl/hardcoded">Interface Impl Test (硬编码)</a></li>
    </ul>
    <h2>代码执行漏洞 (Code Execution)</h2>
    <ul>
        <li><a href="/code_exec/sub_class?code=__import__('os').system('whoami')">SubClass Test (外部参数)</a></li>
        <li><a href="/code_exec/sub_class/hardcoded">SubClass Test (硬编码)</a></li>
        <li><a href="/code_exec/interface_impl?code=__import__('os').system('whoami')">Interface Impl Test (外部参数)</a></li>
        <li><a href="/code_exec/interface_impl/hardcoded">Interface Impl Test (硬编码)</a></li>
    </ul>
    <h2>SSRF漏洞 (Server-Side Request Forgery)</h2>
    <ul>
        <li><a href="/ssrf/sub_class?url=http://127.0.0.1">SubClass Test (外部参数)</a></li>
        <li><a href="/ssrf/sub_class/hardcoded">SubClass Test (硬编码)</a></li>
        <li><a href="/ssrf/interface_impl?url=http://127.0.0.1">Interface Impl Test (外部参数)</a></li>
        <li><a href="/ssrf/interface_impl/hardcoded">Interface Impl Test (硬编码)</a></li>
    </ul>
    <h2>跨文件函数调用 &amp; 硬编码漏洞</h2>
    <ul>
        <li><a href="/func_call?cmd=whoami">FuncCall - 命令执行 (外部参数)</a></li>
        <li><a href="/func_call/code?code=1+1">FuncCall - 代码执行 (外部参数)</a></li>
        <li><a href="/func_call/ssrf?url=http://127.0.0.1">FuncCall - SSRF (外部参数)</a></li>
        <li><a href="/hardcoded/cmd">Hardcoded - 命令执行</a></li>
        <li><a href="/hardcoded/code">Hardcoded - 代码执行</a></li>
        <li><a href="/hardcoded/ssrf">Hardcoded - SSRF</a></li>
    </ul>
    '''

@app.route('/cmd_exec/sub_class')
def cmd_exec_sub_class():
    cmd = request.args.get('cmd', 'echo default')
    # 漏洞调用链：请求参数 -> 路由 -> VulnChild子类 -> VulnParent -> os.system
    tester = CmdExecSubClassTest()
    result = tester.execute(cmd)
    return f'<pre>{result}</pre>'

@app.route('/cmd_exec/sub_class/hardcoded')
def cmd_exec_sub_class_hardcoded():
    # 硬编码漏洞调用链：路由 -> VulnChild子类 -> VulnParent.hardcoded_cmd_exec -> os.system
    tester = CmdExecSubClassTest()
    result = tester.hardcoded_test()
    return f'<pre>{result}</pre>'

@app.route('/cmd_exec/interface_impl')
def cmd_exec_interface_impl():
    cmd = request.args.get('cmd', 'echo default')
    # 漏洞调用链：请求参数 -> 路由 -> 接口实现类 -> VulnParent -> os.system
    tester = CmdExecInterfaceImpl()
    result = tester.execute(cmd)
    return f'<pre>{result}</pre>'

@app.route('/cmd_exec/interface_impl/hardcoded')
def cmd_exec_interface_impl_hardcoded():
    # 硬编码漏洞调用链：路由 -> 接口实现类 -> VulnParent.hardcoded_cmd_exec -> os.system
    tester = CmdExecInterfaceImpl()
    result = tester.hardcoded_execute()
    return f'<pre>{result}</pre>'

@app.route('/code_exec/sub_class')
def code_exec_sub_class():
    code = request.args.get('code', 'print("default")')
    # 漏洞调用链：请求参数 -> 路由 -> VulnChild子类 -> VulnParent -> eval
    tester = CodeExecSubClassTest()
    result = tester.execute(code)
    return f'<pre>{result}</pre>'

@app.route('/code_exec/sub_class/hardcoded')
def code_exec_sub_class_hardcoded():
    # 硬编码漏洞调用链：路由 -> VulnChild子类 -> VulnParent.hardcoded_code_exec -> eval
    tester = CodeExecSubClassTest()
    result = tester.hardcoded_test()
    return f'<pre>{result}</pre>'

@app.route('/code_exec/interface_impl')
def code_exec_interface_impl():
    code = request.args.get('code', 'print("default")')
    # 漏洞调用链：请求参数 -> 路由 -> 接口实现类 -> VulnParent -> eval
    tester = CodeExecInterfaceImpl()
    result = tester.execute(code)
    return f'<pre>{result}</pre>'

@app.route('/code_exec/interface_impl/hardcoded')
def code_exec_interface_impl_hardcoded():
    # 硬编码漏洞调用链：路由 -> 接口实现类 -> VulnParent.hardcoded_code_exec -> eval
    tester = CodeExecInterfaceImpl()
    result = tester.hardcoded_execute()
    return f'<pre>{result}</pre>'

@app.route('/ssrf/sub_class')
def ssrf_sub_class():
    url = request.args.get('url', 'http://127.0.0.1')
    # 漏洞调用链：请求参数 -> 路由 -> VulnChild子类 -> VulnParent -> requests.get
    tester = SsrfSubClassTest()
    result = tester.execute(url)
    return f'<pre>{result}</pre>'

@app.route('/ssrf/sub_class/hardcoded')
def ssrf_sub_class_hardcoded():
    # 硬编码漏洞调用链：路由 -> VulnChild子类 -> VulnParent.hardcoded_ssrf -> requests.get
    tester = SsrfSubClassTest()
    result = tester.hardcoded_test()
    return f'<pre>{result}</pre>'

@app.route('/ssrf/interface_impl')
def ssrf_interface_impl():
    url = request.args.get('url', 'http://127.0.0.1')
    # 漏洞调用链：请求参数 -> 路由 -> 接口实现类 -> VulnParent -> requests.get
    tester = SsrfInterfaceImpl()
    result = tester.execute(url)
    return f'<pre>{result}</pre>'

@app.route('/ssrf/interface_impl/hardcoded')
def ssrf_interface_impl_hardcoded():
    # 硬编码漏洞调用链：路由 -> 接口实现类 -> VulnParent.hardcoded_ssrf -> requests.get
    tester = SsrfInterfaceImpl()
    result = tester.hardcoded_execute()
    return f'<pre>{result}</pre>'

@app.route('/func_call')
def func_call_route():
    cmd = request.args.get('cmd', 'echo default')
    # 跨文件函数调用：请求参数 -> 路由 -> func_call.vuln_func_call -> os.system
    result = vuln_func_call(cmd)
    return f'<pre>{result}</pre>'

@app.route('/func_call/code')
def func_call_code_route():
    code = request.args.get('code', 'print("default")')
    # 跨文件函数调用：请求参数 -> 路由 -> func_call.code_exec_vuln -> eval
    result = code_exec_vuln(code)
    return f'<pre>{result}</pre>'

@app.route('/func_call/ssrf')
def func_call_ssrf_route():
    url = request.args.get('url', 'http://127.0.0.1')
    # 跨文件函数调用：请求参数 -> 路由 -> func_call.ssrf_vuln -> requests.get
    result = ssrf_vuln(url)
    return f'<pre>{result}</pre>'

@app.route('/hardcoded/cmd')
def hardcoded_cmd_route():
    # 硬编码命令执行漏洞：路由 -> func_call.hardcoded_vuln -> os.system
    result = hardcoded_vuln()
    return f'<pre>{result}</pre>'

@app.route('/hardcoded/code')
def hardcoded_code_route():
    # 硬编码代码执行漏洞：路由 -> VulnParent.hardcoded_code_exec -> eval
    from common.vuln_parent import VulnParent
    parent = VulnParent()
    result = parent.hardcoded_code_exec()
    return f'<pre>{result}</pre>'

@app.route('/hardcoded/ssrf')
def hardcoded_ssrf_route():
    # 硬编码SSRF漏洞：路由 -> VulnParent.hardcoded_ssrf -> requests.get
    from common.vuln_parent import VulnParent
    parent = VulnParent()
    result = parent.hardcoded_ssrf()
    return f'<pre>{result}</pre>'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)