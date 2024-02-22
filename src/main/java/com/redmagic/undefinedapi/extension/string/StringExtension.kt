package com.redmagic.undefinedapi.extension.string

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

private val miniMessage = MiniMessage.miniMessage()

/**
 * Converts the given string to small text.
 *
 * @return The converted string in small text.
 */
fun String.toSmallText(): String {
    return uppercase()
        .replace("Q", "ꞯ")
        .replace("W", "ᴡ")
        .replace("E", "ᴇ")
        .replace("R", "ʀ")
        .replace("T", "ᴛ")
        .replace("Y", "ʏ")
        .replace("U", "ᴜ")
        .replace("I", "ɪ")
        .replace("O", "ᴏ")
        .replace("P", "ᴘ")
        .replace("A", "ᴀ")
        .replace("S", "ѕ")
        .replace("D", "ᴅ")
        .replace("F", "ꜰ")
        .replace("G", "ɢ")
        .replace("H", "ʜ")
        .replace("J", "ᴊ")
        .replace("K", "ᴋ")
        .replace("L", "ʟ")
        .replace("Z", "ᴢ")
        .replace("X", "x")
        .replace("C", "ᴄ")
        .replace("V", "ᴠ")
        .replace("B", "ʙ")
        .replace("N", "ɴ")
        .replace("M", "ᴍ")
}

fun String.toComponent(): Component = miniMessage.deserialize(this)