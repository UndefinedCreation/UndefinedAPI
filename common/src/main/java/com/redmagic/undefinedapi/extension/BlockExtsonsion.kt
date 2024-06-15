package com.redmagic.undefinedapi.extension

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.nms.createFakeEntity
import com.redmagic.undefinedapi.nms.interfaces.NMSSlimeEntity
import com.redmagic.undefinedapi.scheduler.TimeUnit
import com.redmagic.undefinedapi.scheduler.delay
import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
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

private val glowMap: HashMap<Block, NMSSlimeEntity> = hashMapOf()


fun Block.glow(chatColor: ChatColor, tick: Int) {

    val slime = glowMap[this] ?: UndefinedAPI.api.createFakeEntity(EntityType.SLIME)!! as NMSSlimeEntity
    glowMap[this] = slime
    slime.size = 2

    slime.viewers.clear()

    this.location.world!!.getNearbyEntities(this.location,25.0, 25.0, 25.0)
        .filterIsInstance<Player>()
        .forEach { slime.addViewer(it) }

    slime.spawn(this.location.add(0.5,0.0,0.5))
    slime.isVisible = false
    slime.glowing = true
    slime.glowingColor = chatColor

    delay(tick) {
        println("KIll")
        slime.kill()
        glowMap.remove(this@glow)
    }
}

fun Block.glow(chatColor: ChatColor, amount: Int, timeUnit: TimeUnit) = this.glow(chatColor, timeUnit.toTicks(amount.toLong()).toInt())