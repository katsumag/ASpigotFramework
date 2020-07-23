package me.katsumag.aspigotframework

import me.katsumag.aspigotframework.modules.listeners.createListener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.plugin.java.JavaPlugin

class KatLib : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        createListener<PlayerAdvancementDoneEvent>(this) {
            //your code here
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}