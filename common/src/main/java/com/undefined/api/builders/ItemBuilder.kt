package com.undefined.api.builders

import com.undefined.api.extension.string.asItemStack
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*
import kotlin.collections.HashMap

/**
 * Represents a builder for creating ItemStack objects.
 *
 * The ItemBuilder class provides methods to conveniently set various properties of an ItemStack object,
 * such as display name, lore, amount, custom model data, and localized name.
 */
class ItemBuilder(private var itemStack: ItemStack) {

    var name: String? = null
    var lore: MutableList<String> = mutableListOf()
    var amount: Int = 1
    var customModelData: Int = 0
    var localizedName: String = ""
    var enchants: HashMap<Enchantment, Int> = HashMap()
    var unbreakable: Boolean = false
    var skullowner: UUID? = null
    var flags: MutableList<ItemFlag> = mutableListOf()

    /**
     * Constructs a new instance of an item using the specified material.
     *
     * @param material The material of the item.
     */
    constructor(material: Material): this(ItemStack(material))

    /**
     * Creates a new instance of the class using a base64 string representation of an ItemStack.
     *
     * @param base64String The base64 string representation of an ItemStack.
     */
    constructor(base64String: String): this(base64String.asItemStack())


    /**
     * Sets the name of the ItemStack.
     *
     * @param name the name to set for the ItemStack
     * @return the ItemBuilder instance
     */
    fun setName(name: String): com.undefined.api.builders.ItemBuilder {
        this.name = name
        return this
    }

    /**
     * Adds an item flag to the ItemBuilder.
     *
     * @param flag
     */
    fun addFlags(flag: ItemFlag): com.undefined.api.builders.ItemBuilder {
        flags.add(flag)
        return this
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level The level of the enchantment.
     * @return The updated item builder instance.
     */
    fun addEnchant(enchantment: Enchantment, level: Int): com.undefined.api.builders.ItemBuilder {
        this.enchants.set(enchantment, level)
        return this
    }

    /**
     * Setter method to set the enchantments of an ItemBuilder object.
     *
     * @param enchantment A HashMap containing the Enchantment objects as keys and the level of the enchantments as values.
     * @return The current ItemBuilder object after setting the enchantments.
     */
    fun setEnchants(enchantment: HashMap<Enchantment, Int>): com.undefined.api.builders.ItemBuilder {
        this.enchants = enchantment
        return this
    }

    /**
     * Sets the unbreakable state of the item.
     *
     * @param boolean true if the item should be unbreakable, false otherwise
     * @return the ItemBuilder instance
     */
    fun setUnbreakable(boolean: Boolean): com.undefined.api.builders.ItemBuilder {
        unbreakable = boolean
        return this
    }

    /**
     * Sets the owner of the skull.
     *
     * @param uuid The UUID of the skull owner.
     * @return The updated ItemBuilder instance.
     */
    fun setSkullOwner(uuid: UUID): com.undefined.api.builders.ItemBuilder {
        this.skullowner = uuid
        return this
    }

    /**
     * Sets the lore of the item.
     *
     * @param lore The list of components representing the lore of the item.
     * @return The ItemBuilder instance.
     */
    fun setLore(lore: MutableList<String>): com.undefined.api.builders.ItemBuilder {
        this.lore = lore
        return this
    }

    fun addLine(line: String): com.undefined.api.builders.ItemBuilder {
        this.lore.add(line)
        return this
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount the desired amount of the item
     * @return the ItemBuilder object for method chaining
     */
    fun setAmount(amount: Int): com.undefined.api.builders.ItemBuilder {
        this.amount = amount
        return this
    }

    /**
     * Sets the custom model data for the item.
     *
     * @param customModelData The custom model data value to set.
     * @return The ItemBuilder object.
     */
    fun setCustomModelData(customModelData: Int): com.undefined.api.builders.ItemBuilder {
        this.customModelData = customModelData
        return this
    }

    /**
     * Sets the localized name of the item.
     *
     * @param name The localized name to set.
     * @return The ItemBuilder instance.
     */
    fun setLocalizedName(name: String): com.undefined.api.builders.ItemBuilder {
        this.localizedName = name
        return this
    }

    /**
     * Constructs and returns an ItemStack object.
     *
     * @return The constructed ItemStack object.
     */
    fun build(): ItemStack {
        itemStack.amount = amount
        var itemMeta = itemStack.itemMeta ?: return itemStack

        if (name != null) itemMeta.setDisplayName(name!!)

        itemMeta.lore = lore
        itemMeta.setCustomModelData(customModelData)
        itemMeta.setLocalizedName(localizedName)

        if (enchants.isNotEmpty()) enchants.forEach{
            itemMeta.addEnchant(it.key, it.value, true)
        }

        itemMeta.isUnbreakable = unbreakable

        if (skullowner != null)
            if (itemStack.type == Material.PLAYER_HEAD){
                val meta = itemMeta as SkullMeta
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(skullowner!!))
                itemMeta = meta
            }

        flags.forEach {
            itemMeta.addItemFlags(it)
        }

        itemStack.itemMeta = itemMeta

        return itemStack
    }

}