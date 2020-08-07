package me.katsumag.aspigotframework

import me.katsumag.aspigotframework.modules.commands.Command
import me.katsumag.aspigotframework.modules.listeners.createListener
import me.katsumag.aspigotframework.modules.worldbuilder.factories.SuperflatWorldFactory
import me.katsumag.aspigotframework.modules.worldbuilder.factories.VoidWorldFactory
import me.katsumag.aspigotframework.modules.worldbuilder.options.LayerOption
import me.katsumag.aspigotframework.modules.worldbuilder.options.LayerOptions
import me.katsumag.aspigotframework.modules.worldbuilder.options.defaults.DefaultSuperflatOptions
import org.bukkit.Material
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.plugin.java.JavaPlugin

class KatLib : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic

        val a = createListener<PlayerAdvancementDoneEvent>(this) {
            //your code here
        }
        a.unregister()

        val abc = Command("abc") {
            it.commandContext.commandSender.sendMessage("AAAAAAAAAAAA")
        }

        abc.addSubCommand("def") {
            it.command.commandContext.commandSender.sendMessage("BBBBBBBBBBBBB")
        }

        abc.register()
        abc.unregister()

        val voidWorld = VoidWorldFactory("name").generate()
        val defaultSuperflatWorld = SuperflatWorldFactory(DefaultSuperflatOptions().options).generate("name")
        val customSuperflatWorld = SuperflatWorldFactory(LayerOptions(
                LayerOption(Material.GRASS_BLOCK).addY(2, 10, 5),
                LayerOption(Material.BEDROCK).addY(0),
                LayerOption(Material.DIRT).addY(3, 4, 5)
        )).generate("name")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}