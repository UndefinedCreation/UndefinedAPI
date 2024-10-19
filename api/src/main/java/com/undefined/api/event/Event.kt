package com.undefined.api.event

import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

/**
 * Registers an event listener for a specific type of event with the given priority and cancellation options.
 *
 * @param T The type of event to listen for.
 * @param priority The priority of the event listener. Default value is EventPriority.NORMAL.
 * @param ignoreCancelled Determines whether cancelled events should be ignored. Default value is false.
 * @param callback The lambda function that will be executed when the event occurs. It takes an instance of the event as a receiver and does not return any value.
 * @return An instance of UndefinedListener that can be used to unregister the event listener.
 */
inline fun <reified T : Event> event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline callback: T.() -> Unit
): UndefinedListener = UndefinedListener().apply {
    com.undefined.api.API.plugin.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        { _, event ->
            if (event is T) callback(event)
        },
        com.undefined.api.API.plugin,
        ignoreCancelled
    )
}

/**
 * Implementation of the [Listener] interface that provides a method to unregister the listener from the [HandlerList].
 */
class UndefinedListener: Listener {
    fun unregister() = HandlerList.unregisterAll(this)
}

/**
 * Represents an undefined event that can be triggered in the application.
 *
 * @property async Specifies whether the event should be handled asynchronously or not. Default value is false.
 */
open class UndefinedEvent(async: Boolean = false): Event(async), Cancellable {

    /**
     * Represents the cancellation status of an event.
     *
     * Cancelling an event typically means preventing the event from further processing or propagation.
     * This variable can be used to determine if an event has been cancelled or not.
     */
    private var cancelled = false

    /**
     * Returns whether the event has been cancelled or not.
     *
     * @return true if the event has been cancelled, false otherwise
     */
    override fun isCancelled() = cancelled

    /**
     * Sets the cancellation state of the event. A cancelled event will not be called
     * by event handlers, and will not propagate to other event listeners.
     *
     * @param cancel true to cancel the event, false otherwise
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    /**
     *
     * @hidden
     *
     * The companion object for the [UndefinedEvent] class.
     *
     * This companion object provides access to the [HandlerList] used to manage event handlers for [UndefinedEvent].
     */
    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    /**
     * Returns the list of handlers for this undefined event.
     *
     * @return The list of handlers for this event.
     */
    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    fun call() = Bukkit.getPluginManager().callEvent(this)

}