```
请在当前项目的rubyvulnweb 目录下，编写一套基于Rails框架的Ruby Web测试代码，用于安全代码分析工具、漏洞检测工具的深度能力验证。整体遵循分目录、分文件原则，结构规范、注释清晰、代码简洁无冗余。

一、整体目录与文件拆分规则
1. 公共父类、通用子类统一放置在 app/models/common 目录；
2. 接口/模块定义单独存放至 app/models/concerns 目录；
3. 按照漏洞类型划分独立子目录：cmd_exec（命令执行）、code_exec（代码执行）、ssrf（SSRF漏洞）；
4. 每个漏洞目录下区分两类测试文件：纯子类继承调用文件、接口实现类调用文件；
5. 根目录保留路由配置 routes.rb、通用跨文件函数调用文件 func_call.rb。

二、代码功能与场景要求
1. 漏洞类型要求
   集成命令执行、代码执行、SSRF三类高危漏洞，同时包含两种数据源场景：
   - 外部可控数据源：通过 Rails params 接收前端传入参数，构造完整漏洞调用链；
   - 硬编码无外部数据源：内置固定参数的漏洞代码；
   所有漏洞点嵌入到父类方法中，通过「子类继承、模块混入、跨文件调用」形成多层深层调用链。

2. 面向对象调用场景（全覆盖）
   - 基础调用：同一文件内函数/类调用、跨文件函数调用、跨文件类实例与方法调用；
   - 类继承：编写独立父类 VulnParent、独立子类 VulnChild，实现子类继承父类、子类调用父类漏洞方法的链路；
   - 模块能力：独立定义模块文件，每个漏洞目录编写对应的包含模块的类，实现「模块 + 类继承」组合调用；
   - 多层调用链：构造 外部请求参数 → 调用入口 → 子类/模块包含类 → 父类 → 漏洞点 的完整长调用链路。

3. 代码规范要求
   1. 每个文件功能单一、职责清晰，漏洞位置、调用关系添加明确注释标注；
   2. 所有文件路径引用合理，require/include无路径错误；
   3. 总入口页面整理所有测试链接，方便直接访问各个测试用例；
   4. 保留纯子类继承调用测试用例、模块包含类测试用例两类场景，分开独立测试。

三、输出要求
1. 先输出完整的目录树结构；
2. 再按目录顺序，依次输出每一个文件的完整可运行代码；
3. 代码保证可直接部署运行，无需额外配置。
```

---

## 补充目录结构参考
```
参考目录结构：
demo/rubyvulnweb/
├─ app/
│  ├─ controllers/
│  │  ├─ application_controller.rb    # 基础控制器
│  │  ├─ cmd_exec_controller.rb       # 命令执行测试控制器
│  │  ├─ code_exec_controller.rb      # 代码执行测试控制器
│  │  ├─ ssrf_controller.rb           # SSRF测试控制器
│  │  └─ func_call_controller.rb      # 跨文件调用控制器
│  └─ models/
│     ├─ common/
│     │  ├─ vuln_parent.rb            # 公共父类
│     │  └─ vuln_child.rb             # 独立子类
│     ├─ concerns/
│     │  └─ vuln_module.rb            # 漏洞模块定义
│     ├─ cmd_exec/
│     │  ├─ sub_class_test.rb         # 纯子类继承测试
│     │  └─ module_impl.rb            # 模块包含测试
│     ├─ code_exec/
│     │  ├─ sub_class_test.rb
│     │  └─ module_impl.rb
│     └─ ssrf/
│        ├─ sub_class_test.rb
│        └─ module_impl.rb
├─ config/
│  └─ routes.rb                       # 路由配置
├─ public/
│  └─ index.html                      # 测试入口页面
└─ Gemfile                            # 依赖配置
```