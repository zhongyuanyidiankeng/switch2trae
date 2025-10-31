package com.github.yan.switch2trae

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

/**
 * Settings service for Switch2Trae plugin
 */
@Service(Service.Level.PROJECT)
@State(
    name = "Switch2TraeSettings",
    storages = [Storage("switch2trae.xml")]
)
class Switch2TraeSettings : PersistentStateComponent<Switch2TraeSettings.State> {
    
    data class State(
        var traeExecutablePath: String = "trae",
        var commandLineFormat: String = "file:line:column" // Default to VS Code style
    )
    
    private var myState = State()
    
    var traeExecutablePath: String
        get() = myState.traeExecutablePath
        set(value) {
            myState.traeExecutablePath = value
        }
    
    var commandLineFormat: String
        get() = myState.commandLineFormat
        set(value) {
            myState.commandLineFormat = value
        }
    
    override fun getState(): State = myState
    
    override fun loadState(state: State) {
        myState = state
    }
    
    companion object {
        fun getInstance(project: Project): Switch2TraeSettings {
            return project.service<Switch2TraeSettings>()
        }
    }
}