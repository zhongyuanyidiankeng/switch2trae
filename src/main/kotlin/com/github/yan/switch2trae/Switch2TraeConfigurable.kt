package com.github.yan.switch2trae

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JComboBox

/**
 * Configurable for Switch2Trae settings
 */
class Switch2TraeConfigurable : Configurable {
    
    private var traePathField: TextFieldWithBrowseButton? = null
    private var formatComboBox: JComboBox<String>? = null
    private var mainPanel: JPanel? = null
    
    companion object {
        private val COMMAND_FORMATS = arrayOf(
            "file:line:column",
            "--goto line:column file",
            "-g file:line:column",
            "file +line:column"
        )
    }
    
    override fun getDisplayName(): String = "Switch2Trae"
    
    override fun createComponent(): JComponent? {
        val traePathField = TextFieldWithBrowseButton().apply {
            addBrowseFolderListener(
                "Select Trae Executable",
                "Choose the path to Trae executable",
                null,
                FileChooserDescriptorFactory.createSingleFileDescriptor()
            )
        }
        this.traePathField = traePathField
        
        val formatComboBox = JComboBox(COMMAND_FORMATS)
        this.formatComboBox = formatComboBox
        
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(
                JBLabel("Trae executable path:"),
                traePathField,
                1,
                false
            )
            .addLabeledComponent(
                JBLabel("Command line format:"),
                formatComboBox,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
        
        return mainPanel
    }
    
    override fun isModified(): Boolean {
        val settings = Switch2TraeSettings.getInstance()
        return traePathField?.text != settings.traeExecutablePath ||
               formatComboBox?.selectedItem as? String != settings.commandLineFormat
    }
    
    override fun apply() {
        val settings = Switch2TraeSettings.getInstance()
        traePathField?.text?.let { path ->
            settings.traeExecutablePath = path.trim().ifEmpty { "trae" }
        }
        formatComboBox?.selectedItem?.let { format ->
            settings.commandLineFormat = format as String
        }
    }
    
    override fun reset() {
        val settings = Switch2TraeSettings.getInstance()
        traePathField?.text = settings.traeExecutablePath
        formatComboBox?.selectedItem = settings.commandLineFormat
    }
    
    override fun disposeUIResources() {
        traePathField = null
        formatComboBox = null
        mainPanel = null
    }
}