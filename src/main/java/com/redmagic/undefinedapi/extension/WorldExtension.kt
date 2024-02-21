package com.redmagic.undefinedapi.extension

import org.bukkit.Location
import org.bukkit.World
import java.util.Random

fun World.randomLocation(xRange: Double, zRange: Double): Location{

    val startLoc = Location(this, 0.0, 0.0, 0.0)

    val x = Random().nextDouble(xRange * -1, xRange)
    val z = Random().nextDouble(zRange * -1, zRange)

    startLoc.x = x
    startLoc.z = z

    when(environment){
        World.Environment.NETHER -> {
            for (y in 0..127){
                if (!getBlockAt(x.toInt(), y, z.toInt()).type.isAir) continue
                startLoc.y = y.toDouble()
                break
            }
        }
        World.Environment.THE_END ->{
            startLoc.y = this.getHighestBlockAt(x.toInt(), z.toInt()).y.toDouble()
        }
        else -> startLoc.y = this.getHighestBlockAt(x.toInt(), z.toInt()).y.toDouble()
    }
    return startLoc
}

fun World.randomLocation(range: Double): Location = randomLocation(range, range)
fun World.randomLocation(): Location = randomLocation(worldBorder.size / 2, worldBorder.size / 2)