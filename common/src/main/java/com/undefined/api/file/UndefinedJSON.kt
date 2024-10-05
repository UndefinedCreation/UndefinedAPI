package com.undefined.api.file

import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.nio.file.Path

/**
 * The `UndefinedJSON` class represents a JSON file that may or may not exist. It extends the `UndefinedFile` class and provides
 * functionality specific to JSON files.
 *
 * @param file The file object representing the JSON file.
 * @param fromResources Flag indicating whether the JSON file should be copied from a resource.
 */
class UndefinedJSON(file: File, fromResources: Boolean = false): UndefinedFile(file, fromResources) {

    /**
     * The constructor for the `UndefinedJSON` class.
     *
     * @param path The path of the file.
     * @param fromResources Flag indicating whether the file should be copied from a resource.
     */
    constructor(path: String, fromResources: Boolean = false): this(File(path), fromResources)

    /**
     * The `UndefinedJSON` class represents a JSON file that may or may not exist. It extends the `UndefinedFile` class and provides
     * functionality specific to JSON files.
     *
     * @param file The file object representing the JSON file.
     * @param fromResources Flag indicating whether the JSON file should be copied from a resource.
     */
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    /**
     * The `configuration` variable stores the JSON configuration parsed from a file.
     */
    val configuration: JSONObject = JSONParser().parse(file.readText()) as JSONObject

     /**
      * Saves the configuration data to the specified file.
      *
      * The configuration data is converted to JSON format and saved to the file. If the file does not exist,
      * it will be created along with any necessary parent folders. If the file already exists, its contents will be overwritten.
      *
      * @see UndefinedFile
      */
     override fun save() {
         val jsonString = configuration.toString()
         file.writeText(jsonString)
     }

}