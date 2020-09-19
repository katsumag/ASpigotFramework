package me.katsumag.aspigotframework.itembuilder

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemFactory(private var itemStack: ItemStack) {

    companion object {
        fun getPlayerSkull(uuid: UUID): ItemFactory {
            return ItemFactory(getPlayerHead(uuid))
        }
    }

    constructor(material: Material) : this(ItemStack(material))

    fun setDisplayName(name: String): ItemFactory {
        itemStack = itemStack.setDisplayName(name)
        return this
    }

    fun setLore(vararg lore: String): ItemFactory {
        itemStack.setItemLore(lore as Array<String>)
        return this
    }

    fun setAmount(amount: Int): ItemFactory {
        itemStack.setItemAmount(amount)
        return this
    }

    fun setData(data: Int): ItemFactory {
        itemStack.setData(data)
        return this
    }

    fun build(): ItemStack {
        return itemStack
    }

}