package com.undefined.api.nms

import com.undefined.api.UndefinedAPI
import com.undefined.api.extension.getNMSVersion
import com.undefined.api.nms.interfaces.*
import com.undefined.api.nms.interfaces.display.NMSBlockDisplayEntity
import com.undefined.api.nms.interfaces.display.NMSInteractionEntity
import com.undefined.api.nms.interfaces.display.NMSItemDisplayEntity
import com.undefined.api.nms.interfaces.display.NMSTextDisplay
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

interface NMSEntityFactory {
    fun createPlayer(data: Array<out Any>): NMSPlayer?
    fun createSlime(): NMSSlimeEntity
    fun createBlockDisplay(data: Array<out Any>): NMSBlockDisplayEntity?
    fun createLivingEntity(entityType: EntityType): NMSLivingEntity
    fun createItemEntity(data: Array<out Any>): NMSItemEntity?
    fun createEntity(entityType: EntityType): NMSEntity
    fun createTextDisplay(data: Array<out Any>): NMSTextDisplay?
    fun createItemDisplay(data: Array<out Any>): NMSItemDisplayEntity?
    fun createInteraction(): NMSInteractionEntity
}

class NMSEntityV1_20_4Factory : NMSEntityFactory {
    override fun createPlayer(data: Array<out Any>): NMSPlayer? {
        return when(data .size) {

            2 -> com.undefined.api.nms.v1_20_4.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null
            )

            3 -> com.undefined.api.nms.v1_20_4.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null,
                data[2] as? String ?: return null
            )

            else -> null
        }
    }

    override fun createSlime(): NMSSlimeEntity = com.undefined.api.nms.v1_20_4.entity.livingEntities.NMSSlimeEntity()

    override fun createBlockDisplay(data: Array<out Any>): NMSBlockDisplayEntity = com.undefined.api.nms.v1_20_4.entity.entityClasses.display.NMSBlockDisplayEntity(data[0] as BlockData)

    override fun createLivingEntity(entityType: EntityType): NMSLivingEntity = com.undefined.api.nms.v1_20_4.entity.NMSLivingEntity(entityType)

    override fun createItemEntity(data: Array<out Any>): NMSItemEntity = com.undefined.api.nms.v1_20_4.entity.entityClasses.NMSItemEntity(data[0] as ItemStack)

    override fun createEntity(entityType: EntityType): NMSEntity = com.undefined.api.nms.v1_20_4.entity.NMSEntity(entityType)

    override fun createTextDisplay(data: Array<out Any>): NMSTextDisplay = com.undefined.api.nms.v1_20_4.entity.entityClasses.display.NMSTextDisplayEntity(data[0] as String)

    override fun createItemDisplay(data: Array<out Any>): NMSItemDisplayEntity? = com.undefined.api.nms.v1_20_4.entity.entityClasses.display.NMSItemDisplayEntity(data[0] as ItemStack)

    override fun createInteraction(): NMSInteractionEntity = com.undefined.api.nms.v1_20_4.entity.entityClasses.display.NMSInteractionEntity()
}

class NMSEntityV1_20_6Factory : NMSEntityFactory {
    override fun createPlayer(data: Array<out Any>): NMSPlayer? {
        return when(data.size) {

            2 -> com.undefined.api.nms.v1_20_6.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null
            )

            3 -> com.undefined.api.nms.v1_20_6.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null,
                data[2] as? String ?: return null
            )

            else -> null
        }
    }

    override fun createSlime(): NMSSlimeEntity = com.undefined.api.nms.v1_20_6.entity.livingEntities.NMSSlimeEntity()

    override fun createBlockDisplay(data: Array<out Any>): NMSBlockDisplayEntity = com.undefined.api.nms.v1_20_6.entity.entityClass.display.NMSBlockDisplayEntity(data[0] as BlockData)

    override fun createLivingEntity(entityType: EntityType): NMSLivingEntity = com.undefined.api.nms.v1_20_6.entity.NMSLivingEntity(entityType)

    override fun createItemEntity(data: Array<out Any>): NMSItemEntity = com.undefined.api.nms.v1_20_6.entity.entityClass.NMSItemEntity(data[0] as ItemStack)

    override fun createEntity(entityType: EntityType): NMSEntity = com.undefined.api.nms.v1_20_6.entity.NMSEntity(entityType)

    override fun createTextDisplay(data: Array<out Any>): NMSTextDisplay = com.undefined.api.nms.v1_20_6.entity.entityClass.display.NMSTextDisplayEntity(data[0] as String)

    override fun createItemDisplay(data: Array<out Any>): NMSItemDisplayEntity? = com.undefined.api.nms.v1_20_6.entity.entityClass.display.NMSItemDisplayEntity(data[0] as ItemStack)

    override fun createInteraction(): NMSInteractionEntity = com.undefined.api.nms.v1_20_6.entity.entityClass.display.NMSInteractionEntity()
}

