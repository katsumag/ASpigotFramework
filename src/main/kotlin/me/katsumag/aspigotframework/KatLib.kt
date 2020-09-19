package me.katsumag.aspigotframework

import me.katsumag.aspigotframework.commands.Command
import me.katsumag.aspigotframework.listeners.createListener
import me.katsumag.aspigotframework.recipes.crafting.CraftingShape
import me.katsumag.aspigotframework.recipes.crafting.ShapedCraftingRecipe
import me.katsumag.aspigotframework.recipes.crafting.ShapelessCraftingRecipe
import me.katsumag.aspigotframework.worldbuilder.factories.SuperflatWorldFactory
import me.katsumag.aspigotframework.worldbuilder.factories.VoidWorldFactory
import me.katsumag.aspigotframework.worldbuilder.options.LayerOption
import me.katsumag.aspigotframework.worldbuilder.options.LayerOptions
import me.katsumag.aspigotframework.worldbuilder.options.defaults.DefaultSuperflatOptions
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class KatLib : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic

        val a = createListener<PlayerAdvancementDoneEvent>(this) {
            //your code here
        }
        a.unregister()

        val abc = Command("abc") {
            it.commandContext.commandSender.sendMessage("AAAAAAAAAAAA")
        }

        abc.addSubCommand("def") {
            it.command.commandContext.commandSender.sendMessage("BBBBBBBBBBBBB")
        }

        abc.register()
        abc.unregister()

        val voidWorld = VoidWorldFactory("name").generate()
        val defaultSuperflatWorld = SuperflatWorldFactory(DefaultSuperflatOptions().options).generate("name")
        val customSuperflatWorld = SuperflatWorldFactory(LayerOptions(
                LayerOption(Material.GRASS_BLOCK).addY(2, 10, 5),
                LayerOption(Material.BEDROCK).addY(0),
                LayerOption(Material.DIRT).addY(3, 4, 5)
        )).generate("name")

        val shape = CraftingShape("%%*", "**(", "(**")
        shape.setIngredient('%', Material.ITEM_FRAME)
        shape.setIngredient('*', Material.ACACIA_BUTTON)
        shape.setIngredient('(', Material.ACACIA_PRESSURE_PLATE)
        val shapedRecipe = ShapedCraftingRecipe(ItemStack(Material.ACACIA_BOAT), NamespacedKey(this, "abc"), shape)

        val shapelessRecipe = ShapelessCraftingRecipe(ItemStack(Material.ACACIA_BOAT), NamespacedKey(this, "abc"))
        shapelessRecipe.setIngredients(Material.ITEM_FRAME, Material.ACACIA_SIGN)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}