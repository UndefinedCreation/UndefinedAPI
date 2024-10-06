package com.undefined.api.command.sub

import com.undefined.api.command.info.ListSubCommandInfo
import org.bukkit.command.CommandSender

class ListSubCommand<T>(
    private val subList: CommandSender.() -> List<T>,
    private val serialize: T.() -> String = { this.toString() },
    private val deserialize: ListSubCommandInfo<String>.() -> T = { this.value as T }
): UndefinedSubCommand("undefined_api_list") {

    private val listExe: MutableList<ListSubCommandInfo<T>.() -> Boolean> = mutableListOf()

    fun addListExecute(c: ListSubCommandInfo<T>.() -> Boolean): ListSubCommand<T> {
        listExe.add(c)
        return this
    }

    override fun getNames(sender: CommandSender): List<String> = subList.invoke(sender).map { serialize.invoke(it) }

    override fun runSpecialExecute(args: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (args.isEmpty()) return false
        val stringValue = args[indexOf]

        try {
            val value = deserialize.invoke(ListSubCommandInfo(commandSender, stringValue))
            listExe.forEach { it.invoke(ListSubCommandInfo(commandSender, value)) }
        } catch (_: Exception) {
        }

        return true
    }
}