package me.katsumag.aspigotframework.modules.nms

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.plugin.SimplePluginManager

object NMSHelper {

    fun getCommandMap(): CommandMap {
        val mapField = SimplePluginManager::class.java.getDeclaredField("commandMap")
        mapField.isAccessible = true
        return mapField.get(Bukkit.getServer().pluginManager) as CommandMap
    }

}