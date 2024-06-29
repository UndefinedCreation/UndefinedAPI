package com.undefined.api.nms.interfaces

import com.undefined.api.nms.Billboard

interface NMSDisplayEntity: NMSEntity {

    var scaleX: Float
    var scaleY: Float
    var scaleZ: Float

    var offsetX: Float
    var offsetY: Float
    var offsetZ: Float

    var billboard: Billboard

    var transformationInterpolationDuration: Int
    var positionRotationInterpolationDuration: Int
    var interpolationDelay: Int

    fun resetOffset()

    fun scale(scale: Float)

    fun offset(x: Float, y: Float, z: Float)

    fun leftRotation(x: Float, y: Float, z: Float)
    fun rightRotation(x: Float, y: Float, z: Float)

    fun updateScale()

    fun updateTranslation()


}