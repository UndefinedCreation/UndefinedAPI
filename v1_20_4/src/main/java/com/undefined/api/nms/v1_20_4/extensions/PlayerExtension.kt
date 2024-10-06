package com.undefined.api.nms.v1_20_4.extensions

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.stats.Stats
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Items
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.inventory.ItemStack

/**
 * Sends a packet to each player in the list.
 *
 * @param packet the packet to send
 */
fun List<Player>.sendPacket(vararg packet: Packet<*>) = forEach { it.sendPacketArray(packet) }

/**
 * Retrieves the connection associated with the player.
 *
 * @return the ServerGamePacketListenerImpl representing the player's connection
 */
fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

/**
 * Sends one or more packets to the player's connection.
 *
 * @param packet the packet(s) to send
 */
fun Player.sendPacket(vararg packet: Packet<*>) = packet.forEach { getConnection().send(it) }

/**
 * Sends an array of packets to the player.
 *
 * @param packet the array of packets to be sent
 */
fun Player.sendPacketArray(packet: Array<out Packet<*>>) = packet.forEach { getConnection().send(it) }

object PlayerExtension {

    /**
     * Retrieves the textures of a player.
     *
     * @param player The player whose textures are to be retrieved.
     * @return An array of strings representing the player's texture and signature.
     */
    fun getTextures(player: Player): Array<String> {
        val cPlayer = player as CraftPlayer
        val sPlayer = cPlayer.handle
        val gameProfile = sPlayer.getGameProfile()
        val property = gameProfile.properties["textures"].iterator().next()
        val texture = property.value as String
        val sign = property.signature as String

        return arrayOf(texture, sign)
    }

    /**
     * Triggers the totem if in main hand or offhand.
     *
     * @return if the player had a totem equipped
     */
    fun triggerTotem(player: Player): Boolean {
        val item: ItemStack
        when {
            player.inventory.itemInMainHand.type == Material.TOTEM_OF_UNDYING -> {
                item = player.inventory.itemInMainHand
                player.inventory.itemInMainHand.amount = 0
            }
            player.inventory.itemInOffHand.type == Material.TOTEM_OF_UNDYING -> {
                item = player.inventory.itemInOffHand
                player.inventory.itemInOffHand.amount = 0
            }
            else -> return false
        }

        val serverPlayer: ServerPlayer = (player as CraftPlayer).handle
        serverPlayer.awardStat(Stats.ITEM_USED[Items.TOTEM_OF_UNDYING])
        CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, CraftItemStack.asNMSCopy(item))

        serverPlayer.setHealth(1.0f)

        serverPlayer.removeAllEffects(EntityPotionEffectEvent.Cause.TOTEM)
        serverPlayer.addEffect(MobEffectInstance(MobEffects.REGENERATION, 900, 1), EntityPotionEffectEvent.Cause.TOTEM)
        serverPlayer.addEffect(MobEffectInstance(MobEffects.ABSORPTION, 100, 1), EntityPotionEffectEvent.Cause.TOTEM)
        serverPlayer.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0), EntityPotionEffectEvent.Cause.TOTEM)

        serverPlayer.level().broadcastEntityEvent(serverPlayer, 35.toByte())
        return true
    }

}

/**
 * Checks if the server is running on Paper.
 *
 * @return true if the server is running on Paper, false otherwise.
 */
fun isPaper(): Boolean {
    val hasClass = { name: String -> try { Class.forName(name); true } catch (e: ClassNotFoundException) { false } }
    return hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")
}