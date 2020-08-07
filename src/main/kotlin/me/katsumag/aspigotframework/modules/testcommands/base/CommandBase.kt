package me.katsumag.aspigotframework.modules.testcommands.base

import org.bukkit.command.CommandSender

abstract class CommandBase {

    private val args = mutableMapOf<String, String>()
    var messageHandler: MessageHandler? = null
    private val _aliases = mutableListOf<String>()
    val aliases: List<String> get() = _aliases

    fun onRegister() {}

    fun setAliases(aliases: List<String>) = this._aliases.addAll(aliases)

    fun getArgument(name: String) = args.getOrDefault(name, null)

    fun sendMessage(id: String, sender: CommandSender) = messageHandler?.sendMessage(id, sender)

    fun clearArgs() = args.clear()

    fun addArgument(name: String, arg: String) = args.put(name, arg)

}