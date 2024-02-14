package com.redmagic.undefinedapi

import kotlin.random.Random

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

private fun getTotalWeight(map: Map<Any, Double>): Double {
    var total = 0.0
    map.forEach{total += it.value}
    return total
}