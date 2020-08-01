package me.katsumag.aspigotframework.modules.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.plugin.SimplePluginManager

object CommandHandler {

    private val _registeredCommands = mutableListOf<Command>()
    private val commandMap: CommandMap

    init {
        val mapField = SimplePluginManager::class.java.getDeclaredField("commandMap")
        mapField.isAccessible = true
        commandMap = mapField.get(Bukkit.getServer().pluginManager) as CommandMap
    }

    val registeredCommands: List<Command>
        get() = _registeredCommands


    fun registerCommand(command: Command) {
        _registeredCommands.add(command)
        commandMap.register(command.label, command.name, command)
    }

}