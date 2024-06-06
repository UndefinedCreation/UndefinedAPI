package com.redmagic.undefinedapi.extension

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

fun Block.getConnectedBlocks(): List<Block> = BlockFace.values()
    .filter { !it.name.contains("_") && it != BlockFace.SELF }
    .map { this.getRelative(it) }

fun Block.sendBreakProgress(player: Player, stage: Int) {
    when(getNMSVersion()){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.extensions.BlockExtension.setBlockProgress(player, this, stage)
        "1.20.5", "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_5.extensions.BlockExtension.setBlockProgress(player, this, stage)
    }
}