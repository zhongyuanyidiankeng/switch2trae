# Switch2Trae

<div align="center">

![JetBrains Plugin](https://img.shields.io/badge/JetBrains-Plugin-orange.svg)
![Version](https://img.shields.io/badge/version-1.1.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Platform](https://img.shields.io/badge/platform-IntelliJ%20Platform-lightgrey.svg)

**🚀 一个强大的 JetBrains IDE 插件，实现与 Trae IDE 的无缝切换**

*支持智能光标位置同步，多种命令行格式，完美的工作流程集成*

</div>

---

## 📖 目录

- [功能特性](#-功能特性)
- [安装指南](#️-安装指南)
- [配置设置](#️-配置设置)
- [使用指南](#-使用指南)
- [系统要求](#-系统要求)
- [开发指南](#‍-开发指南)
- [常见问题](#-常见问题)
- [更新日志](#-更新日志)
- [贡献](#-贡献)
- [许可证](#-许可证)

## 🌟 功能特性

### 🔄 智能编辑器切换
- **一键切换**：在 JetBrains IDE 和 Trae IDE 之间瞬间切换
- **精确定位**：自动同步光标位置（行号和列号）
- **上下文保持**：完美保持编辑状态，无缝衔接工作流程

### ⌨️ 多样化操作方式
- **快捷键支持**
  - `Alt+Shift+P` - 在 Trae 中打开整个项目
  - `Alt+Shift+O` - 在 Trae 中打开当前文件
- **右键菜单集成**
  - 编辑器右键菜单
  - 项目视图右键菜单
- **工具菜单访问**
  - Tools → Switch2Trae

### 🛠️ 灵活配置选项
- **多种命令行格式支持**
  - `file:line:column` (VS Code 风格)
  - `--goto line:column file` (传统 IDE 风格)
  - `-g file:line:column` (简短参数风格)
  - `file +line:column` (Vim 风格)
- **自定义可执行文件路径**
- **个性化快捷键设置**

### 🔧 现代化架构
- **后台线程执行**：避免 UI 阻塞，确保流畅体验
- **智能错误处理**：友好的错误提示和故障排除指导
- **兼容性优化**：支持最新的 IntelliJ Platform API

## 🛠️ 安装指南

### 方法 1：JetBrains Marketplace（推荐）
1. 打开 IDE → `Settings/Preferences` → `Plugins` → `Marketplace`
2. 搜索 **"Switch2Trae"**
3. 点击 `Install` 并重启 IDE

### 方法 2：本地安装
1. 从 [Releases](https://github.com/yan/switch2trae/releases) 下载最新版本
2. IDE → `Settings/Preferences` → `Plugins` → ⚙️ → `Install Plugin from Disk...`
3. 选择下载的 `.zip` 文件
4. 重启 IDE 完成安装

## ⚙️ 配置设置

### 基本配置
1. 打开 `Settings/Preferences` → `Tools` → `Switch2Trae`
2. **Trae 可执行文件路径**：
   - 默认：`trae`
   - 自定义：指定完整路径，如 `C:\Program Files\Trae\trae.exe`
3. **命令行格式**：选择适合你的 Trae IDE 版本的格式

### 快捷键自定义
1. 打开 `Settings/Preferences` → `Keymap`
2. 搜索 "Switch2Trae"
3. 右键修改快捷键组合

### 命令行格式说明
| 格式 | 示例 | 适用场景 |
|------|------|----------|
| `file:line:column` | `trae file.txt:10:5` | VS Code 风格，推荐默认 |
| `--goto line:column file` | `trae --goto 10:5 file.txt` | 传统 IDE 风格 |
| `-g file:line:column` | `trae -g file.txt:10:5` | 简短参数风格 |
| `file +line:column` | `trae file.txt +10:5` | Vim 风格 |

## 🚀 使用指南

### 快速开始

#### 打开项目
```
方式 1：快捷键 Alt+Shift+P
方式 2：项目视图右键 → "Open Project In Trae"
方式 3：Tools → Switch2Trae → "Open Project in Trae"
```

#### 打开当前文件
```
方式 1：快捷键 Alt+Shift+O
方式 2：编辑器右键 → "Open File In Trae"
方式 3：Tools → Switch2Trae → "Open File in Trae"
```

### 高级用法

#### 精确光标定位
- 在编辑器中将光标定位到目标位置
- 使用快捷键或菜单打开文件
- Trae 将自动跳转到相同的行和列位置

#### 项目级别操作
- 在项目视图中右键任意文件夹
- 选择 "Open Project In Trae"
- 整个项目将在 Trae 中打开

## 📋 系统要求

### 必需条件
- **Trae IDE**：已安装并可通过命令行访问
- **JetBrains IDE**：版本 2025.1 及以上
- **Java**：JDK 17 或更高版本

### 兼容的 IDE
- IntelliJ IDEA (Ultimate/Community)
- PyCharm (Professional/Community)
- WebStorm
- PhpStorm
- GoLand
- RustRover
- CLion
- DataGrip
- Android Studio
- 其他基于 IntelliJ Platform 的 IDE

## 🧑‍💻 开发指南

### 环境准备
```bash
# 克隆仓库
git clone https://github.com/yan/switch2trae.git
cd switch2trae

# 检查 Java 版本（需要 JDK 17+）
java -version
```

### 构建项目
```bash
# 编译项目
./gradlew compileKotlin

# 构建插件
./gradlew buildPlugin

# 运行测试
./gradlew test

# 在开发环境中运行
./gradlew runIde
```

### 项目结构
```
src/main/kotlin/com/github/yan/switch2trae/
├── Switch2TraeAction.kt          # 主要操作逻辑
├── Switch2TraeSettings.kt        # 设置管理
├── Switch2TraeConfigurable.kt    # 配置界面
└── Switch2TraeBundle.kt          # 国际化支持
```

### 代码贡献
1. Fork 本仓库
2. 创建功能分支：`git checkout -b feature/amazing-feature`
3. 提交更改：`git commit -m 'Add amazing feature'`
4. 推送分支：`git push origin feature/amazing-feature`
5. 提交 Pull Request

## 📦 发布和分发

### 从 JetBrains Marketplace 安装
1. 打开 IDE → `Settings/Preferences` → `Plugins`
2. 搜索 "Switch2Trae"
3. 点击 `Install` 并重启 IDE

### 手动安装
1. 从 [Releases](https://github.com/yan/switch2trae/releases) 下载最新版本
2. 打开 IDE → `Settings/Preferences` → `Plugins`
3. 点击齿轮图标 → `Install Plugin from Disk`
4. 选择下载的 `.zip` 文件并重启 IDE

### 开发者发布指南
如果你想参与插件开发或发布自己的版本：

- 📋 **快速发布**：查看 [QUICK_PUBLISH.md](./QUICK_PUBLISH.md)
- 📖 **详细指南**：查看 [PUBLISH_GUIDE.md](./PUBLISH_GUIDE.md)
- 🔍 **发布检查**：运行 `scripts/check-release.ps1`

```bash
# 构建插件包
./gradlew buildPlugin

# 发布前检查
./scripts/check-release.ps1

# 发布到市场（需要 API Token）
./gradlew publishPlugin
```

## 🙋 常见问题

### 安装和配置

**Q: 安装后找不到插件菜单？**
A: 重启 IDE，然后在 `Tools` 菜单中查找 `Switch2Trae` 选项。

**Q: 如何验证 Trae 是否正确安装？**
A: 在终端运行 `trae --version`，如果显示版本信息则安装正确。

### 使用问题

**Q: 点击菜单或快捷键没有反应？**
A: 检查以下设置：
1. `Settings` → `Tools` → `Switch2Trae` 中的可执行文件路径是否正确
2. 尝试不同的命令行格式选项
3. 查看 IDE 日志：`Help` → `Show Log in Explorer/Finder`

**Q: 文件打开了但光标位置不对？**
A: 在设置中尝试不同的命令行格式，不同版本的 Trae 可能支持不同的参数格式。

**Q: 支持相对路径吗？**
A: 建议使用绝对路径以确保兼容性，插件会自动处理路径转换。

### 故障排除

**Q: 出现 "Failed to launch Trae IDE" 错误？**
A: 按以下步骤排查：
1. 确认 Trae 可执行文件路径正确
2. 检查 Trae 是否有执行权限
3. 尝试在终端手动运行 Trae 命令
4. 查看详细错误信息：`Help` → `Show Log in Files`

## 📝 更新日志

### v1.1.0 (最新)
- 🌐 **全局配置** - 配置现在应用于所有项目
- ⚙️ **应用级别设置** - 无需为每个项目重新配置
- 🔄 **改进用户体验** - 一次设置，全局生效
- 📍 **设置位置** - 在 File → Settings → Tools → Switch2Trae 中找到配置

### v1.0.0
- ✨ 支持多种命令行格式
- 🐛 修复 EDT 线程访问问题
- 🔧 更新到最新 IntelliJ Platform API
- 💡 改进错误处理和用户提示
- 📚 完善文档和配置界面

### v0.9.0
- 🎉 初始版本发布
- ⚡ 基本的文件和项目切换功能
- ⌨️ 快捷键支持

## 🤝 贡献

我们欢迎所有形式的贡献！

### 贡献方式
- 🐛 报告 Bug
- 💡 提出新功能建议
- 📝 改进文档
- 🔧 提交代码修复
- 🌍 帮助翻译

### 开发规范
- 遵循 Kotlin 编码规范
- 添加适当的测试用例
- 更新相关文档
- 确保向后兼容性

## 📄 许可证

本项目采用 [MIT 许可证](LICENSE)。

## 📮 支持与反馈

### 获取帮助
- 📋 [GitHub Issues](https://github.com/yan/switch2trae/issues) - 报告问题或建议
- 💬 [Discussions](https://github.com/yan/switch2trae/discussions) - 社区讨论
- 📧 Email: [yanwanglaiye@proton.me](mailto:your-email@example.com)

### 项目状态
- 🔄 积极维护中
- 📈 持续改进
- 🆕 定期发布更新

---

<div align="center">

**如果这个插件对你有帮助，请给我们一个 ⭐ Star！**

以上均有ai生成

</div>
