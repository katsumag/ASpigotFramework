package me.katsumag.aspigotframework.modules.worldbuilder.options

import org.bukkit.generator.ChunkGenerator

class LayerOptions<T: ChunkGenerator>(vararg option: LayerOption) {

    private val _options = mutableListOf<LayerOption>()
    val options: List<LayerOption> get() = _options

    init {
        _options.addAll(option)
    }
    fun removeOption(option: LayerOption) {
        _options.remove(option)
    }

    fun clearOptions() {
        _options.clear()
    }

}