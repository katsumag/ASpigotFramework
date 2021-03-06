package me.katsumag.aspigotframework.itembuilder

import me.katsumag.aspigotframework.extensions.colour
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

inline fun ItemStack.transformMeta(transformer: ItemMeta.() -> Unit) {
    val meta = itemMeta
    meta?.transformer()
    itemMeta = meta
}

fun Material.toItemStack(): ItemStack {
    return ItemStack(this)
}

fun ItemStack.setItemAmount(amount: Int): ItemStack {
    this.amount = amount
    return this
}

fun ItemStack.setDisplayName(name: String): ItemStack {
    transformMeta {
        setDisplayName(name.colour())
    }

    return this
}

fun ItemStack.setLore(vararg lore: String): ItemStack {
    transformMeta {
        setLore(lore.toList())
    }
    return this
}

fun ItemStack.setItemLore(lore: Array<String>): ItemStack {
    transformMeta {
        setLore(lore.toList())
    }
    return this
}

fun ItemStack.setData(data: Int): ItemStack {
    val damageable = this.itemMeta as Damageable
    damageable.damage = data
    return this
}

fun getPlayerHead(uuid: UUID): ItemStack {
    return Skull.getPlayerSkull(Bukkit.getOfflinePlayer(uuid))
}
