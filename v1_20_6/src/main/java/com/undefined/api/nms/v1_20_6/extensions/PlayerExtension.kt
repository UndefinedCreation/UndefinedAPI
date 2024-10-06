package com.undefined.api.nms.v1_20_6.extensions

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.stats.Stats
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Items
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.inventory.ItemStack

/**
 * Sends the given packets to each player in the list.
 *
 * @param packet the packets to send
 *
 * @param Player.sendPacket
 * Sends the given packets to the player.
 *
 * @param packet the packets to send
 *
 * @see Player.getConnection
 */
fun List<Player>.sendPacket(vararg packet: Packet<*>) = forEach { it.sendPacketArray(packet) }

/**
 * Returns the connection to the server for the player.
 *
 * @return the ServerGamePacketListenerImpl object representing the player's connection
 */
fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

/**
 * Sends the specified packets to the player's connection.
 *
 * @param packet the packets to send
 */
fun Player.sendPacket(vararg packet: Packet<*>) = packet.forEach { getConnection().sendPacket(it) }

/**
 * Sends packets to the player.
 *
 * @param packet the array of packets to be sent
 */
fun Player.sendPacketArray(packet: Array<out Packet<*>>) = packet.forEach { getConnection().sendPacket(it) }

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
 * Determines whether the current execution environment has remapped the ServerboundSwingPacket class.
 *
 * @return true if the ServerboundSwingPacket class is remapped, false otherwise.
 */
fun isRemapped(): Boolean = ServerboundSwingPacket::class.java.name == "net.minecraft.network.protocol.game.ServerboundSwingPacket"