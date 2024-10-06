package com.undefined.api.extension

import org.bukkit.Location
import org.bukkit.World
import java.util.Random

/**
 * Generates a random location within the specified range in the world.
 *
 * @param xRange The range of x coordinates (positive and negative) within which the location should be generated.
 * @param zRange The range of z coordinates (positive and negative) within which the location should be generated.
 * @return A randomly generated Location object.
 */
fun World.randomLocation(xRange: Double, zRange: Double): Location = Location(this, 0.0, 0.0, 0.0).apply {
    x = Random().nextDouble(xRange * -1, xRange)
    z = Random().nextDouble(zRange * -1, zRange)
    y = when(environment) {
        World.Environment.NETHER -> (0..127).firstOrNull { getBlockAt(x.toInt(), it, z.toInt()).type.isAir }?.toDouble() ?: 0.0
        else -> getHighestBlockAt(x.toInt(), z.toInt()).y.toDouble()
    }
}

/**
 * Generates a random location within the specified range in the world.
 *
 * @param range The range of x and z coordinates (positive and negative) within which the location should be generated.
 * @return A randomly generated Location object.
 */
fun World.randomLocation(range: Double): Location = randomLocation(range, range)

/**
 * Generates a random location within the world.
 *
 * @return A randomly generated [Location] object.
 */
fun World.randomLocation(): Location = randomLocation(worldBorder.size / 2, worldBorder.size / 2)