package com.redmagic.undefinedapi.file

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Path

/**
 * The `UndefinedYAML` class represents a YAML file that may or may not exist. It extends the `UndefinedFile` class and provides
 * functionality for loading and saving configurations from a YAML file.
 *
 * @property configuration The `YamlConfiguration` object associated with the YAML file.
 * @property file The file object representing the YAML file.
 * @property fromResources Flag indicating whether the file should be copied from a resource.
 */
class UndefinedYAML(override val file: File, fromResources: Boolean = false): UndefinedFile(file, fromResources) {

    /**
     * Constructs an instance of the UndefinedYAML class.
     *
     * @param path The path of the file.
     * @param fromResources Flag indicating whether the file should be copied from a resource. Default is false.
     */
    constructor(path: String, fromResources: Boolean = false): this(File(path), fromResources)
    /**
     * This constructor creates an instance of the `UndefinedYAML` class.
     *
     * @param path The path to the file.
     * @param fromResources Flag indicating whether the file should be copied from a resource. Default is `false`.
     */
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    /**
     * Represents a configuration loaded from a YAML file.
     */
    val configuration: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    /**
     * Saves the file.
     *
     * This method saves the file by calling the `save` method of the `YamlConfiguration` object associated with the file.
     * Subclasses can override this method to provide custom saving functionality specific to their file type.
     */
    override fun save() {
        configuration.save(file)
    }
}