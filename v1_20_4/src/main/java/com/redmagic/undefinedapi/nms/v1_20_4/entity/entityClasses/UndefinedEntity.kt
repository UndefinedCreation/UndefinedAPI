package com.redmagic.undefinedapi.nms.v1_20_4.entity.entityClasses

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

open class UndefinedEntity(type: EntityType<*>, level: Level): Entity(type, level) {

    override fun defineSynchedData() {}

    override fun addAdditionalSaveData(nbt: CompoundTag) {}

    override fun readAdditionalSaveData(nbt: CompoundTag) {}


}