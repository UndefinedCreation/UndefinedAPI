package com.undefined.api.nms.v1_21.extensions

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Slime
import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.entity.Player

object BlockExtension {

    fun setBlockProgress(player: Player, block: Block, stage: Int) {

        val packet = ClientboundBlockDestructionPacket(player.entityId, BlockPos(block.x, block.y, block.z), stage)

        player.sendPacket(packet)
    }
}