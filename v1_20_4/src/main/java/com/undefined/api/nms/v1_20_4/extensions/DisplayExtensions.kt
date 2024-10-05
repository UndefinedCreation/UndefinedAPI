package com.undefined.api.nms.v1_20_4.extensions

import com.undefined.api.nms.v1_20_4.SpigotNMSMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Display
import org.joml.Quaternionf
import org.joml.Vector3f


fun Display.getScaleAccessor(): EntityDataAccessor<Vector3f> {
    val field = Display::class.java.getDeclaredField(SpigotNMSMappings.DisplayScale)
    field.isAccessible = true
    return field.get(this) as EntityDataAccessor<Vector3f>
}

fun Display.getTranslationAccessor(): EntityDataAccessor<Vector3f> {
    val field = Display::class.java.getDeclaredField(SpigotNMSMappings.DisplayTransfrom)
    field.isAccessible = true
    return field.get(this) as EntityDataAccessor<Vector3f>
}

fun Display.getLeftRotation(): EntityDataAccessor<Quaternionf> {
    val field = Display::class.java.getDeclaredField(SpigotNMSMappings.DisplayLeftRot)
    field.isAccessible = true
    return field.get(this) as EntityDataAccessor<Quaternionf>
}

fun Display.getRightRotation(): EntityDataAccessor<Quaternionf> {
    val field = Display::class.java.getDeclaredField(SpigotNMSMappings.DisplayRightRot)
    field.isAccessible = true
    return field.get(this) as EntityDataAccessor<Quaternionf>
}

fun Display.getScale(): Vector3f = entityData.get(getScaleAccessor())
fun Display.getTranslation(): Vector3f = entityData.get(getTranslationAccessor())

fun Display.setScaleX(scale: Float): SynchedEntityData {
    val v = getScale()
    val v2 = v.set(scale, v.y, v.z)
    entityData.set(getScaleAccessor(), v2)
    return entityData
}

fun Display.setScaleY(scale: Float) {
    val v = getScale()
    val v2 = v.set(v.x, scale, v.z)
    entityData.set(getScaleAccessor(), v2)
}

fun Display.setScaleZ(scale: Float) {
    val v = getScale()
    val v2 = v.set(v.x, v.y, scale)
    entityData.set(getScaleAccessor(), v2)
}

fun Display.setTranslationX(x: Float) {
    val translation = getTranslation()
    translation.x = x
    entityData.set(getTranslationAccessor(), translation)
}

fun Display.setTranslationY(y: Float) {
    val translation = getTranslation()
    translation.y = y
    entityData.set(getTranslationAccessor(), translation)
}

fun Display.setTranslationZ(z: Float) {
    val translation = getTranslation()
    translation.z = z
    entityData.set(getTranslationAccessor(), translation)
}

fun Display.setLeftRotation(x: Float, y: Float, z: Float): Quaternionf {
    val rotation = getLeftRotation()
    val quaternionf = Quaternionf().rotationXYZ(x, y, z)
    entityData.set(rotation, quaternionf)
    return quaternionf
}

fun Display.setRightRotation(x: Float, y: Float, z: Float): Quaternionf {
    val rotation = getRightRotation()
    val quaternionf = Quaternionf().rotationXYZ(x, y, z)
    entityData.set(rotation, quaternionf)
    return quaternionf
}



