package com.redmagic.undefinedapi.nms.v1_20_5

/**
 * This object contains constant values used for Spigot NMS mapping.
 *
 * @property ClientboundSetEntityDataPacketEntityID The key used to access the entity ID in the ClientboundSetEntityDataPacket.
 * @property ClientboundSetEntityDataPacketSyncedEntityList The key used to access the synchronized entity list in the ClientboundSetEntityDataPacket.
 * @property ServerboundInteractPacketEntityID The key used to access the entity ID in the ServerboundInteractPacket.
 * @property ServerboundInteractPacketAction The key used to access the action in the ServerboundInteractPacket.
 * @property ServerboundInteractPacketActionHand The key used to access the action hand in the ServerboundInteractPacket.
 * @property ServerCommonPacketListenerImplConnection The key used to access the connection in the ServerCommonPacketListenerImpl.
 */
object SpigotNMSMappings {

    const val ClientboundSetEntityDataPacketEntityID = "c"
    const val ClientboundSetEntityDataPacketSyncedEntityList = "d"
    const val ServerboundInteractPacketEntityID = "b"
    const val ServerboundInteractPacketAction = "c"
    const val ServerboundInteractPacketActionHand = "a"
    const val ServerCommonPacketListenerImplConnection = "e"
    const val LivingEntitySetFlag = "c"
}