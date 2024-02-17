package com.redmagic.undefinedapi.extension

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.scheduler.TimeUnit
import com.redmagic.undefinedapi.scheduler.repeatingTask
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Sends an action bar message to the player.
 *
 * @param component The component representing the action bar message to be sent.
 * @param time The duration for which the action bar message should be displayed.
 * @param timeUnit The time unit in which the duration is measured.
 *
 * @throws IllegalArgumentException if the provided `time` parameter is negative.
 */
fun Player.sendActionBar(component: Component, time: Int, timeUnit: TimeUnit = TimeUnit.TICKS) = repeatingTask(1, timeUnit.toTicks(time.toLong()).toInt()){ sendActionBar(component) }
/**
 * Sends an action bar message to the player for a specified duration.
 *
 * @param component The action bar message to send.
 * @param time The duration in the specified time unit for which the action bar message should be displayed.
 */
fun Player.sendActionBar(component: Component, time: Int) = sendActionBar(component, time, TimeUnit.TICKS)
/**
 * Sends an action bar message to the player for a specific duration.
 *
 * @param string The text content of the action bar message.
 * @param time The duration in ticks for which the action bar message should be displayed.
 */
fun Player.sendActionBar(string: String, time: Int) = sendActionBar(Component.text(string), time)
/**
 * Sends an action bar message to the player for a specified duration.
 *
 * @param string The message to be displayed in the action bar.
 * @param time The duration to display the message, in the specified time unit.
 * @param timeUnit The time unit for the duration (default is `TimeUnit.TICKS`).
 */
fun Player.sendActionBar(string: String, time: Int, timeUnit: TimeUnit = TimeUnit.TICKS) = sendActionBar(Component.text(string), time, timeUnit)

/**
 * Sets the food level of the player to the maximum value.
 */
fun Player.feed() { foodLevel = 20 }

/**
 * Restores the player's health to the maximum health value.
 */
fun Player.heal() { health = maxHealth }

/**
 * Resets the walk speed of the player to the default value.
 */
fun Player.resetWalkSpeed() { walkSpeed = 0.2F }
/**
 * Resets the fly speed of the player.
 * The fly speed is set to the default value of 0.1F.
 */
fun Player.resetFlySpeed() { flySpeed = 0.1F }

/**
 * Hides the player from all online players.
 *
 * This method will loop through all online players and hide the current player from each of them.
 *
 * @receiver The player to hide.
 * @see Player.showPlayer
 */
fun Player.hidePlayer() = Bukkit.getOnlinePlayers().forEach{
    it.hidePlayer(UndefinedAPI.plugin, this)
}
/**
 * This method is used to show the player to all online players on the server.
 * It iterates over all online players and calls the `showPlayer` method on each player.
 * The `showPlayer` method is a method provided by the Bukkit API to show a player to another player.
 * It takes two parameters - the plugin instance and the player to be shown.
 *
 * @see [Bukkit.getOnlinePlayers]
 * @see [Player.showPlayer]
 * @see [UndefinedAPI.plugin]
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