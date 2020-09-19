package me.katsumag.aspigotframework.listeners

import org.bukkit.event.Event

@FunctionalInterface
interface ListenerAction<T: Event> {

    fun perform(t: T)

}