package com.undefined.api.nms.extensions

import com.undefined.api.API
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

fun Player.setMetaData(name: String, any: Any) {
    this.setMetadata(name, FixedMetadataValue(API.plugin, any))
}

fun Player.getMetaDataInfo(name: String): Any? = if (this.hasMetadata(name)) this.getMetadata(name)[0] else null
fun Player.removeMetaData(name: String) = this.removeMetadata(name, API.plugin)