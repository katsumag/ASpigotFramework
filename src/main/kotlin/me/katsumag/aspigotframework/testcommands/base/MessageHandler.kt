package me.katsumag.aspigotframework.testcommands.base

import me.katsumag.aspigotframework.extensions.colour
import me.katsumag.aspigotframework.testcommands.base.components.MessageResolver
import me.katsumag.aspigotframework.testcommands.exceptions.CommandException
import org.bukkit.command.CommandSender

class MessageHandler {

    private val messages = mutableMapOf<String, MessageResolver>()

    init {
        register("cmd.no.permission") { it.sendMessage("&cYou don't have permission to execute this command!".colour()) }
        register("cmd.no.console") { it.sendMessage("&cCommand can't be executed through the console!".colour()) }
        register("cmd.no.player") { it.sendMessage("&cCommand can only be executed through the console!".colour()) }
        register("cmd.no.exists") { it.sendMessage("&cThe command you're trying to use doesn't exist!".colour()) }
        register("cmd.wrong.usage") { it.sendMessage("&cWrong usage for the command!".colour()) }
    }

    fun register(id: String, resolver: MessageResolver) = messages.put(id, resolver)

    fun hasId(id: String) = messages.containsKey(id)

    fun sendMessage(id: String, sender: CommandSender) = messages[id]?.resolve(sender) ?: throw CommandException("The message ID $id does not exist!")

}