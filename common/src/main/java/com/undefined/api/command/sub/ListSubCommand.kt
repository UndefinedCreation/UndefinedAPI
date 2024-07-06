package com.undefined.api.command.sub

import org.bukkit.command.CommandSender

class ListSubCommand<T>(
    private val subList: List<T>,
    private val serialize: T.() -> String = { this.toString() },
    private val deserialize: String.() -> T = { this as T }
): UndefinedSubCommand("undefined_api_list") {

    private val listExe: MutableList<T.() -> Boolean> = mutableListOf()

    fun addListExecute(c: T.() -> Boolean): ListSubCommand<T> {
        listExe.add(c)
        return this
    }

    override fun getNames(): List<String> = subList.map { serialize.invoke(it) }

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return false
        val stringValue = arg[indexOf]

        try {
            val t = deserialize.invoke(stringValue)
            listExe.forEach { it.invoke(t) }
        }catch (_: Exception){}

        return true
    }
}