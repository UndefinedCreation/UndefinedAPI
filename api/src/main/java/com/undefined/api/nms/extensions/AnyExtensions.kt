package com.undefined.api.nms.extensions

fun <T : Any> Any.getPrivateField(fieldName: String): T = javaClass.getDeclaredField(fieldName).apply { isAccessible = true }[this] as T
fun <T : Any> Any.getPrivateFieldFromSuper(fieldName: String): T = javaClass.superclass.getDeclaredField(fieldName).apply { isAccessible = true }[this] as T