package testdata.javafulldemo;

import java.util.*;
import java.util.function.Function;

// ========== 1. 接口定义 ==========
interface IRunner {
    String run(String cmd);
}

interface IConfig {
    String get(String key);
}

// ========== 2. 泛型接口 ==========
interface Transformer<T, R> {
    R transform(T input);
}

// ========== 3. 类实现接口 ==========
class BaseRunner implements IRunner {
    @Override
    public String run(String cmd) {
        return "base: " + cmd;
    }
}

// ========== 4. 类继承 + 泛型 ==========
class AdvancedRunner extends BaseRunner implements IConfig {
    private String prefix;

    public AdvancedRunner(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String run(String cmd) {
        return prefix + ": " + cmd;
    }

    @Override
    public String get(String key) {
        return prefix + "." + key;
    }
}

// ========== 5. 顶层类 + 静态方法 + 泛型方法 ==========
public class App {
    private final String name;

    public App(String name) {
        this.name = name;
    }

    public static String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static <T> T identity(T value) {
        return value;
    }

    public String getName() {
        return name;
    }

    // ========== 6. 数据处理 ==========
    public String transform(String data) {
        String trimmed = data.trim();
        return trimmed.toUpperCase();
    }

    public int sum(List<Integer> items) {
        int total = 0;
        for (int item : items) {
            total += item;
        }
        return total;
    }

    // ========== 7. 入口方法 ==========
    public static void main(String[] args) {
        App app = new App("JavaFullDemo");
        String result = app.transform("  hello  ");
        System.out.println(result);
    }
}