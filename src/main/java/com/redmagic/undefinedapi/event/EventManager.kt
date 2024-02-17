package com.redmagic.undefinedapi.event

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.extension.findMethods
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener


class EventManager : Listener {
    init {

        UndefinedAPI.plugin.findMethods(EventHandler::class.java).forEach{

            println("Method Test")

            val eventHandler = it.getAnnotation(EventHandler::class.java)

            val type = it.parameters[0].type as Class<out Event>

            println(type)

            Bukkit.getPluginManager().registerEvent(type, this, eventHandler.priority, {
                    _, event -> it.invoke(event)
            }, UndefinedAPI.plugin, eventHandler.ignoreCancelled)

        }

    }
}

