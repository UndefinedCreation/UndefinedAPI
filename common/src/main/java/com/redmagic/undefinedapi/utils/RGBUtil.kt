package com.redmagic.undefinedapi.utils

/**
 * Converts RGB values to a hexadecimal color code.
 *
 * @param red The red component of the RGB color (0-255).
 * @param green The green component of the RGB color (0-255).
 * @param blue The blue component of the RGB color (0-255).
 * @return The hexadecimal color code in the format "#RRGGBB".
 *
 * @since 1.0.0
 */
fun rgbToHex(red: Int, green: Int, blue: Int) =
    "#${red.toString(16).padStart(2, '0')}${green.toString(16).padStart(2, '0')}${blue.toString(16).padStart(2, '0')}".uppercase()
