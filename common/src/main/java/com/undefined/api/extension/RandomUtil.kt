package com.undefined.api.extension

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.random.Random

/**
 * Returns a randomly selected element from the given map, weighted based on the provided weights.
 *
 * @param map The map containing the elements and their corresponding weights.
 * @return A randomly chosen element from the map.
 */
fun Random.weightedRandom(map: Map<Any, Double>): Any{
    val totalWeight = getTotalWeight(map)
    var randomWeight = this.nextDouble(totalWeight)
    for ((item, weight) in map) {
        randomWeight -= weight
        if (randomWeight <= 0) {
            return item
        }
    }
    throw IllegalArgumentException("The map is empty.")
}

/**
 * Calculates the total weight of a given map.
 *
 * @param map The map containing items as keys and their respective weights as values.
 * @return The total weight calculated as the sum of all the weights in the map.
 */
private fun getTotalWeight(map: Map<Any, Double>): Double {
    var total = 0.0
    map.forEach{total += it.value}

    return total
}
