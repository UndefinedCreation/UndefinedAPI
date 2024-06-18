package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.extension.getNMSVersion
import com.redmagic.undefinedapi.nms.interfaces.NMSEntity
import com.redmagic.undefinedapi.nms.interfaces.NMSPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


@Deprecated("Use createFakeEntity instead")
fun UndefinedAPI.createFakePlayer(name: String, skinName: String = name): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(name, skinName)
        "1.20.5", "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(name, skinName)
        "1.21" -> com.redmagic.undefinedapi.nms.v1_21.npc.NMSPlayer(name, skinName)
        else -> null
    }
}

@Deprecated("Use createFakeEntity instead")
fun UndefinedAPI.createFakePlayer(name: String, texture: String, sign: String): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(name, texture, sign)
        "1.20.5", "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(name, texture, sign)
        "1.21" -> com.redmagic.undefinedapi.nms.v1_21.npc.NMSPlayer(name, texture, sign)
        else -> null
    }
}

fun UndefinedAPI.createFakeEntity(entityType: EntityType, vararg data: Any): NMSEntity? {
    val isLivingEntity = LivingEntity::class.java.isAssignableFrom(entityType.entityClass!!)
    val isPlayer = entityType == EntityType.PLAYER
    val isSlime = entityType == EntityType.SLIME
    val isItem = entityType == EntityType.DROPPED_ITEM

    return when(getNMSVersion()) {
        "1.20.4" -> when {
            isLivingEntity -> when {
                isSlime -> com.redmagic.undefinedapi.nms.v1_20_4.entity.livingEntities.NMSSlimeEntity()
                isPlayer -> when(data.size){
                    2 -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(data[0] as String, data[1] as String)
                    3 -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(data[0] as String, data[1] as String, data[2] as String)
                    else -> null
                }
                else -> com.redmagic.undefinedapi.nms.v1_20_4.entity.NMSLivingEntity(entityType)
            }

            isItem -> com.redmagic.undefinedapi.nms.v1_20_4.entity.entityClasses.NMSItemEntity(data[0] as ItemStack)
            else -> com.redmagic.undefinedapi.nms.v1_20_4.entity.NMSEntity(entityType)
        }

        "1.20.5", "1.20.6" -> when {
            isLivingEntity -> when {
                isSlime -> com.redmagic.undefinedapi.nms.v1_20_5.entity.livingEntities.NMSSlimeEntity()
                isPlayer -> when(data.size){
                    2 -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(data[0] as String, data[1] as String)
                    3 -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(data[0] as String, data[1] as String, data[2] as String)
                    else -> null
                }
                else -> com.redmagic.undefinedapi.nms.v1_20_5.entity.NMSLivingEntity(entityType)
            }

            isItem -> com.redmagic.undefinedapi.nms.v1_20_5.entity.entityClass.NMSItemEntity(data[0] as ItemStack)
            else -> com.redmagic.undefinedapi.nms.v1_20_5.entity.NMSEntity(entityType)
        }

        "1.21" -> when {
            isLivingEntity -> when {
                isSlime -> com.redmagic.undefinedapi.nms.v1_21.entity.livingEntities.NMSSlimeEntity()
                isPlayer -> when(data.size){
                    2 -> com.redmagic.undefinedapi.nms.v1_21.npc.NMSPlayer(data[0] as String, data[1] as String)
                    3 -> com.redmagic.undefinedapi.nms.v1_21.npc.NMSPlayer(data[0] as String, data[1] as String, data[2] as String)
                    else -> null
                }
                else -> com.redmagic.undefinedapi.nms.v1_21.entity.NMSLivingEntity(entityType)
            }

            isItem -> com.redmagic.undefinedapi.nms.v1_21.entity.entityClass.NMSItemEntity(data[0] as ItemStack)
            else -> com.redmagic.undefinedapi.nms.v1_21.entity.NMSEntity(entityType)
        }

        else -> null

    }
}

fun Player.getTexture(): Array<String> {
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.extensions.PlayerExtension.getTextures(this)
        "1.20.5", "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_5.extensions.PlayerExtension.getTextures(this)
        else -> arrayOf("","")
    }
}