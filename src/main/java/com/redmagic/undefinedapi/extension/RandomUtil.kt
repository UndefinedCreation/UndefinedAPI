package com.redmagic.undefinedapi.extension

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
    var idx = 0;
    var r = this.nextDouble() * totalWeight
    while (idx < map.keys.size - 1){
        val  s = map.keys.elementAt(idx)
        r -= map[s] ?: 0.0
        if (r <= 0) break
        ++idx
    }
    return map.keys.elementAt(idx)
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

