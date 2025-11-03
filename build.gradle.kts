import org.jetbrains.intellij.platform.gradle.tasks.BuildSearchableOptionsTask.Companion.register

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.7.1"
}

group = "com.github.yan"
version = "1.1.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure IntelliJ Platform Gradle Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1.4.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        // Plugin version
        version = project.version.toString()
        
        // IDE version compatibility
        ideaVersion {
            sinceBuild = "251"
            // 不设置 untilBuild，表示兼容所有未来版本
        }

        // Change notes for this version
        changeNotes = """
            <h3>v1.0.0 - Initial Release 🎉</h3>
            <ul>
                <li>✨ <strong>Project Switching</strong> - Open entire projects in Trae IDE with Alt+Shift+P</li>
                <li>📄 <strong>File Switching</strong> - Open individual files with cursor positioning using Alt+Shift+O</li>
                <li>⌨️ <strong>Keyboard Shortcuts</strong> - Quick access shortcuts for seamless workflow</li>
                <li>🎨 <strong>Context Menu Integration</strong> - Right-click options in editor and project tree</li>
                <li>🔧 <strong>Flexible Configuration</strong> - Support for multiple Trae command-line formats:
                    <ul>
                        <li><code>file:line:column</code> (VS Code style)</li>
                        <li><code>--goto line:column file</code> (Vim style)</li>
                        <li><code>-g file:line:column</code> (Emacs style)</li>
                        <li><code>file +line:column</code> (Nano style)</li>
                    </ul>
                </li>
                <li>🛠️ <strong>Smart Error Handling</strong> - Helpful troubleshooting dialogs with actionable guidance</li>
                <li>📍 <strong>Cursor Position Sync</strong> - Maintains exact cursor position across IDEs</li>
                <li>⚙️ <strong>Easy Configuration</strong> - Simple setup through Settings → Tools → Switch2Trae</li>
            </ul>
            <p><strong>Perfect for developers who want to leverage the best of both JetBrains and Trae ecosystems!</strong></p>
        """.trimIndent()
    }
    
    // Publishing configuration
    publishing {
        // Token should be set via environment variable or gradle.properties
        token = providers.environmentVariable("PUBLISH_TOKEN")
        
        // Publishing channels
        channels = listOf("default")
    }
    
    // Plugin verification
    pluginVerification {
        ides {
            // Verify against multiple IDE versions
            recommended()
        }
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    
    // Configure test task
    test {
        useJUnitPlatform()
    }
    
    // Plugin verification is handled automatically by the IntelliJ Platform plugin
    
    // Custom task to prepare for release
    register("prepareRelease") {
        group = "publishing"
        description = "Prepare plugin for release by running all checks"
        
        dependsOn("clean", "compileKotlin", "test", "verifyPlugin", "buildPlugin")
        
        doLast {
            println("✅ Plugin is ready for release!")
            println("📦 Plugin package: ${project.layout.buildDirectory.get()}/distributions/${project.name}-${project.version}.zip")
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}
