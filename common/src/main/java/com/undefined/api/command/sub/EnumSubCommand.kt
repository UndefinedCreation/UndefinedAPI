package com.undefined.api.command.sub

import com.undefined.api.command.info.EnumSubCommandInfo
import org.bukkit.command.CommandSender

class EnumSubCommand<T: Enum<T>>(
    val enumClass: Class<T>
): UndefinedSubCommand("undefined_api_enum") {

    private val enumExe: MutableList<EnumSubCommandInfo<T>.() -> Boolean> = mutableListOf()
    private val names = enumClass.enumConstants.map { it.name }
    override fun getNames(sender: CommandSender): List<String> = names

    fun addEnumExecute(c: EnumSubCommandInfo<T>.() -> Boolean): EnumSubCommand<T> {
        enumExe.add(c)
        return this
    }

    fun clearEnumExecute() = enumExe.clear()

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return false
        val string = arg[indexOf]
        val enumV = getEnumValue(string)

        enumExe.forEach { execution ->
            if (!execution.invoke(EnumSubCommandInfo(commandSender, enumV))) return false
        }

        return true
    }

    fun getEnumValue(string: String): T {
        val m = enumClass.getMethod("valueOf", String::class.java)
        m.isAccessible = true
        return m.invoke(null, string) as T
    }

}