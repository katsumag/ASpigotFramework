package me.katsumag.aspigotframework.testcommands.base.components

import org.bukkit.command.CommandSender

fun interface MessageResolver {

    fun resolve(sender: CommandSender)

}