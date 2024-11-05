package com.undefined.api.nms.v1_21_3.extensions

import com.undefined.api.nms.v1_21_3.SpigotNMSMappings
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
    val v = getTranslation()
    v.x = x
    entityData.set(getTranslationAccessor(), v)
}

fun Display.setTranslationY(y: Float) {
    val v = getTranslation()
    v.y = y
    entityData.set(getTranslationAccessor(), v)
}

fun Display.setTranslationZ(z: Float) {
    val v = getTranslation()
    v.z = z
    entityData.set(getTranslationAccessor(), v)
}

fun Display.setLeftRotation(x: Float, y: Float, z: Float): Quaternionf {
    val l = getLeftRotation()
    val q = Quaternionf().rotationXYZ(x, y, z)
    entityData.set(l, q)
    return q
}

fun Display.setRightRotation(x: Float, y: Float, z: Float): Quaternionf {
    val l = getRightRotation()
    val q = Quaternionf().rotationXYZ(x, y, z)
    entityData.set(l, q)
    return q
}


