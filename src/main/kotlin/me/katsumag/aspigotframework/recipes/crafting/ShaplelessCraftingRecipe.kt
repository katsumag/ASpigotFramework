package me.katsumag.aspigotframework.recipes.crafting

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

class ShapelessCraftingRecipe(private val itemStack: ItemStack, private val key: NamespacedKey) {

    private val recipe = ShapelessRecipe(key, itemStack)

    fun setIngredients(vararg items: Material) { items.forEach { recipe.addIngredient(it) } }

}