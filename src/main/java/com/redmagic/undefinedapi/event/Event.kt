package com.redmagic.undefinedapi.event

import com.redmagic.undefinedapi.UndefinedAPI
import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

inline fun <reified T : Event> event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline callback: T.() -> Unit
): UndefinedListener = UndefinedListener().apply{
    UndefinedAPI.plugin.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        { _, event ->
            if (event is T) callback(event)

        },
        UndefinedAPI.plugin,
        ignoreCancelled
    )
}

class UndefinedListener: Listener{
    fun unregister() = HandlerList.unregisterAll(this)
}

open class UndefinedEvent(async: Boolean = false): Event(async), Cancellable{

    private var cancelled = false

    override fun isCancelled() = cancelled
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }
    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

}