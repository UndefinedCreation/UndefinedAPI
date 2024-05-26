package com.redmagic.undefinedapi.file

import com.redmagic.undefinedapi.UndefinedAPI
import java.io.File
import java.nio.file.Path

/**
 * The `UndefinedFile` class represents a file that may or may not exist. It provides methods for creating the parent folders,
 * copying the file from a resource, and creating the file if it doesn't exist. Subclasses of `UndefinedFile` can override the `save` method
 * to provide custom saving functionality specific to their file type.
 *
 * @param file The file object representing the file.
 * @param fromResources Flag indicating whether the file should be copied from a resource.
 */
open class UndefinedFile(open val file: File, private val fromResources: Boolean = false) {
    /**
     * Constructs an instance of the UndefinedFile class.
     *
     * @param path The path of the file.
     * @param fromResources Flag indicating whether the file should be copied from a resource.
     */
    constructor(path: String, fromResources: Boolean = false): this(File(UndefinedAPI.plugin.dataFolder ,path), fromResources)
    /**
     * Constructs a new `UndefinedFile` object with the specified `path` and `fromResources` flag.
     *
     * @param path The path to the file.
     * @param fromResources Flag indicating whether the file should be copied from a resource.
     *
     * @throws IllegalArgumentException if the `path` is empty or null.
     */
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    init {
        createParentFolders()
        if (fromResources) copyFromResource() else createIfDoesntExist()
    }

    /**
     * Creates the parent folders for the file.
     */
    private fun createParentFolders(){
        file.parentFile.mkdirs()
    }

    /**
     * Creates the file if it doesn't exist.
     */
    private fun createIfDoesntExist(){
        if (file.exists()){
            file.mkdir()
        }
    }
    /**
     * Copies the file from a resource.
     */
    private fun copyFromResource(){
        UndefinedAPI.plugin.saveResource(file.toString(), false)
    }

     /**
      * Saves the file. Subclasses can override this method to provide custom saving functionality specific to their file type.
      */
     open fun save(){}
}