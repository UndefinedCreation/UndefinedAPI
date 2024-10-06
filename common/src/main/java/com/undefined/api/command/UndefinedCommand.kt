package com.undefined.api.command

import com.undefined.api.command.info.AllCommand
import com.undefined.api.command.sub.UndefinedSubCommand
import com.undefined.api.nms.extensions.getPrivateField
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil


class UndefinedCommand(name: String, permission: String? = null, description: String = "", aliases: List<String> = emptyList()): BaseUndefinedCommand()  {

    init {
        UndefinedCoreCommand(name, permission, description, aliases, {
            val player: Player?
            val console: ConsoleCommandSender?
            when {
                sender is Player -> {
                    player = sender
                    playerExecute.forEach { execution -> if (!execution.invoke(player)) return@UndefinedCoreCommand false }
                }
                else -> {
                    console = sender as ConsoleCommandSender
                    consoleExecute.forEach { execution -> if (!execution.invoke(console)) return@UndefinedCoreCommand false }
                }
            }

            genExecute.forEach { execution -> if (!execution.invoke(sender)) return@UndefinedCoreCommand false }
            mainExecute.forEach { execution -> if (!execution.invoke(AllCommand(sender, arg))) return@UndefinedCoreCommand false}

            if (arg.isNullOrEmpty()) return@UndefinedCoreCommand false

            var lastSub: BaseUndefinedCommand = this@UndefinedCommand
            arg.forEach { arg ->
                val sub = getAndRun(lastSub, arg, this.arg, sender is Player, sender, this.arg.indexOf(arg)) ?: return@UndefinedCoreCommand false
                lastSub = sub
            }

            return@UndefinedCoreCommand true

        }, {
            if (this.second.isNullOrEmpty()) return@UndefinedCoreCommand mutableListOf()
            val index = this.second!!.size - 1
            return@UndefinedCoreCommand StringUtil.copyPartialMatches(this.second!![index], getAllSubCommandNames(this.second!!, this.first), mutableListOf())
        })
    }

    private fun getAllSubCommandNames(args: Array<out  String>, sender: CommandSender): List<String> {
        if (args.isEmpty()) return emptyList()
        var lastSub: BaseUndefinedCommand = this
        var index = 0
        val maxIndex = args.size - 1

        args.onEach {
            if (index == maxIndex) return getNameList(lastSub.subCommandList, sender)
            lastSub = lastSub.getSubCommand(args[index]) ?: return emptyList()
            index++
        }

        return emptyList()
    }

    private fun getNameList(list: List<UndefinedSubCommand>, sender: CommandSender): List<String> = list.flatMap { it.getNames(sender) }

    private fun getAndRun(
        sub: BaseUndefinedCommand,
        name: String,
        arg: Array<out String>?,
        isPlayer: Boolean,
        commandSender: CommandSender,
        indexOf: Int
    ) : UndefinedSubCommand? {
        val command = sub.getSubCommand(name)
        command?.let {
            if (isPlayer) {
                it.playerExecute.forEach { func ->
                    (commandSender as? Player)?.let { player ->
                        if (!func.invoke(player)) return null
                    }
                }
            } else {
                it.consoleExecute.forEach { func ->
                    (commandSender as? ConsoleCommandSender)?.let { console ->
                        if (!func.invoke(console)) return null
                    }
                }
            }
            it.genExecute.forEach { if (!it.invoke(commandSender)) return null }
            it.mainExecute.forEach { if (!it.invoke(AllCommand(commandSender, arg))) return null}

            if (!it.runSpecialExecute(arg!!, commandSender, indexOf)) return null

        }
        return command
    }

    override fun getNames(sender: CommandSender): List<String> = listOf()
    override fun runSpecialExecute(args: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean = true

}

class UndefinedCoreCommand(name: String, permission: String? = null, description: String = "", aliases: List<String> = emptyList(), private val execution: AllCommand.() -> Boolean, private val tabCompletion: Pair<CommandSender, Array<out String>?>.() -> MutableList<String>) : BukkitCommand(name) {

    override fun execute(sender: CommandSender, alias: String, args: Array<out String>?): Boolean = execution.invoke(AllCommand(sender, args))
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> = tabCompletion.invoke(Pair(sender, args))

    init {
        setDescription(description)
        setPermission(permission)
        setAliases(aliases)
        setField()
    }

    private fun setField() {
        val commandMap = Bukkit.getServer().getPrivateField<CommandMap>("commandMap")
        commandMap.register(name, this)
    }

}


