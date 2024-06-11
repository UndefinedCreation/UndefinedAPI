package com.redmagic.undefinedapi.nms.v1_20_4.entity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class UndefinedEntity(type: EntityType<*>, level: Level): Entity(type, level) {
    override fun defineSynchedData() {}

    override fun addAdditionalSaveData(nbt: CompoundTag) {}

    override fun readAdditionalSaveData(nbt: CompoundTag) {}

    override fun isColliding(pos: BlockPos, state: BlockState): Boolean = false

    override fun isCollidable(ignoreClimbing: Boolean): Boolean  = false
}