package com.undefined.api.command.sub

import com.undefined.api.command.BaseUndefinedCommand
import org.bukkit.command.CommandSender
import java.util.UUID

open class UndefinedSubCommand(val name: String): BaseUndefinedCommand() {
    override fun getNames(sender: CommandSender): List<String> = listOf(name)
    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean = true
}