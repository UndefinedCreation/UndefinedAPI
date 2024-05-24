package com.redmagic.undefinedapi.file

import com.redmagic.undefinedapi.UndefinedAPI
import java.io.File
import java.nio.file.Path

open class UndefinedFile(open val file: File, private val fromResources: Boolean = false) {
    constructor(path: String, fromResources: Boolean = false): this(File(path), fromResources)
    constructor(path: Path, fromResources: Boolean = false): this(path.toFile(), fromResources)

    init {
        createParentFolders()
        if (fromResources) copyFromResource() else createIfDoesntExist()
    }

    private fun createParentFolders(){
        file.parentFile.mkdirs()
    }

    private fun createIfDoesntExist(){
        if (file.exists()){
            file.mkdir()
        }
    }
    private fun copyFromResource(){
        UndefinedAPI.plugin.saveResource(file.toString(), false)
    }

     open fun save(){}
}