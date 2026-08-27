"""pyfulldemo.py — Python 解析器全功能演示"""
from typing import Optional, List
import os
import sys

# ========== 1. 函数定义 ==========
def greet(name: str) -> str:
    return f"Hello, {name}!"

def add(a: int, b: int = 0) -> int:
    return a + b

# ========== 2. 类定义 + 继承 + 方法 ==========
class BaseRunner:
    def run(self, cmd: str) -> str:
        return f"base: {cmd}"

class AdvancedRunner(BaseRunner):
    def __init__(self, prefix: str) -> None:
        self.prefix = prefix

    def run(self, cmd: str) -> str:
        return f"{self.prefix}: {cmd}"

# ========== 3. 闭包 + lambda ==========
def make_multiplier(factor: int):
    def multiply(x: int) -> int:
        return x * factor
    return multiply

def process_items(items: List[int]) -> List[int]:
    return list(map(lambda x: x * 2, items))

# ========== 4. 导入 + 调用 ==========
def read_config() -> str:
    value = os.environ.get("APP_MODE", "dev")
    return value

# ========== 5. 类型注解变量 ===========
app_name: str = "PyFullDemo"
version: int = 1
runner: AdvancedRunner = AdvancedRunner("demo")

# ========== 6. 数据处理 ==========
def transform(data: str) -> str:
    trimmed = data.strip()
    upper = trimmed.upper()
    return upper

def compute(values: List[int]) -> int:
    total = sum(values)
    return total

# ========== 7. 异常 ==========
def divide(a: int, b: int) -> float:
    if b == 0:
        raise ValueError("division by zero")
    return a / b