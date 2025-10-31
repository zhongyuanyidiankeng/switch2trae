# Switch2Trae Plugin Release Check Script
# Validates if the plugin is ready for publishing

Write-Host "Switch2Trae Plugin Release Check" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan

$errors = @()
$warnings = @()

# Check project structure
Write-Host "`nChecking project structure..." -ForegroundColor Yellow

$requiredFiles = @(
    "src/main/resources/META-INF/plugin.xml",
    "src/main/resources/META-INF/pluginIcon.svg",
    "build.gradle.kts",
    "README.md",
    "LICENSE"
)

foreach ($file in $requiredFiles) {
    if (Test-Path $file) {
        Write-Host "  [OK] $file" -ForegroundColor Green
    } else {
        Write-Host "  [MISSING] $file" -ForegroundColor Red
        $errors += "Missing required file: $file"
    }
}

# Check plugin.xml content
Write-Host "`nChecking plugin.xml configuration..." -ForegroundColor Yellow

if (Test-Path "src/main/resources/META-INF/plugin.xml") {
    $pluginXml = Get-Content "src/main/resources/META-INF/plugin.xml" -Raw
    
    $requiredElements = @(
        @{Name="id"; Pattern="<id>"},
        @{Name="name"; Pattern="<name>"},
        @{Name="version"; Pattern="<version>"},
        @{Name="vendor"; Pattern="<vendor"},
        @{Name="description"; Pattern="<description>"},
        @{Name="change-notes"; Pattern="<change-notes>"},
        @{Name="idea-version"; Pattern="<idea-version"}
    )
    
    foreach ($element in $requiredElements) {
        if ($pluginXml -match $element.Pattern) {
            Write-Host "  [OK] $($element.Name)" -ForegroundColor Green
        } else {
            Write-Host "  [MISSING] $($element.Name)" -ForegroundColor Red
            $errors += "plugin.xml missing required element: $($element.Name)"
        }
    }
} else {
    $errors += "plugin.xml file not found"
}

# Check build configuration
Write-Host "`nChecking build configuration..." -ForegroundColor Yellow

if (Test-Path "build.gradle.kts") {
    $buildGradle = Get-Content "build.gradle.kts" -Raw
    
    if ($buildGradle -match 'version\s*=\s*".*"') {
        Write-Host "  [OK] Version is set" -ForegroundColor Green
    } else {
        Write-Host "  [ERROR] Version not set" -ForegroundColor Red
        $errors += "Version not set in build.gradle.kts"
    }
    
    if ($buildGradle -match 'publishing\s*\{') {
        Write-Host "  [OK] Publishing configuration found" -ForegroundColor Green
    } else {
        Write-Host "  [WARNING] Publishing configuration not found" -ForegroundColor Yellow
        $warnings += "Auto-publishing not configured in build.gradle.kts"
    }
} else {
    $errors += "build.gradle.kts file not found"
}

# Check source code files
Write-Host "`nChecking source code files..." -ForegroundColor Yellow

$sourceFiles = @(
    "src/main/kotlin/com/github/yan/switch2trae/Switch2TraeAction.kt",
    "src/main/kotlin/com/github/yan/switch2trae/Switch2TraeSettings.kt",
    "src/main/kotlin/com/github/yan/switch2trae/Switch2TraeConfigurable.kt"
)

foreach ($file in $sourceFiles) {
    if (Test-Path $file) {
        Write-Host "  [OK] $file" -ForegroundColor Green
    } else {
        Write-Host "  [MISSING] $file" -ForegroundColor Red
        $errors += "Missing source file: $file"
    }
}

# Check documentation
Write-Host "`nChecking documentation..." -ForegroundColor Yellow

if (Test-Path "README.md") {
    $readme = Get-Content "README.md" -Raw
    if ($readme.Length -gt 500) {
        Write-Host "  [OK] README.md has sufficient content" -ForegroundColor Green
    } else {
        Write-Host "  [WARNING] README.md content is minimal" -ForegroundColor Yellow
        $warnings += "README.md might need more detailed content"
    }
} else {
    $errors += "README.md file not found"
}

if (Test-Path "LICENSE") {
    Write-Host "  [OK] LICENSE file exists" -ForegroundColor Green
} else {
    Write-Host "  [WARNING] LICENSE file not found" -ForegroundColor Yellow
    $warnings += "Consider adding a LICENSE file"
}

# Try to build the plugin
Write-Host "`nTrying to build plugin..." -ForegroundColor Yellow

try {
    $buildResult = & "./gradlew" "buildPlugin" "--quiet" 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  [OK] Plugin build successful" -ForegroundColor Green
        
        # Check build artifacts
        $pluginZip = Get-ChildItem "build/distributions/*.zip" -ErrorAction SilentlyContinue
        if ($pluginZip) {
            Write-Host "  [OK] Plugin package generated: $($pluginZip.Name)" -ForegroundColor Green
            Write-Host "  [INFO] File size: $([math]::Round($pluginZip.Length / 1MB, 2)) MB" -ForegroundColor Cyan
        } else {
            $errors += "Plugin package not generated"
        }
    } else {
        Write-Host "  [ERROR] Plugin build failed" -ForegroundColor Red
        $errors += "Plugin build failed: $buildResult"
    }
} catch {
    Write-Host "  [ERROR] Cannot execute build command" -ForegroundColor Red
    $errors += "Cannot execute gradlew command: $($_.Exception.Message)"
}

# Output check results
Write-Host "`nCheck Results" -ForegroundColor Cyan
Write-Host "=============" -ForegroundColor Cyan

if ($errors.Count -eq 0) {
    Write-Host "`nCongratulations! Plugin is ready for publishing!" -ForegroundColor Green
    Write-Host "`nNext steps:" -ForegroundColor Cyan
    Write-Host "1. Visit https://plugins.jetbrains.com/" -ForegroundColor White
    Write-Host "2. Register developer account" -ForegroundColor White
    Write-Host "3. Upload plugin package: build/distributions/*.zip" -ForegroundColor White
    Write-Host "4. Fill plugin information and submit for review" -ForegroundColor White
} else {
    Write-Host "`nFound $($errors.Count) errors that need to be fixed before publishing:" -ForegroundColor Red
    foreach ($error in $errors) {
        Write-Host "  - $error" -ForegroundColor Red
    }
}

if ($warnings.Count -gt 0) {
    Write-Host "`nFound $($warnings.Count) warnings (optional fixes):" -ForegroundColor Yellow
    foreach ($warning in $warnings) {
        Write-Host "  - $warning" -ForegroundColor Yellow
    }
}

Write-Host "`nFor more information, see:" -ForegroundColor Cyan
Write-Host "  - Detailed publishing guide: PUBLISH_GUIDE.md" -ForegroundColor White
Write-Host "  - Quick publishing guide: QUICK_PUBLISH.md" -ForegroundColor White

# Return appropriate exit code
if ($errors.Count -gt 0) {
    exit 1
} else {
    exit 0
}