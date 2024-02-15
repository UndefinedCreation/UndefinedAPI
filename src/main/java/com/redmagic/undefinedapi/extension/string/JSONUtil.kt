package com.redmagic.undefinedapi.extension.string

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Saves the JSONObject to a file.
 *
 * @param file the File object representing the file to save the JSONObject to
 */
fun JSONObject.save(file: File){
    val fileWriter = FileWriter(file)
    fileWriter.write(this.toString())
    fileWriter.close()
}

/**
 * Updates the current JSONObject by merging it with the contents of the specified file.
 *
 * @param file The file containing the JSON data to merge.
 */
fun JSONObject.update(file: File){
    val json = file.loadFromFile()
    json.putAll(this)
    this.save(file)
}

/**
 * Loads the contents of the file into a JSONObject.
 *
 * @return The JSONObject containing the contents of the file.
 */
fun File.loadFromFile(): JSONObject {
    val fileReader = FileReader(this)
    val obj = JSONParser().parse(fileReader)
    fileReader.close()
    return obj as JSONObject
}