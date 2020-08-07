package me.katsumag.aspigotframework.modules.cooldown

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class CountdownManager(private val plugin: JavaPlugin) {

    private val COUNTDOWNS = mutableMapOf<UUID, Cooldown>()


    fun addCooldown(p: Player, duration: String): Cooldown {
        val countdown = Cooldown(plugin, duration)
        COUNTDOWNS[p.uniqueId] = countdown
        return countdown
    }

    fun getCooldown(p: Player): Cooldown? {
        return COUNTDOWNS[p.uniqueId]
    }

}