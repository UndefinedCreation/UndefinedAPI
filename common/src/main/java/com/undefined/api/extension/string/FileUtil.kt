package com.undefined.api.extension.string

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

/**
 * Converts the file to a Base64-encoded string.
 * Uses the `java.util.Base64` class to encode the file contents as a Base64 string.
 *
 * @return the Base64-encoded string representation of the file.
 */
fun File.asString(): String = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(toPath()))

/**
 * Converts the given Base64 encoded string to a file and writes it to the specified file path.
 * @param filePath The path of the file to create or overwrite with the contents of the Base64 string.
 * @throws RuntimeException If there is an error writing the file.
 */
fun String.asFile(filePath: String) {
    val bytes = java.util.Base64.getDecoder().decode(this)
    try {
        Files.write(Path.of(filePath), bytes, StandardOpenOption.CREATE)
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}