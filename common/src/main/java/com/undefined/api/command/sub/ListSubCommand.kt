package com.undefined.api.command.sub

import org.bukkit.command.CommandSender
import java.util.UUID

class ListSubCommand<T> (val subList: List<T>): UndefinedSubCommand("undefined_api_list") {

    private val listExe: MutableList<T.() -> Boolean> = mutableListOf()

    fun addListExecute(c: T.() -> Boolean): ListSubCommand<T> {
        listExe.add(c)
        return this
    }

    override fun getNames(): List<String> = subList.map { it.toString() }

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return false
        val stringValue = arg[indexOf]

        try {
            val value = stringValue as T
            listExe.forEach { execution -> if (!execution.invoke(value)) return false }
        }catch (e: Exception) {
            try {
                val uuid = UUID.fromString(stringValue)
                listExe.forEach { execution -> if (!execution.invoke(uuid as T)) return false }
            } catch (e : Exception) { }
        }
        return true
    }
}