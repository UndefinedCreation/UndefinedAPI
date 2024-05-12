package com.redmagic.undefinedapi.extension

import org.bukkit.block.Block
import org.bukkit.block.BlockFace

fun Block.getConnectedBlocks(): List<Block> = BlockFace.values()
    .filter { !it.name.contains("_") && it != BlockFace.SELF }
    .map { this.getRelative(it) }