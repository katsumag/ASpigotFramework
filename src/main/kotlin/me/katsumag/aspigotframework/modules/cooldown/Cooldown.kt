package me.katsumag.aspigotframework.modules.cooldown

import me.katsumag.aspigotframework.extensions.bukkitRunnable
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

class Cooldown(private val plugin: JavaPlugin, private val time: String) {

    private val actions = mutableListOf<(Cooldown) -> Unit>()
    var timeRemaining = time.parseTime().to(TimeUnit.SECONDS) * 20
    var isExpired = false
    private val runnable = bukkitRunnable {

        if (timeRemaining > 0) {
            actions.forEach { it.invoke(this@Cooldown) }
            return@bukkitRunnable
        }
        isExpired = true
        cancel()
    }

    fun addAction(callback: (Cooldown) -> Unit) = actions.add(callback)


    fun start() = runnable.runTaskTimer(plugin, 1, 1)

    fun startAsync() = runnable.runTaskAsynchronously(plugin)

    fun pause() = runnable.cancel()


    fun pause(time: Long) {
        pause()
        runnable.runTaskLater(plugin, time)
    }

    fun cancel() = pause()

}