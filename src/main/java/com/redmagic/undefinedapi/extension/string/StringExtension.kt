package com.redmagic.undefinedapi.extension.string

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern

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

fun String.translateColor(): String  {

    var string = this

    string = ChatColor.translateAlternateColorCodes('&', string)

    val hexPattern: Pattern = Pattern.compile("#([A-Fa-f0-9]{6})")
    val matcher: Matcher = hexPattern.matcher(string)
    val buffer: StringBuilder = StringBuilder(string.length + 4 * 8)
    while (matcher.find()) {
        val group: String = matcher.group(1)
        matcher.appendReplacement(
            buffer, ChatColor.COLOR_CHAR + "x"
                    + ChatColor.COLOR_CHAR + group[0] + ChatColor.COLOR_CHAR + group[1]
                    + ChatColor.COLOR_CHAR + group[2] + ChatColor.COLOR_CHAR + group[3]
                    + ChatColor.COLOR_CHAR + group[4] + ChatColor.COLOR_CHAR + group[5]
        )
    }

    return matcher.appendTail(buffer).toString()
}