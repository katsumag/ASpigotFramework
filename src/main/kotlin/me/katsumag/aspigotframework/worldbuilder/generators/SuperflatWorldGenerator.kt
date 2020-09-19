package me.katsumag.aspigotframework.worldbuilder.generators

import me.katsumag.aspigotframework.worldbuilder.options.LayerOptions
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*
import javax.annotation.Nonnull

class SuperflatWorldGenerator(private val options: LayerOptions<SuperflatWorldGenerator>) : ChunkGenerator() {

    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biome: BiomeGrid): ChunkData {

        val chunk = createChunkData(world)

        for (x in 0..15) for (z in 0..15) {

            options.options.forEach { layer ->
                layer.layers.forEach {
                    chunk.setBlock(x, it, z, layer.material)
                }
            }

            /*
            0 -> chunk.setBlock(x, y, z, Material.BEDROCK)
            1 -> chunk.setBlock(x, y, z, Material.STONE)
            2 -> chunk.setBlock(x, y, z, Material.DIRT)
            3 -> chunk.setBlock(x, y, z, Material.GRASS_BLOCK)*/
        }

        return chunk
    }

}