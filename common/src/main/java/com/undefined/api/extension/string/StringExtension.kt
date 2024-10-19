package com.undefined.api.extension.string

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern

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

/**
 * Converts the given string to a Component using the MiniMessage deserialization.
 *
 * @return The converted Component.
 */
fun String.toComponent(): Component = MiniMessage.miniMessage().deserialize(this)

/**
 * Converts the given string to a Component.
 *
 * @return The converted Component.
 */
fun String.component(): Component = Component.text(this)

/**
 * Converts the given string to a Component using the MiniMessage deserialization.
 *
 * @return The converted Component.
 */
fun String.miniMessage(): Component = MiniMessage.miniMessage().deserialize(this)

/**
 * Map of replacement values for color formatting. This map is used to replace color placeholders in strings with their corresponding ChatColor values.
 * Each key-value pair represents a color placeholder and its corresponding ChatColor value.
 *
 * The following placeholders and their corresponding ChatColor values are defined:
 * "<BLACK>" : ChatColor.BLACK.toString()
 * "<dark_blue>" : ChatColor.DARK_BLUE.toString()
 * "<dark_green>" : ChatColor.DARK_GREEN.toString()
 * "<dark_aqua>" : ChatColor.DARK_AQUA.toString()
 * "<dark_red>" : ChatColor.DARK_RED.toString()
 * "<dark_purple>" : ChatColor.DARK_PURPLE.toString()
 * "<gold>" : ChatColor.GOLD.toString()
 * "<gray>" : ChatColor.GRAY.toString()
 * "<dark_gray>" : ChatColor.DARK_GRAY.toString()
 * "<blue>" : ChatColor.BLUE.toString()
 * "<green>" : ChatColor.GREEN.toString()
 * "<aqua>" : ChatColor.AQUA.toString()
 * "<red>" : ChatColor.RED.toString()
 * "<light_purple>" : ChatColor.LIGHT_PURPLE.toString()
 * "<yellow>" : ChatColor.YELLOW.toString()
 * "<white>" : ChatColor.WHITE.toString()
 * "<magic>" : ChatColor.MAGIC.toString()
 * "<bold>" : ChatColor.BOLD.toString()
 * "<strikethrough>" : ChatColor.STRIKETHROUGH.toString()
 * "<underline>" : ChatColor.UNDERLINE.toString()
 * "<italic>" : ChatColor.ITALIC.toString()
 * "<reset>" : ChatColor.RESET.toString()
 */
private val replacements = mapOf(
    "<BLACK>" to ChatColor.BLACK.toString(),
    "<dark_blue>" to ChatColor.DARK_BLUE.toString(),
    "<dark_green>" to ChatColor.DARK_GREEN.toString(),
    "<dark_aqua>" to ChatColor.DARK_AQUA.toString(),
    "<dark_red>" to ChatColor.DARK_RED.toString(),
    "<dark_purple>" to ChatColor.DARK_PURPLE.toString(),
    "<gold>" to ChatColor.GOLD.toString(),
    "<gray>" to ChatColor.GRAY.toString(),
    "<dark_gray>" to ChatColor.DARK_GRAY.toString(),
    "<blue>" to ChatColor.BLUE.toString(),
    "<green>" to ChatColor.GREEN.toString(),
    "<aqua>" to ChatColor.AQUA.toString(),
    "<red>" to ChatColor.RED.toString(),
    "<light_purple>" to ChatColor.LIGHT_PURPLE.toString(),
    "<yellow>" to ChatColor.YELLOW.toString(),
    "<white>" to ChatColor.WHITE.toString(),
    "<magic>" to ChatColor.MAGIC.toString(),
    "<bold>" to ChatColor.BOLD.toString(),
    "<strikethrough>" to ChatColor.STRIKETHROUGH.toString(),
    "<underline>" to ChatColor.UNDERLINE.toString(),
    "<italic>" to ChatColor.ITALIC.toString(),
    "<reset>" to ChatColor.RESET.toString()
)

/**
 * Regular expression pattern to match hexadecimal color codes in the format "#RRGGBB".
 * This pattern is used for identifying and replacing hexadecimal color codes in strings.
 */
private val hexPattern: Pattern = Pattern.compile("<#[A-Fa-f0-9]{6}>")

/**
 * Translates color codes in a string to their corresponding ChatColor values.
 *
 * The translateColor method replaces color code replacements and hexadecimal color codes in the string with their corresponding ChatColor values.
 * Color code replacements are performed case-insensitively using the replacements map.
 * Hexadecimal color codes are identified using the hexPattern regular expression and replaced with the corresponding ChatColor value obtained from ChatColor.of.
 *
 * @return The string with color codes translated to ChatColor values.
 */
fun String.translateColor(): String {
    var string = replacements.entries.fold(this) { acc, (old, new) -> acc.replace(old, new, ignoreCase = true) }
    val matcher: Matcher = hexPattern.matcher(string)

    while (matcher.find()) {
        val color: String = matcher.group()
        string = string.replace(color, ChatColor.of(color.substring(1,8))!!.toString())
    }

    return string
}
