package com.redmagic.undefinedapi.nms.extensions

fun <T : Any> Any.getPrivateField(fieldName: String): T = javaClass.getDeclaredField(fieldName).apply { isAccessible = true }[this] as T