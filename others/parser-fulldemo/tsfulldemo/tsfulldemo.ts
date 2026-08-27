// tsfulldemo.ts — TypeScript 解析器全功能演示文件
// 覆盖 P0/P1/P2 所有已实现功能

// ========== 1. 导入（import）==========
import { VulnChild } from './common/vuln_child';
import { VulnRunner } from './iface/vuln_runner';
import { InterfaceImpl } from './cmd_exec/interface_impl';
import DefaultExport from './external/module';
import * as NamespaceImport from './namespace/module';

// ========== 2. 重导出（re-export）==========
export { VulnChild } from './common/vuln_child';
export type { VulnRunner } from './iface/vuln_runner';

// ========== 3. 接口定义（interface）==========
interface ILogger {
    log(message: string): void;
    error(message: string): void;
}

interface IConfigProvider {
    get(key: string): string;
}

// ========== 4. 类型别名（type alias）==========
type ID = string;
type Callback<T> = (value: T) => void;
type Result<T> = { data: T; error: string | null };
type UnionType = string | number | boolean;

// ========== 5. 枚举（enum）==========
enum Color {
    Red,
    Green,
    Blue
}

enum HttpStatus {
    OK = 200,
    NotFound = 404,
    InternalError = 500
}

// ========== 6. 类定义（class）==========
class AppLogger implements ILogger {
    private prefix: string;

    constructor(prefix: string) {
        this.prefix = prefix;
    }

    log(message: string): void {
        console.log(`[${this.prefix}] ${message}`);
    }

    error(message: string): void {
        console.error(`[${this.prefix}] ${message}`);
    }

    static createDefault(): AppLogger {
        return new AppLogger('App');
    }
}

// ========== 7. 类继承 + 泛型 + 计算属性 ==========
class AdvancedLogger extends AppLogger {
    ["app.version"]: string = '1.0';
    ["log.level"]: number = 0;

    constructor(prefix: string, private level: number) {
        super(prefix);
    }

    // 泛型方法
    wrap<T>(value: T): { data: T; timestamp: Date } {
        return { data: value, timestamp: new Date() };
    }

    // 方法重写
    log(message: string): void {
        if (this.level > 0) {
            super.log(message);
        }
    }
}

// ========== 8. 接口继承 ===========
interface IExtendedLogger extends ILogger, IConfigProvider {
    setLevel(level: number): void;
}

// ========== 9. 函数声明 ===========
// 函数声明（基础类型）
function greet(name: string): string {
    return `Hello, ${name}!`;
}

// 函数声明（多参数 + 可选参数 + 默认值）
function configure(url: string, port: number = 8080, timeout?: number): string {
    return `${url}:${port}`;
}

// 函数声明（可变参数）
function sum(...numbers: number[]): number {
    return numbers.reduce((a, b) => a + b, 0);
}

// 函数声明（联合类型返回）
function formatDate(date: Date): string | null {
    if (!date) return null;
    return date.toISOString();
}

// 异步函数
async function fetchData(url: string): Promise<string> {
    const response = await fetch(url);
    return response.text();
}

// 生成器函数
function* idGenerator(): Generator<number> {
    let id = 0;
    while (true) {
        yield id++;
    }
}

// ========== 10. 箭头函数 ===========
// 箭头函数赋值
const double = (x: number): number => x * 2;

// 多语句箭头函数
const calculate = (a: number, b: number): number => {
    const result = a + b;
    return result * 2;
};

// 箭头函数作为回调
const processed = [1, 2, 3].map((x: number) => x * 2);

// ========== 11. 对象字面量箭头函数方法 ===========
const handler = {
    getName: () => 'handler',
    getValue: (key: string) => this.cache[key],
    process: (data: string) => {
        return data.trim();
    }
};

// ========== 12. 变量声明 + 类型注解 ===========
const appName: string = 'TypeScriptParser';
let counter: number = 0;
const logger: ILogger = new AppLogger('App');
const config: IConfigProvider = new AdvancedLogger('Config', 1);

// ========== 13. 闭包捕获 ===========
function createCounter(initial: number) {
    let count = initial;
    return () => {
        count++;
        return count;
    };
}

function createMultiplier(factor: number) {
    const prefix = 'mul';
    return (value: number) => {
        // 捕获外部变量 factor 和 prefix
        console.log(`${prefix}: ${value * factor}`);
        return value * factor;
    };
}

// ========== 14. 装饰器 ===========
function sealed(target: any) {
    Object.seal(target);
}

function logMethod(target: any, key: string, descriptor: PropertyDescriptor) {
    const original = descriptor.value;
    descriptor.value = function (...args: any[]) {
        console.log(`Calling ${key} with`, args);
        return original.apply(this, args);
    };
    return descriptor;
}

@sealed
class DecoratedService {
    @logMethod
    execute(cmd: string): string {
        return `executed: ${cmd}`;
    }
}

// ========== 15. new 表达式 ===========
function createServices() {
    const appLogger = new AppLogger('Service');
    const advLogger = new AdvancedLogger('Advanced', 2);
    const service = new DecoratedService();
    return { appLogger, advLogger, service };
}

// ========== 16. 成员方法调用 ===========
function runPipeline(cmd: string, mode: string): string {
    const runner: VulnRunner = new InterfaceImpl();
    const child = new VulnChild();
    if (mode === 'impl') {
        return runner.run(cmd);
    }
    return child.run(cmd);
}

// ========== 17. 数据流追踪（return 语句）==========
function processData(input: string): string {
    const trimmed = input.trim();
    const upper = trimmed.toUpperCase();
    return upper;
}

function computeValue(a: number, b: number): number {
    const sum = a + b;
    const doubled = sum * 2;
    return doubled;
}

// ========== 18. 链式调用 ===========
function processChain(items: string[]): string[] {
    return items
        .filter(item => item.length > 0)
        .map(item => item.trim())
        .filter(item => item.length > 0);
}

// ========== 19. 三元表达式 + 分支 ===========
function getStatus(code: number): string {
    return code >= 200 && code < 300 ? 'success' : 'error';
}

// ========== 20. 模板字符串 ===========
function buildMessage(name: string, version: number): string {
    return `Application ${name} v${version} is running`;
}

// ========== 21. 解构赋值 ===========
function extractConfig(config: { host: string; port: number; ssl: boolean }): string {
    const { host, port, ssl } = config;
    const protocol = ssl ? 'https' : 'http';
    return `${protocol}://${host}:${port}`;
}

// ========== 22. as 类型断言 ===========
function parseInput(raw: unknown): string {
    const text = raw as string;
    return text.trim();
}

// ========== 23. 泛型函数 ===========
function identity<T>(value: T): T {
    return value;
}

function pair<A, B>(first: A, second: B): [A, B] {
    return [first, second];
}

// ========== 24. satisfies 表达式 ===========
type Colors = 'red' | 'green' | 'blue';
type RGB = [number, number, number];
const palette = {
    red: [255, 0, 0],
    green: [0, 255, 0],
    blue: [0, 0, 255]
} satisfies Record<Colors, RGB | string>;

// ========== 25. 顶层调用链 ===========
const appLoggerInstance = AppLogger.createDefault();
appLoggerInstance.log('Application started');