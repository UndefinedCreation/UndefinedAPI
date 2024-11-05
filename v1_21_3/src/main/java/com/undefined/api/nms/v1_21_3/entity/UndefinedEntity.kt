package com.undefined.api.nms.v1_21_3.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class UndefinedEntity(type: EntityType<*>, level: Level): Entity(type, level) {
    override fun addAdditionalSaveData(nbt: CompoundTag) {}
    override fun readAdditionalSaveData(nbt: CompoundTag) {}
    override fun defineSynchedData(builder: SynchedEntityData.Builder) {}
    override fun hurtServer(world: ServerLevel, source: DamageSource, amount: Float): Boolean = false
}