package com.github.yan.switch2trae

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.io.IOException

/**
 * Base action class for Switch2Trae functionality
 */
abstract class Switch2TraeAction : AnAction() {
    
    companion object {
        private val LOG = Logger.getInstance(Switch2TraeAction::class.java)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        try {
            when (this) {
                is OpenProjectInTraeAction -> openProjectInTrae(project)
                is OpenFileInTraeAction -> openFileInTrae(e, project)
            }
        } catch (ex: Exception) {
            LOG.error("Failed to execute Switch2Trae action", ex)
            Messages.showErrorDialog(
                project,
                "Failed to open in Trae: ${ex.message}",
                "Switch2Trae Error"
            )
        }
    }

    private fun openProjectInTrae(project: Project) {
        val projectPath = project.basePath ?: return
        val settings = Switch2TraeSettings.getInstance()
        
        executeTraeCommand(listOf(settings.traeExecutablePath, projectPath))
    }

    private fun openFileInTrae(e: AnActionEvent, project: Project) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val editor = e.getData(CommonDataKeys.EDITOR)
        val settings = Switch2TraeSettings.getInstance()
        
        val filePath = virtualFile.path
        
        // Build command based on user-selected format
        editor?.let {
            val caretModel = it.caretModel
            val line = caretModel.logicalPosition.line + 1 // Convert to 1-based
            val column = caretModel.logicalPosition.column + 1 // Convert to 1-based
            
            val commands = buildTraeCommand(settings.traeExecutablePath, filePath, line, column, settings.commandLineFormat)
            executeTraeCommand(commands)
        } ?: run {
            // If no editor context, just open the file
            executeTraeCommand(listOf(settings.traeExecutablePath, filePath))
        }
    }
    
    private fun buildTraeCommand(executable: String, filePath: String, line: Int, column: Int, format: String): List<String> {
        return when (format) {
            "file:line:column" -> listOf(executable, "$filePath:$line:$column")
            "--goto line:column file" -> listOf(executable, "--goto", "$line:$column", filePath)
            "-g file:line:column" -> listOf(executable, "-g", "$filePath:$line:$column")
            "file +line:column" -> listOf(executable, filePath, "+$line:$column")
            else -> listOf(executable, "$filePath:$line:$column") // Default to VS Code style
        }
    }

    private fun executeTraeCommand(commands: List<String>) {
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                LOG.info("Executing Trae command: ${commands.joinToString(" ")}")
                val processBuilder = ProcessBuilder(commands)
                processBuilder.directory(null) // Use current working directory
                val process = processBuilder.start()
                
                // Don't wait for the process to complete, as Trae should run independently
                LOG.info("Trae command executed successfully")
            } catch (e: IOException) {
                LOG.error("Failed to execute Trae command: ${commands.joinToString(" ")}", e)
                
                // Show user-friendly error message
                ApplicationManager.getApplication().invokeLater {
                    Messages.showErrorDialog(
                        "Failed to launch Trae IDE. Please check:\n" +
                        "1. Trae executable path is correct in Settings\n" +
                        "2. Trae IDE is properly installed\n" +
                        "3. File permissions are correct\n\n" +
                        "Error: ${e.message}",
                        "Trae IDE Launch Error"
                    )
                }
            }
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabled = project != null
        
        // For file actions, also check if a file is selected
        if (this is OpenFileInTraeAction) {
            val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
            e.presentation.isEnabled = project != null && virtualFile != null && !virtualFile.isDirectory
        }
    }
}

/**
 * Action to open the current project in Trae
 */
class OpenProjectInTraeAction : Switch2TraeAction()

/**
 * Action to open the current file in Trae
 */
class OpenFileInTraeAction : Switch2TraeAction()