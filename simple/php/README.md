# PHP 文件上传示例

该示例展示 PHP Web 项目中常见的继承和依赖组合方式：

- `BaseController` → `UploadController`：基础控制器继承。
- `FileUploader` → `AbstractFileUploader` → `LocalFileUploader` / `OssFileUploader`：接口、抽象基类和具体实现组合。
- `UploadService` 依赖 `FileUploader`，可在本地存储和 OSS 存储之间切换。

OSS 客户端通过 `OssClient` 注入，避免示例依赖具体云厂商 SDK。
