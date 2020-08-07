package me.katsumag.aspigotframework.modules.commands

class SubCommand(private val name: String, val command: Command, val action: (SubCommand) -> Unit) {

    private val _subCommands = mutableListOf<SubCommand>()

    val subCommands: List<SubCommand>
        get() = _subCommands

    fun addSubCommand(subCommandName: String, command: Command, action: (SubCommand) -> Unit) {
        _subCommands.add(SubCommand(subCommandName, command, action))
    }

    fun sendMessage(message: String) = command.sendMessage(message)

    fun getArgs() = command.getArgs()

}