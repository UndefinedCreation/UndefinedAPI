package com.redmagic.undefinedapi.file

import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.nio.file.Path

class UndefinedJSON(override val file: File, fromResources: Boolean = false): UndefinedFile(file, fromResources) {
    constructor(path: String, fromResources: Boolean = false): this(File(path), fromResources)
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    val configuration: JSONObject = JSONParser().parse(file.readText()) as JSONObject

     override fun save() {
         val jsonString = configuration.toString()
         file.writeText(jsonString)
     }
}