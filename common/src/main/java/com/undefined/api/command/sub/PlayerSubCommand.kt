package com.undefined.api.command.sub

import com.undefined.api.command.info.TargetCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

class PlayerSubCommand: UndefinedSubCommand("undefined_api_players") {

    private val targetExe: MutableList<TargetCommand.() -> Boolean> = mutableListOf()

    fun addTargetExecute(c: TargetCommand.() -> Boolean): PlayerSubCommand {
        targetExe.add(c)
        return this
    }

    fun clearTargetExecute() = targetExe.clear()

    override fun getNames(sender: CommandSender): List<String> = Bukkit.getOnlinePlayers().map { it.name }
    override fun runSpecialExecute(arg: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean {
        if (arg.isEmpty()) return false
        val offinePlayer = Bukkit.getOfflinePlayer(arg[indexOf])
        if (!offinePlayer.isOnline) return false


        targetExe.forEach { execution ->

            if (!execution.invoke(TargetCommand(commandSender, offinePlayer.player!!))) return false

        }
        return true
    }
}

