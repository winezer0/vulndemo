import os

def vuln_func_call(cmd):
    # 跨文件函数调用：从app.py调用此函数，执行命令执行漏洞
    # 漏洞点：os.system直接执行用户控制的cmd参数
    # 调用链：request.args -> app.py路由 -> vuln_func_call -> os.system
    result = os.system(cmd)  # 漏洞：命令执行
    return f"Command executed with return code: {result}"

def hardcoded_vuln():
    # 硬编码漏洞场景：无外部数据源，内置固定命令
    # 漏洞点：os.system执行固定字符串，但可能被误用为模板
    hardcoded_cmd = "echo hardcoded_command"
    result = os.system(hardcoded_cmd)  # 漏洞：命令执行（硬编码）
    return f"Hardcoded command executed with return code: {result}"

def code_exec_vuln(code):
    # 代码执行漏洞函数，供跨文件调用
    # 漏洞点：eval执行用户控制的code参数
    try:
        output = eval(code)  # 漏洞：代码执行
        return str(output)
    except Exception as e:
        return f"Error: {str(e)}"

def ssrf_vuln(url):
    # SSRF漏洞函数，供跨文件调用
    # 漏洞点：requests.get获取用户控制的url
    try:
        import requests
        response = requests.get(url, timeout=5)  # 漏洞：SSRF
        return response.text[:500] + "..." if len(response.text) > 500 else response.text
    except Exception as e:
        return f"Error: {str(e)}"