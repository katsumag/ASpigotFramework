package me.katsumag.aspigotframework.recipes.crafting

import org.bukkit.Material

class CraftingShape(top: String, middle: String, bottom: String) {

    private var _items = mutableMapOf<Char, Material>()
    val items: Map<Char, Material> get() = _items

    fun setIngredient(char: Char, item: Material) = _items.put(char, item)

    fun setIngredients(ingredients: Map<Char, Material>) { _items = ingredients.toMutableMap() }

}