class NMSEntityV1_21Factory : NMSEntityFactory {
    override fun createPlayer(data: Array<out Any>): NMSPlayer? {
        return when(data .size) {

            2 -> com.undefined.api.nms.v1_21.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null
            )

            3 -> com.undefined.api.nms.v1_21.npc.NMSPlayer(
                data[0] as? String ?: return null,
                data[1] as? String ?: return null,
                data[2] as? String ?: return null
            )

            else -> null
        }
    }

    override fun createSlime(): NMSSlimeEntity = com.undefined.api.nms.v1_21.entity.livingEntities.NMSSlimeEntity()

    override fun createBlockDisplay(data: Array<out Any>): NMSBlockDisplayEntity = com.undefined.api.nms.v1_21.entity.entityClass.display.NMSBlockDisplayEntity(data[0] as BlockData)

    override fun createLivingEntity(entityType: EntityType): NMSLivingEntity = com.undefined.api.nms.v1_21.entity.NMSLivingEntity(entityType)

    override fun createItemEntity(data: Array<out Any>): NMSItemEntity = com.undefined.api.nms.v1_21.entity.entityClass.NMSItemEntity(data[0] as ItemStack)

    override fun createEntity(entityType: EntityType): NMSEntity = com.undefined.api.nms.v1_21.entity.NMSEntity(entityType)

    override fun createTextDisplay(data: Array<out Any>): NMSTextDisplay = com.undefined.api.nms.v1_21.entity.entityClass.display.NMSTextDisplayEntity(data[0] as String)

    override fun createItemDisplay(data: Array<out Any>): NMSItemDisplayEntity? = com.undefined.api.nms.v1_21.entity.entityClass.display.NMSItemDisplayEntity(data[0] as ItemStack)

    override fun createInteraction(): NMSInteractionEntity = com.undefined.api.nms.v1_20_6.entity.entityClass.display.NMSInteractionEntity()
}

private val factories = mapOf(
    "1.20.4" to NMSEntityV1_20_4Factory(),
    "1.20.6" to NMSEntityV1_20_6Factory(),
    "1.21" to NMSEntityV1_21Factory()
)

fun UndefinedAPI.createFakeEntity(entityType: EntityType, vararg data: Any): NMSEntity? {
    val factory = factories[getNMSVersion()] ?: return null

    val isLivingEntity = LivingEntity::class.java.isAssignableFrom(entityType.entityClass!!)
    val isPlayer = entityType == EntityType.PLAYER
    val isSlime = entityType == EntityType.SLIME
    val isItem = entityType == EntityType.DROPPED_ITEM
    val isTextDisplay = entityType == EntityType.TEXT_DISPLAY
    val isBlockDisplay = entityType == EntityType.BLOCK_DISPLAY
    val isItemDisplay = entityType == EntityType.ITEM_DISPLAY
    val isInteraction = entityType == EntityType.INTERACTION
    return when {
        isLivingEntity -> when {
            isSlime -> factory.createSlime()
            isPlayer -> factory.createPlayer(data)
            else -> factory.createLivingEntity(entityType)
        }
        isItemDisplay -> factory.createItemDisplay(data)
        isBlockDisplay -> factory.createBlockDisplay(data)
        isTextDisplay -> factory.createTextDisplay(data)
        isItem -> factory.createItemEntity(data)
        isInteraction -> factory.createInteraction()
        else -> factory.createEntity(entityType)
    }
}