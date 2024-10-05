package com.undefined.api.extension

import com.undefined.api.UndefinedAPI
import com.undefined.api.scheduler.TimeUnit
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player

/**
 * Sends an action bar message to the player.
 *
 * @param string The action bar message to be sent.
 * @param time The duration for which the action bar message should be displayed.
 * @param timeUnit The time unit in which the duration is measured. (Default: [TimeUnit.TICKS])
 *
 * @throws IllegalArgumentException if the provided `time` parameter is negative.
 */
fun Player.sendActionBar(string: String, time: Int, timeUnit: TimeUnit = TimeUnit.TICKS) {
    com.undefined.api.scheduler.repeatingTask(
        1,
        timeUnit.toTicks(time.toLong()).toInt()
    ) { this@sendActionBar.sendActionBar(string) }
}

/**
 * Sends an action bar message to the player.
 *
 * @param string The message to be displayed in the action bar.
 */
fun Player.sendActionBar(string: String){
    this.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(string
    ))
}
/**
 * Sends an action bar message to the player.
 *
 * @param string The message to be displayed in the action bar.
 * @param time The duration in ticks for which the message should be displayed.
 */
fun Player.sendActionBar(string: String, time: Int) = sendActionBar(string, time, TimeUnit.TICKS)


/**
 * Sets the food level of the player to the maximum value (20).
 * This method is used to feed the player, replenishing their hunger.
 */
fun Player.feed() { foodLevel = 20 }

/**
 * Restores the player's health to its maximum value.
 *
 * This method sets the player's health to the maximum health value specified by the "maxHealth" property.
 * After calling this method, the player will be fully healed and have no health deficit.
 *
 */
fun Player.heal() { health = this.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value }

/**
 * Resets the walk speed of the player to the default value.
 *
 * The walk speed is a measure of how fast the player can move
 * while walking. By calling this method, the walk speed of the
 * player will be set to the default value of 0.2F.
 *
 * Example usage:
 * ```
 * player.resetWalkSpeed()
 * ```
 */
fun Player.resetWalkSpeed() { walkSpeed = 0.2F }
/**
 * Resets the fly speed of the player to the default value.
 * The default fly speed is 0.1F.
 *
 */
fun Player.resetFlySpeed() { flySpeed = 0.1F }

/**
 * Hides the player from all online players in the game.
 *
 * This method uses the Bukkit API to loop through all online players
 * and hides the current player from each of them.
 *
 * Please note that this method must be called on a Player object.
 *
 * Example usage:
 * ```
 * player.hidePlayer()
 * ```
 */
fun Player.hidePlayer() = Bukkit.getOnlinePlayers().forEach{
    it.hidePlayer(UndefinedAPI.plugin, this)
}
/**
 * Displays the player to all online players.
 */
fun Player.showPlayer() = Bukkit.getOnlinePlayers().forEach{
    it.showPlayer(UndefinedAPI.plugin, this)
}
/**
 * Removes all active potion effects from the player.
 */
fun Player.removeActivePotionEffects() {
    activePotionEffects.forEach { removePotionEffect(it.type) }
}

/**
 * Sends a message to the player.
 *
 * @param component the message component to send
 */
fun Player.sendMessage(component: Component) = UndefinedAPI.adventure().player(this).sendMessage(component)

/**
 * Sends an action bar message to the player.
 *
 * @param component The message to be displayed as the action bar, represented as a [Component].
 */
fun Player.sendActionBar(component: Component) = UndefinedAPI.adventure().player(this).sendActionBar(component)


fun Player.sendBlockUpdateArray(hashMap: HashMap<Location, BlockData>) {
    sendBlockUpdateArray(hashMap.keys.toList(), hashMap.values.toList())
}

fun Player.sendBlockUpdateArray(locs: List<Location>, data: List<BlockData>, persistent: Boolean = true) {
    listOf(this).sendBlockUpdateArray(locs, data, persistent)
}

fun List<Player>.sendBlockUpdateArray(locs: List<Location>, data: List<BlockData>, persistent: Boolean) {
    when(getNMSVersion()) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.BlockExtension.setBlockArray(data.toTypedArray(), locs, this, persistent)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.BlockExtension.setBlockArray(data.toTypedArray(), locs, this, persistent)
        "1.21" -> com.undefined.api.nms.v1_21.extensions.BlockExtension.setBlockArray(data.toTypedArray(), locs, this, persistent)
    }
}

fun Player.sendClearFakeBlock() {
    when(getNMSVersion()) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.BlockExtension.clearFakeBlocks(this)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.BlockExtension.clearFakeBlocks(this)
        "1.21" -> com.undefined.api.nms.v1_21.extensions.BlockExtension.clearFakeBlocks(this)
    }
}