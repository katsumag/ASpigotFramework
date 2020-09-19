package me.katsumag.aspigotframework.holograms

import me.katsumag.aspigotframework.listeners.createListener
import org.bukkit.entity.ArmorStand
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.plugin.java.JavaPlugin

object HologramHandler {

    private val _holograms = mutableListOf<Hologram>()
    private val _stands = mutableListOf<ArmorStand>()
    val stands: List<ArmorStand> get() = _stands
    val holograms: List<Hologram> get() = _holograms

    fun register(vararg hologram: Hologram) {
        _holograms.addAll(hologram)
        _stands.addAll(hologram.map { it.stand })
    }

    fun unregister(vararg hologram: Hologram) {
        _holograms.removeAll(hologram)
        _stands.removeAll(hologram.map { it.stand})
    }


    init {
        createListener<PlayerArmorStandManipulateEvent>(JavaPlugin.getProvidingPlugin(HologramHandler::class.java)) {

            if (! it.rightClicked.isVisible) return@createListener
            if (stands.contains(it.rightClicked)) it.isCancelled = true

        }
    }

}