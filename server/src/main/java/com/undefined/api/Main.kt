package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.extension.sendActionBar
import com.undefined.api.scheduler.TimeUnit
import com.undefined.api.utils.item.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        UndefinedCommand("test")
            .addIncorrectMessage("test")
            .addSubCommand("hi")
            .addIncorrectMessage("hi")
            .addSubCommand("hi")
            .addIncorrectMessage("hi")
            .addExecutePlayer {
                sendActionBar(Component.text("hi!"), 1, TimeUnit.SECONDS)
                val item = ItemBuilder(Material.DIAMOND_SWORD)
                    .setName("<reset><red>Test!")
                    .addLore(Component.text("test"))
                    .addAmount(2)
                    .setCustomModelData(2)
                    .addPersistentDataType(NamespacedKey(this@Main, "test"), PersistentDataType.BOOLEAN, true)
                    .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                    .addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 7)
                    .setUnbreakable(true)
                    .setSkullOwner(player!!.uniqueId)
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, AttributeModifier("generic.max_health", 100.0, AttributeModifier.Operation.ADD_NUMBER))
                    .build()
                player!!.inventory.addItem(item)
                return@addExecutePlayer true
            }
    }
}