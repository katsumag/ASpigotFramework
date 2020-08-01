package me.katsumag.aspigotframework

import me.katsumag.aspigotframework.modules.commands.Command
import me.katsumag.aspigotframework.modules.listeners.createListener
import me.katsumag.aspigotframework.modules.worldbuilder.builders.SuperflatWorldBuilder
import me.katsumag.aspigotframework.modules.worldbuilder.builders.VoidWorldBuilder
import me.katsumag.aspigotframework.modules.worldbuilder.options.LayerOption
import me.katsumag.aspigotframework.modules.worldbuilder.options.LayerOptions
import me.katsumag.aspigotframework.modules.worldbuilder.options.defaults.DefaultSuperflatOptions
import org.bukkit.Material
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.plugin.java.JavaPlugin

class KatLib : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        createListener<PlayerAdvancementDoneEvent>(this) {
            //your code here
        }

        val abc = Command("abc") {

        }

        val voidWorld = VoidWorldBuilder("name").generate()
        val defaultSuperflatWorld = SuperflatWorldBuilder(DefaultSuperflatOptions().options).generate("name")
        val customSuperflatWorld = SuperflatWorldBuilder(LayerOptions(
                LayerOption(Material.GRASS_BLOCK).addY(2, 10, 5),
                LayerOption(Material.BEDROCK).addY(0),
                LayerOption(Material.DIRT).addY(3, 4, 5)
        )).generate("name")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}