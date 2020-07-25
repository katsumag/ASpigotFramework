package me.katsumag.aspigotframework.modules.commands

object CommandHandler {

    private val _registeredCommands = mutableListOf<Command>()

    val registeredCommands: List<Command>
        get() = _registeredCommands


    fun registerCommand(command: Command) {
        _registeredCommands.add(command)
    }

}

fun registerCommand(name: String, action: (Command) -> Unit): Command {
    val command = Command(name, action)
    CommandHandler.registerCommand(command)
    return command
}