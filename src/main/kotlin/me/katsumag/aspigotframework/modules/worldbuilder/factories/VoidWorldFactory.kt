package me.katsumag.aspigotframework.modules.worldbuilder.factories

import me.katsumag.aspigotframework.modules.worldbuilder.generators.VoidWordGenerator
import org.bukkit.World
import org.bukkit.WorldCreator

class VoidWorldFactory(private val name: String) {

    fun generate(): World? {
        val creator = WorldCreator(name)
        creator.generator(VoidWordGenerator())
        return creator.createWorld()
    }

}