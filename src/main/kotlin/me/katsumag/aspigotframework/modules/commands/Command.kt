package me.katsumag.aspigotframework.modules.commands

import org.bukkit.command.CommandSender

class Command(private val commandName: String, private val action: (Command) -> Unit) : org.bukkit.command.Command(commandName) {

    private val _subCommands = mutableListOf<SubCommand>()

    val subCommands: List<SubCommand>
        get() = _subCommands

    fun addSubCommand(subCommandName: String, action: (Command) -> Unit) {
        _subCommands.add(SubCommand(subCommandName, action))
    }

    override fun execute(p0: CommandSender, p1: String, p2: Array<out String>): Boolean {
        TODO("Not yet implemented")
    }

}