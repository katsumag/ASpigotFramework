package me.katsumag.aspigotframework.modules.worldbuilder.generators

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*
import javax.annotation.Nonnull

class VoidWordGenerator : ChunkGenerator() {

    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData = createChunkData(world)

}