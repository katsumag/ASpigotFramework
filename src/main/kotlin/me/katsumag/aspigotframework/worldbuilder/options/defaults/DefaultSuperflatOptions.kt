package me.katsumag.aspigotframework.worldbuilder.options.defaults

import me.katsumag.aspigotframework.worldbuilder.generators.SuperflatWorldGenerator
import me.katsumag.aspigotframework.worldbuilder.options.LayerOption
import me.katsumag.aspigotframework.worldbuilder.options.LayerOptions
import org.bukkit.Material

class DefaultSuperflatOptions {

    val options = LayerOptions<SuperflatWorldGenerator>(
            LayerOption(Material.BEDROCK).addY(0),
            LayerOption(Material.STONE).addY(1),
            LayerOption(Material.DIRT).addY(2),
            LayerOption(Material.GRASS_BLOCK).addY(3)
    )

}