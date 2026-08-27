package testdata.kotlinfulldemo

import java.util.*

// ========== 1. 接口定义 ==========
interface Runner {
    fun run(cmd: String): String
}

interface ConfigProvider {
    fun get(key: String): String
}

// ========== 2. 类实现接口 ==========
open class BaseRunner : Runner {
    override fun run(cmd: String): String {
        return "base: $cmd"
    }
}

// ========== 3. 类继承 + 构造器 ==========
class AdvancedRunner(private val prefix: String) : BaseRunner(), ConfigProvider {
    override fun run(cmd: String): String {
        return "$prefix: $cmd"
    }

    override fun get(key: String): String {
        return "$prefix.$key"
    }
}

// ========== 4. 顶层函数 + 泛型 ==========
fun greet(name: String): String {
    return "Hello, $name!"
}

fun <T> identity(value: T): T {
    return value
}

// ========== 5. Lambda 闭包 ==========
fun makeMultiplier(factor: Int): (Int) -> Int {
    return { x -> x * factor }
}

// ========== 6. 数据类 ==========
data class Result(val success: Boolean, val message: String)

// ========== 7. 伴生对象 + 静态方法 ==========
class App private constructor(val name: String) {
    companion object {
        fun create(name: String): App {
            return App(name)
        }

        fun greet(name: String): String {
            return "Hello, $name!"
        }
    }

    fun transform(data: String): String {
        val trimmed = data.trim()
        return trimmed.uppercase()
    }
}

// ========== 8. 入口 ==========
fun main() {
    val app = App.create("KotlinFullDemo")
    val result = app.transform("  hello  ")
    println(result)
}