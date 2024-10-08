package com.undefined.api.nms.v1_20_6.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class UndefinedEntity(type: EntityType<*>, level: Level): Entity(type, level) {
    override fun addAdditionalSaveData(nbt: CompoundTag) {}
    override fun readAdditionalSaveData(nbt: CompoundTag) {}
    override fun defineSynchedData(builder: SynchedEntityData.Builder) {}
}