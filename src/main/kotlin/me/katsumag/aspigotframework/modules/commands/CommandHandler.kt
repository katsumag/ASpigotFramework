package me.katsumag.aspigotframework.modules.commands

import me.katsumag.aspigotframework.modules.nms.NMSHelper
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.plugin.SimplePluginManager

object CommandHandler {

    private val _registeredCommands = mutableListOf<Command>()
    val registeredCommands: List<Command>
        get() = _registeredCommands
    private val commandMap = NMSHelper.getCommandMap()

    fun registerCommand(command: Command) {
        _registeredCommands.add(command)
        commandMap.register(command.label, command.name, command)
    }

    fun unregisterCommand(command: Command) {
        _registeredCommands.remove(command)
        command.unregister(commandMap)
    }

}