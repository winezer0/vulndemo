# 路由生命周期事实样例

用于七种语言真实解析器、Memory/SQLite、QueryRoutes、审计快照的共同回归。

- Flask before_request、Spring InterceptorRegistry、ASP.NET Configure 管线。
- Ktor install 的同级作用域隔离、Laravel 分组与局部 middleware。
- Axum route_layer 只作用于已注册路由，后加路由不继承。
- Express 静态路径不匹配的注册保留原始参与者和 excluded 原因；匹配路径仍不证明控制有效。

所有中间件均为语法候选，不表示认证有效。Java/C# 的应用容器归属未证明，必须保留 global_registration_scope_unknown；开放分支用于验证不会错误继承兄弟注册。本目录不作为可运行的业务项目。
