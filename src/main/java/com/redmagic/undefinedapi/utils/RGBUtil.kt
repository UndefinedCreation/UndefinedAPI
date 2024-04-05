package com.redmagic.undefinedapi.utils

class RGBUtil{
    companion object{
        fun rgbToHex(red: Int, green: Int, blue: Int) =
            "#${red.toString(16).padStart(2, '0')}${green.toString(16).padStart(2, '0')}${blue.toString(16).padStart(2, '0')}".uppercase()

    }
}