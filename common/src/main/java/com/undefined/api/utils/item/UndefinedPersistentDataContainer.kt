package com.undefined.api.utils.item

import org.bukkit.persistence.PersistentDataType

data class UndefinedPersistentDataContainer<P, C : Any>(val type: PersistentDataType<P, C>, val value: C)