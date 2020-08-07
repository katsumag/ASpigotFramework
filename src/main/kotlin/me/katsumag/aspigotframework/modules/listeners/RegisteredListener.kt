package me.katsumag.aspigotframework.modules.listeners

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.BiConsumer

class RegisteredListener<T: Event> (private val plugin: JavaPlugin, private val actions: ListenerAction<T>) : Listener {

    @EventHandler
    private fun listen(event: T) {
        actions.perform(event)
    }

    init {
        Bukkit.getServer().pluginManager.registerEvents(this, plugin)
    }

    fun unregister() {
        HandlerList.unregisterAll(this)
    }

}

private fun <T : Event> createAction(body: (T) -> Unit): ListenerAction<T> {
    return object : ListenerAction<T> {
        override fun perform(t: T) = body(t)
    }
}

fun <T : Event> createListener(plugin: JavaPlugin, action: (T) -> Unit) = RegisteredListener(plugin, createAction(action))