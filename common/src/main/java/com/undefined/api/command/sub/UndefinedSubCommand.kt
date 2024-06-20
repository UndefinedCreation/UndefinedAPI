package com.undefined.api.command.sub

import com.undefined.api.command.BaseUndefinedCommand
import org.bukkit.command.CommandSender


open class UndefinedSubCommand(val name: String): BaseUndefinedCommand() {

    override fun getNames(): List<String> = listOf(name)
    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean = true
}