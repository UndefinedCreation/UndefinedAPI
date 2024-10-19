package com.undefined.api.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

/**
 * Convert component into a string that uses the legacy format.
 *
 * @return A string in legacy format.
 */
fun Component.legacyString(): String = LegacyComponentSerializer.legacy('§').serialize(this)