package com.undefined.api.nms.interfaces.display

interface NMSTextDisplay: NMSDisplayEntity {

    var text: String
    var width: Float
    var backGroundColor: Int
    var textOpacity: Byte
}