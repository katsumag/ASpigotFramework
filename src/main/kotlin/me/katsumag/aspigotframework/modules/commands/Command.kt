package me.katsumag.aspigotframework.modules.commands

import org.bukkit.command.CommandSender

class Command(private val commandName: String, private val action: (Command) -> Unit) : org.bukkit.command.Command(commandName) {

    private val _subCommands = mutableListOf<SubCommand>()

    val subCommands: List<SubCommand>
        get() = _subCommands

    lateinit var commandContext: CommandContext

    fun addSubCommand(subCommandName: String, action: (SubCommand) -> Unit) {
        _subCommands.add(SubCommand(subCommandName, this, action))
    }

    fun register() {
        this.label = commandName
        CommandHandler.registerCommand(this)
    }

    fun unregister() {
        CommandHandler.unregisterCommand(this)
    }

    private fun process(command: Command) {
        command.action.invoke(this)
        command.subCommands.forEach { process(it) }
    }

    private fun process(subCommand: SubCommand) {
        subCommand.action.invoke(subCommand)
        subCommand.subCommands.forEach { process(it) }
    }

    override fun execute(commandSender: CommandSender, label: String, args: Array<String>): Boolean {
        commandContext = CommandContext(commandSender, label, args)
        process(this)
        return true
    }

    fun sendMessage(message: String) = this.commandContext.commandSender.sendMessage(message)


    fun getArgs() = this.commandContext.args

}