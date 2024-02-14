package com.redmagic.undefinedapi.string

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun File.asString(): String = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(toPath()))

fun String.asFile(filePath: String) {
    val bytes = java.util.Base64.getDecoder().decode(this)
    try {
        Files.write(Path.of(filePath), bytes, StandardOpenOption.CREATE)
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}