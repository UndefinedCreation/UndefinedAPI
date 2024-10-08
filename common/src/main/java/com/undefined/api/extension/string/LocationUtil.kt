package com.undefined.api.extension.string

import org.bukkit.Bukkit
import org.bukkit.Location
import java.text.DecimalFormat
import java.util.*


/**
 * Returns a formatted string representation of the Location object.
 * The format parameter specifies the pattern to format the values.
 *
 * @param format the pattern to format the values (default is "0.##")
 * @return the formatted string representation of the Location object
 */
fun Location.asString(format:String = "0.##"): String {
    val decimalFormat = DecimalFormat(format)

    val uuid = this.world!!.uid
    val x = decimalFormat.format(this.x)
    val y = decimalFormat.format(this.y)
    val z = decimalFormat.format(this.z)
    val pitch = decimalFormat.format(this.pitch)
    val yaw = decimalFormat.format(this.yaw)

    return "$uuid;$x;$y;$z;$pitch;$yaw"
}

/**
 * Converts a String representation of a location into a Location object.
 *
 * @return The Location object representing the supplied String.
 */
fun String.asLocation():Location{
    val split = this.split(";")

    val world = Bukkit.getWorld(UUID.fromString(split[0]))
    val x = split[1].toDouble()
    val y = split[2].toDouble()
    val z = split[3].toDouble()
    val pitch = split[4].toFloat()
    val yaw = split[5].toFloat()
    return Location(world,x,y,z,pitch,yaw)
}