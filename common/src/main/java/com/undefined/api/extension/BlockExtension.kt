package com.undefined.api.extension

import com.undefined.api.UndefinedAPI
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.nms.interfaces.NMSSlimeEntity
import com.undefined.api.scheduler.TimeUnit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

fun Block.getConnectedBlocks(): List<Block> = BlockFace.entries
    .filter { !it.name.contains("_") && it != BlockFace.SELF }
    .map { this.getRelative(it) }

fun Block.sendBreakProgress(player: Player, stage: Int) {
    when(getNMSVersion()) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.BlockExtension.setBlockProgress(player, this, stage)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.BlockExtension.setBlockProgress(player, this, stage)
        "1.21", "1.21.1", "1.21.2", "1.21.3" -> com.undefined.api.nms.v1_21.extensions.BlockExtension.setBlockProgress(player, this, stage)
    }
}

fun BlockData.getID(): Int =
    when(getNMSVersion()) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.BlockExtension.getID(this)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.BlockExtension.getID(this)
        "1.21", "1.21.1", "1.21.2", "1.21.3" -> com.undefined.api.nms.v1_21.extensions.BlockExtension.getID(this)
        else -> 0
    }

fun Int.asBlockData(): BlockData =
    when(getNMSVersion()) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.BlockExtension.getBlockDataFromID(this)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.BlockExtension.getBlockDataFromID(this)
        "1.21", "1.21.1", "1.21.2", "1.21.3" -> com.undefined.api.nms.v1_21.extensions.BlockExtension.getBlockDataFromID(this)
        else -> Material.STONE.createBlockData()
    }

private val glowMap: HashMap<Block, NMSSlimeEntity> = hashMapOf()
private val taskMap: HashMap<Block, BukkitTask> = hashMapOf()

fun Block.glow(chatColor: ChatColor, viewers: List<Player>, tick: Int) {
    taskMap[this]?.let {
        taskMap[this]!!.cancel()
        taskMap.remove(this)
    }

    val slime = glowMap[this] ?: UndefinedAPI.api.createFakeEntity(EntityType.SLIME)!! as NMSSlimeEntity
    glowMap[this] = slime

    slime.size = 2
    slime.viewers.clear()
    viewers.forEach { slime.addViewer(it) }

    val location = this.location.add(0.5,0.0,0.5)
    slime.spawn(location)
    slime.setHeadRotation(0F)
    slime.isVisible = false
    slime.glowing = true
    slime.glowingColor = chatColor

    taskMap[this] = com.undefined.api.scheduler.delay(tick) {
        slime.kill()
        glowMap.remove(this@glow)
    }
}

fun Block.glow(chatColor: ChatColor, viewers: List<Player>, amount: Int, timeUnit: TimeUnit) = this.glow(chatColor, viewers, timeUnit.toTicks(amount.toLong()).toInt())