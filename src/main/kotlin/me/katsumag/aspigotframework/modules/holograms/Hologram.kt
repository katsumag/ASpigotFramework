package me.katsumag.aspigotframework.modules.holograms

import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin

class Hologram(data: HologramData) {

    val stand = createStand(data)

    init {
        HologramHandler.register(this)

        if (data.expireType == ExpireType.TIMED) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(Hologram::class.java), { stand.remove() }, data.expireType.time)
        }

    }

    fun remove() {
        stand.remove()
        HologramHandler.unregister(this)
    }

    fun setName(name: String) { stand.customName = name }

}

fun createStand(hologramData: HologramData): ArmorStand {

    val stand = hologramData.location.world?.spawnEntity(hologramData.location, EntityType.ARMOR_STAND) as ArmorStand
    stand.setGravity(false)
    stand.canPickupItems = false
    stand.customName = hologramData.name
    stand.isCustomNameVisible = true
    stand.isInvulnerable = true
    stand.isVisible = false

    return stand
}