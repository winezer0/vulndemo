import os
import sys

class VulnParent:
    def cmd_exec(self, cmd):
        # 命令执行漏洞方法：父类提供核心漏洞逻辑
        # 漏洞点：os.system直接执行用户控制的cmd参数
        # 调用链：子类/接口实现 -> 父类方法 -> os.system
        result = os.system(cmd)  # 漏洞：命令执行
        return f"Command executed with return code: {result}"
    
    def code_exec(self, code):
        # 代码执行漏洞方法：父类提供核心漏洞逻辑
        # 漏洞点：eval执行用户控制的code参数
        # 调用链：子类/接口实现 -> 父类方法 -> eval
        try:
            output = eval(code)  # 漏洞：代码执行
            return str(output)
        except Exception as e:
            return f"Error: {str(e)}"
    
    def ssrf(self, url):
        # SSRF漏洞方法：父类提供核心漏洞逻辑
        # 漏洞点：requests.get获取用户控制的url
        # 调用链：子类/接口实现 -> 父类方法 -> requests.get
        try:
            import requests
            response = requests.get(url, timeout=5)  # 漏洞：SSRF
            return response.text[:500] + "..." if len(response.text) > 500 else response.text
        except Exception as e:
            return f"Error: {str(e)}"
    
    def hardcoded_cmd_exec(self):
        # 硬编码命令执行漏洞：无外部参数，固定命令
        # 漏洞点：os.system执行固定字符串
        hardcoded_cmd = "echo hardcoded_parent_command"
        result = os.system(hardcoded_cmd)  # 漏洞：命令执行（硬编码）
        return f"Hardcoded command executed with return code: {result}"
    
    def hardcoded_code_exec(self):
        # 硬编码代码执行漏洞：无外部参数，固定代码
        # 漏洞点：eval执行固定字符串
        hardcoded_code = 'print("hardcoded code execution")'
        try:
            output = eval(hardcoded_code)  # 漏洞：代码执行（硬编码）
            return str(output)
        except Exception as e:
            return f"Error: {str(e)}"
    
    def hardcoded_ssrf(self):
        # 硬编码SSRF漏洞：无外部参数，固定URL
        # 漏洞点：requests.get获取固定URL
        hardcoded_url = "http://127.0.0.1"
        try:
            import requests
            response = requests.get(hardcoded_url, timeout=5)  # 漏洞：SSRF（硬编码）
            return response.text[:500] + "..." if len(response.text) > 500 else response.text
        except Exception as e:
            return f"Error: {str(e)}"