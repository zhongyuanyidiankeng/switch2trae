# 🚀 Switch2Trae 插件快速发布指南

## 📋 发布前检查清单

### ✅ 已完成项目
- [x] **代码完善** - 所有功能已实现并测试
- [x] **plugin.xml 配置** - 插件元数据已完善
- [x] **构建脚本** - build.gradle.kts 已优化
- [x] **CI/CD 配置** - GitHub Actions 工作流已设置
- [x] **文档完善** - README 和发布指南已创建
- [x] **插件图标** - SVG 图标已准备

### 🔄 待完成项目
- [ ] **JetBrains 开发者账户** - 注册并验证
- [ ] **插件市场提交** - 上传并提交审核
- [ ] **发布后维护** - 监控反馈和更新

## 🎯 立即开始发布

### 第一步：创建开发者账户
1. 访问 [JetBrains 账户页面](https://account.jetbrains.com/)
2. 注册或登录账户
3. 访问 [插件市场](https://plugins.jetbrains.com/)
4. 点击 "Upload plugin" 申请开发者权限

### 第二步：本地构建测试
```bash
# 进入项目目录
cd d:/github/switch2trae

# 运行完整构建和验证
./gradlew prepareRelease
```

### 第三步：手动上传发布
1. **准备插件包**：
   - 文件位置：`build/distributions/Switch2Trae-1.1.0.zip`
   - 确保文件大小合理（通常 < 10MB）

2. **登录插件市场**：
   - 访问 [JetBrains Marketplace](https://plugins.jetbrains.com/)
   - 使用开发者账户登录

3. **上传插件**：
   - 点击 "Upload plugin"
   - 选择插件包文件
   - 填写插件信息（已在 plugin.xml 中配置）

4. **完善插件页面**：
   - **名称**：Switch2Trae
   - **分类**：Tools Integration
   - **标签**：ide-integration, productivity, tools, trae
   - **许可证**：MIT
   - **描述**：使用 plugin.xml 中的描述
   - **截图**：准备 2-3 张功能演示图

### 第四步：自动化发布（可选）
如果你有 JetBrains API Token：

1. **设置环境变量**：
   ```bash
   export PUBLISH_TOKEN="your-api-token"
   ```

2. **自动发布**：
   ```bash
   ./gradlew publishPlugin
   ```

## 📊 发布状态跟踪

### 审核时间预期
- **首次提交**：1-3 个工作日
- **更新版本**：1-2 个工作日
- **节假日期间**：可能延长至 5-7 天

### 审核通过后
- 插件将在市场中可见
- 用户可以搜索和安装
- 开始收集用户反馈

## 🛠️ 常用命令

```bash
# 清理项目
./gradlew clean

# 编译代码
./gradlew compileKotlin

# 运行测试
./gradlew test

# 验证插件
./gradlew verifyPlugin

# 构建插件包
./gradlew buildPlugin

# 完整发布准备
./gradlew prepareRelease

# 发布到市场（需要 API Token）
./gradlew publishPlugin
```

## 📞 获取帮助

如果遇到问题：
1. 查看 [详细发布指南](./PUBLISH_GUIDE.md)
2. 参考 [JetBrains 官方文档](https://plugins.jetbrains.com/docs/intellij/)
3. 在 GitHub Issues 中提问

## 🎉 发布成功后

1. **监控反馈** - 关注用户评价和问题报告
2. **准备更新** - 根据反馈计划下一版本
3. **推广插件** - 在社区中分享你的插件

---

**祝你发布成功！** 🚀

插件包位置：`build/distributions/Switch2Trae-1.1.0.zip`