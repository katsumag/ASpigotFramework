package me.katsumag.aspigotframework.modules.worldbuilder.factories

import me.katsumag.aspigotframework.modules.worldbuilder.generators.SuperflatWorldGenerator
import me.katsumag.aspigotframework.modules.worldbuilder.options.LayerOptions
import org.bukkit.World
import org.bukkit.WorldCreator

class SuperflatWorldFactory(private val options: LayerOptions<SuperflatWorldGenerator>) {

    fun generate(name: String): World? {
        val creator = WorldCreator(name)
        creator.generator(SuperflatWorldGenerator(options))
        return creator.createWorld()
    }

}