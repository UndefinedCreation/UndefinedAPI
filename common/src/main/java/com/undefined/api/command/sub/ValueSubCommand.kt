package com.undefined.api.command.sub

import com.undefined.api.command.info.BooleanSubCommandInfo
import com.undefined.api.command.info.NumberSubCommandInfo
import com.undefined.api.command.info.StringSubCommandInfo
import org.bukkit.command.CommandSender
import java.util.*

class StringSubCommand: UndefinedSubCommand("undefined_api_value") {

    private val stringExe: MutableList<StringSubCommandInfo.() -> Boolean> = mutableListOf()

    fun addStringExecute(c: StringSubCommandInfo.() -> Boolean): StringSubCommand {
        stringExe.add(c)
        return this
    }

    fun clearStringExecute() = stringExe.clear()

    override fun getNames(sender: CommandSender): List<String> = listOf("<text>")

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return  false
        val string = arg[indexOf]

        stringExe.forEach { execution ->
            if (!execution.invoke(StringSubCommandInfo(commandSender, string))) return false
        }
        return true
    }
}

class NumberSubCommand: UndefinedSubCommand("undefined_api_number") {

    private val numberExe: MutableList<NumberSubCommandInfo.() -> Boolean> = mutableListOf()

    fun addNumberExecute(c: NumberSubCommandInfo.() -> Boolean): NumberSubCommand {
        numberExe.add(c)
        return this
    }

    fun clearNumberExecute() = numberExe.clear()

    override fun getNames(sender: CommandSender): List<String> = listOf("<number>")

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return  false
        val stringN = arg[indexOf]

        try {
            numberExe.forEach { execution ->
                if (!execution.invoke(NumberSubCommandInfo(commandSender, stringN.toDouble()))) return false
            }
        } catch (_: NumberFormatException) {}

        return true
    }
}

class BooleanSubCommand: UndefinedSubCommand("undefined_api_boolean") {

    private val booleanExe: MutableList<BooleanSubCommandInfo.() -> Boolean> = mutableListOf()

    fun addNumberExecute(c: BooleanSubCommandInfo.() -> Boolean): BooleanSubCommand {
        booleanExe.add(c)
        return this
    }

    fun clearNumberExecute() = booleanExe.clear()

    override fun getNames(sender: CommandSender): List<String> = listOf("true", "false")

    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return  false
        val stringN = arg[indexOf]

        stringN.toBoolean().let {
            booleanExe.forEach { execution ->
                if (!execution.invoke(BooleanSubCommandInfo(commandSender, it))) return false
            }
        }

        return true
    }
}