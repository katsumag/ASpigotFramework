package me.katsumag.aspigotframework

import me.katsumag.aspigotframework.modules.itembuilder.*
import me.katsumag.aspigotframework.modules.listeners.createListener
import org.bukkit.Material
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.plugin.java.JavaPlugin

class KatLib : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        createListener<PlayerAdvancementDoneEvent>(this) {
            //your code here
            Material.OAK_WOOD.toItemStack()
                    .setItemAmount(64)
                    .setDisplayName("&4&lTest!")
                    .setLore("This", "is", "a", "&4&lTest!")
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}