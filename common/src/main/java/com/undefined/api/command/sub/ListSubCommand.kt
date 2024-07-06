package com.undefined.api.command.sub

import com.undefined.api.command.info.ListSubCommandInfo
import org.bukkit.command.CommandSender
import java.util.UUID

class ListSubCommand<T>(
    private val subList: CommandSender.() -> List<T>,
    private val serialize: T.() -> String = { this.toString() },
    private val deserialize: String.() -> T = { this as T }
): UndefinedSubCommand("undefined_api_list") {

    private val listExe: MutableList<ListSubCommandInfo<T>.() -> Boolean> = mutableListOf()

    fun addListExecute(c: ListSubCommandInfo<T>.() -> Boolean): ListSubCommand<T> {
        listExe.add(c)
        return this
    }

    override fun getNames(sender: CommandSender): List<String> = subList.invoke(sender).map { serialize.invoke(it) }

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return false
        val stringValue = arg[indexOf]

        try {
            val t = deserialize.invoke(stringValue)
            listExe.forEach { it.invoke(ListSubCommandInfo(commandSender, t)) }
        }catch (_: Exception){}

        return true
    }
}