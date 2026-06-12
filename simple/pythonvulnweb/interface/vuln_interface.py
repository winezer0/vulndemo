from abc import ABC, abstractmethod

class VulnInterface(ABC):
    @abstractmethod
    def execute(self, param):
        # 抽象方法：子类必须实现，用于执行漏洞操作
        # 参数param可以是命令、代码或URL，根据漏洞类型不同
        pass
    
    @abstractmethod
    def hardcoded_execute(self):
        # 抽象方法：子类必须实现，用于执行硬编码漏洞操作
        pass