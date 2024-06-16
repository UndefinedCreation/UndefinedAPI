package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import org.bukkit.Location
import org.bukkit.entity.Entity

class EntityMoveEvent(val entity: Entity, val newLocation: Location): UndefinedEvent() {
}