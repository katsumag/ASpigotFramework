package me.katsumag.aspigotframework

import org.bukkit.Bukkit
import net.md_5.bungee.api.ChatColor
import org.bukkit.scheduler.BukkitRunnable
import java.util.regex.Pattern

val HEX_PATTERN: Pattern = Pattern.compile("#<([A-Fa-f0-9]){6}>")

fun String.colour() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun Array<String>.colour() : Array<String> {
    repeat(size) { index ->
        this[index] = this[index].colour()
    }
    return this
}

fun String.debug() = Bukkit.broadcastMessage("&c(!) [DEBUG] (!)\n&b$this".colour())

fun bukkitRunnable(function: BukkitRunnable.() -> Unit): BukkitRunnable {
    return object: BukkitRunnable() {
        override fun run() {
            function()
        }
    }
}

fun String.translateHex(): String {
    var translation = this

    var matcher = HEX_PATTERN.matcher(translation)

    while (matcher.find()) {
        var hexString = matcher.group()

        hexString = "#" + hexString.substring(2, hexString.length - 1)
        val hex: ChatColor = ChatColor.of(hexString)

        val before = translation.substring(0, matcher.start())
        val after = translation.substring(matcher.end())

        translation = before + hex + after
        matcher = HEX_PATTERN.matcher(translation)
    }

    return ChatColor.translateAlternateColorCodes('&', translation)
}