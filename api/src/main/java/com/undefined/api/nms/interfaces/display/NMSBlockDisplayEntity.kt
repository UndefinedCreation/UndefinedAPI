package com.undefined.api.nms.interfaces.display

import org.bukkit.block.data.BlockData

interface NMSBlockDisplayEntity : NMSDisplayEntity {
    var blockData: BlockData
}