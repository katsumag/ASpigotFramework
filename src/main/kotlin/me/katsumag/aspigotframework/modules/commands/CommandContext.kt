package me.katsumag.aspigotframework.modules.commands

import org.bukkit.command.CommandSender

data class CommandContext(val commandSender: CommandSender, val label: String, val args: Array<String>) {

    override fun equals(other: Any?): Boolean {
        if (other !is CommandContext) return false
        if (commandSender == other.commandSender && label == other.label && args.contentEquals(other.args)) return true
        return false
    }

    override fun hashCode(): Int {
        var result = commandSender.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }
}