package me.katsumag.aspigotframework.modules.worldbuilder.options

import org.bukkit.Material

class LayerOption(val material: Material) {

    private val _layers = mutableListOf<Int>()
    val layers: List<Int>
        get() = _layers

    fun addY(y: Int): LayerOption {
        _layers.add(y)
        return this
    }

    fun addY(vararg y: Int): LayerOption {
        y.forEach { addY(it) }
        return this
    }

}