package me.katsumag.aspigotframework.modules.commands

class SubCommand(private val name: String, val action: (Command) -> Unit) {

    private val _subCommands = mutableListOf<SubCommand>()

    val subCommands: List<SubCommand>
        get() = _subCommands

    fun addSubCommand(subCommandName: String, action: (Command) -> Unit) {
        _subCommands.add(SubCommand(subCommandName, action))
    }

}