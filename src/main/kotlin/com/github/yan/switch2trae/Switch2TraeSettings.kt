package com.github.yan.switch2trae

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

/**
 * Settings service for Switch2Trae plugin
 */
@Service(Service.Level.APP)
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
        fun getInstance(): Switch2TraeSettings {
            return ApplicationManager.getApplication().getService(Switch2TraeSettings::class.java)
        }
    }
}