package me.katsumag.aspigotframework.recipes.crafting

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

class ShapedCraftingRecipe(private val itemStack: ItemStack, private val key: NamespacedKey, private val shape: CraftingShape) {

    private val recipe = ShapedRecipe(key, itemStack)

    init {

        shape.items.forEach { recipe.setIngredient(it.key, it.value) }
        Bukkit.getServer().addRecipe(recipe)

    }

}