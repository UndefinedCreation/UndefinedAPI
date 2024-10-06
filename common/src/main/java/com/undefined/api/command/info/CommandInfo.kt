package com.undefined.api.command.info

import org.bukkit.command.CommandSender

import org.bukkit.entity.Player

open class TargetCommand(open val sender: CommandSender, open val target: Player)

data class AllCommand(val sender: CommandSender, val arg: Array<out String>?)

data class StringSubCommandInfo(val sender: CommandSender, val string: String)

data class NumberSubCommandInfo(val sender: CommandSender, val number: Double)

data class BooleanSubCommandInfo(val sender: CommandSender, val boolean: Boolean)

class EnumSubCommandInfo<T: Enum<T>>(val sender: CommandSender, val value: T)

data class ListSubCommandInfo<T>(val sender: CommandSender, val value: T)