package com.undefined.api.nms.interfaces

import org.bukkit.block.data.BlockData

interface NMSBlockDisplayEntity: NMSDisplayEntity {

    var blockData: BlockData
}