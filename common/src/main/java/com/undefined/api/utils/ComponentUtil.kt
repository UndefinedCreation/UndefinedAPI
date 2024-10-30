package com.undefined.api.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

/**
 * Convert component into a string that uses the legacy format.
 *
 * @return A string in legacy format.
 */
fun Component.legacyString(): String = LegacyComponentSerializer.legacy('§').serialize(this)

/**
 * Deserialize a string into a component using MiniMessage
 *
 * @return A component in MiniMessage format.
 */
fun miniMessage(input: String): Component = MiniMessage.miniMessage().deserialize(input)

/**
 * Serialize a component into a string using MiniMessage
 *
 * @return A string in MiniMessage format.
 */
fun miniMessage(component: Component): String = MiniMessage.miniMessage().serialize(component)