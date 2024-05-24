package com.redmagic.undefinedapi.file

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Path

class UndefinedYAML(override val file: File, fromResources: Boolean = false): UndefinedFile(file, fromResources) {

    constructor(path: String, fromResources: Boolean = false): this(File(path), fromResources)
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    val configuration: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    override fun save() {
        configuration.save(file)
    }
}