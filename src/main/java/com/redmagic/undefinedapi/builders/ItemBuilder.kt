package com.redmagic.undefinedapi.builders

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Represents a builder for creating ItemStack objects.
 *
 * The ItemBuilder class provides methods to conveniently set various properties of an ItemStack object,
 * such as display name, lore, amount, custom model data, and localized name.
 */
class ItemBuilder(private var itemStack: ItemStack) {

    constructor(material: Material): this(ItemStack(material))


    /**
     * Sets the display name of the item.
     *
     * @param name The name to set as the display name of the item. This should be a Component object.
     * @return This ItemBuilder instance.
     */
    fun setName(name:Component):ItemBuilder{
        itemStack.itemMeta = itemStack.itemMeta.apply { displayName(name) }
        return this
    }
    /**
     * Sets the name of the ItemStack.
     *
     * @param name the name to set for the ItemStack
     * @return the ItemBuilder instance
     */
    fun setName(name:String):ItemBuilder{
        itemStack.itemMeta = itemStack.itemMeta.apply { setDisplayName(name) }
        return this
    }

    /**
     * Sets the lore of the item.
     *
     * @param lore The list of components representing the lore of the item.
     * @return The ItemBuilder instance.
     */
    fun setLore(lore:MutableList<Component>):ItemBuilder{
        itemStack.itemMeta = itemStack.itemMeta.apply { lore(lore) }
        return this
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount the desired amount of the item
     * @return the ItemBuilder object for method chaining
     */
    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    /**
     * Sets the custom model data for the item.
     *
     * @param customModelData The custom model data value to set.
     * @return The ItemBuilder object.
     */
    fun setCustomModelData(customModelData:Int):ItemBuilder{
        itemStack.itemMeta = itemStack.itemMeta.apply { setCustomModelData(customModelData) }
        return this
    }

    /**
     * Sets the localized name of the item.
     *
     * @param name The localized name to set.
     * @return The ItemBuilder instance.
     */
    fun setLocalizedName(name:String):ItemBuilder{
        itemStack.itemMeta = itemStack.itemMeta.apply { setLocalizedName(name) }
        return this
    }

    /**
     * Constructs and returns an ItemStack object.
     *
     * @return The constructed ItemStack object.
     */
    fun build(): ItemStack {
        return itemStack
    }

}