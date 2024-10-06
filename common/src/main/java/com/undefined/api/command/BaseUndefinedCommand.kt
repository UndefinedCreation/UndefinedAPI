package com.undefined.api.command

import com.undefined.api.command.info.FullCommand
import com.undefined.api.command.info.ListSubCommandInfo
import com.undefined.api.command.sub.*
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

abstract class BaseUndefinedCommand {

    internal val incorrectMessages: MutableList<FullCommand.() -> Component> = mutableListOf()

    internal val fullExecute: MutableList<FullCommand.() -> Boolean> = mutableListOf()
    internal val generalExecute: MutableList<CommandSender.() -> Boolean> = mutableListOf()
    internal val playerExecute: MutableList<Player.() -> Boolean> = mutableListOf()
    internal val consoleExecute: MutableList<ConsoleCommandSender.() -> Boolean> = mutableListOf()

    val subCommandList: MutableList<UndefinedSubCommand> = mutableListOf()

    fun addIncorrectMessage(incorrectMessage: FullCommand.() -> Component): BaseUndefinedCommand {
        incorrectMessages.add(incorrectMessage)
        return this
    }

    fun addIncorrectMessage(incorrectMessage: String): BaseUndefinedCommand {
        incorrectMessages.add { Component.text(incorrectMessage) }
        return this
    }

    fun addIncorrectMessage(incorrectMessage: Component): BaseUndefinedCommand {
        incorrectMessages.add { incorrectMessage }
        return this
    }

    fun addFullExecute(c: FullCommand.() -> Boolean): BaseUndefinedCommand {
        fullExecute.add(c)
        return this
    }

    fun addExecute(c: CommandSender.() -> Boolean): BaseUndefinedCommand {
        generalExecute.add(c)
        return this
    }

    fun addExecutePlayer(c: Player.() -> Boolean): BaseUndefinedCommand {
        playerExecute.add(c)
        return this
    }

    fun addExecuteConsole(c: ConsoleCommandSender.() -> Boolean): BaseUndefinedCommand {
        consoleExecute.add(c)
        return this
    }

    fun addPlayerSubCommand(subCommand: PlayerSubCommand): PlayerSubCommand {
        if (subCommandList.filterIsInstance<PlayerSubCommand>().isNotEmpty()) return subCommand
        subCommandList.add(subCommand)
        return subCommand
    }

    fun addPlayerSubCommand(): PlayerSubCommand {
        return addPlayerSubCommand(PlayerSubCommand())
    }

    fun addSubCommand(subCommand: UndefinedSubCommand): UndefinedSubCommand {
        subCommandList.add(subCommand)
        return subCommand
    }

    fun addSubCommand(name: String): UndefinedSubCommand {
        val subCommand = UndefinedSubCommand(name)
        return addSubCommand(subCommand)
    }

    fun addStringSubCommand(stringSubCommand: StringSubCommand): StringSubCommand {
        if (subCommandList.filterIsInstance<StringSubCommand>().isNotEmpty()) return stringSubCommand
        subCommandList.add(stringSubCommand)
        return stringSubCommand
    }

    fun addStringSubCommand(): StringSubCommand {
        val subCommand = StringSubCommand()
        return addStringSubCommand(subCommand)
    }

    fun addNumberSubCommand(numberSubCommand: NumberSubCommand): NumberSubCommand {
        if (subCommandList.filterIsInstance<NumberSubCommand>().isNotEmpty()) return numberSubCommand
        subCommandList.add(numberSubCommand)
        return numberSubCommand
    }

    fun addNumberSubCommand(): NumberSubCommand {
        val subCommand = NumberSubCommand()
        return addNumberSubCommand(subCommand)
    }

    fun addBooleanSubCommand(booleanSubCommand: BooleanSubCommand): BooleanSubCommand {
        if (subCommandList.filterIsInstance<BooleanSubCommand>().isNotEmpty()) return booleanSubCommand
        subCommandList.add(booleanSubCommand)
        return booleanSubCommand
    }

    fun addBooleanSubCommand(): BooleanSubCommand {
        val subCommand = BooleanSubCommand()
        return addBooleanSubCommand(subCommand)
    }

    fun addEnumSubCommand(enumSubCommand: EnumSubCommand<*>): EnumSubCommand<*> {
        subCommandList.add(enumSubCommand)
        return enumSubCommand
    }

    inline fun <reified T :Enum<T>> addEnumSubCommand(): EnumSubCommand<T> {
        val subCommand = EnumSubCommand(T::class.java)
        subCommandList.add(subCommand)
        return subCommand
    }

    fun <T> addListSubCommand(list: CommandSender.() -> List<T>, serialize: T.() -> String = { this.toString() }, deserialize: ListSubCommandInfo<String>.() -> T = { this.value as T }): ListSubCommand<T> {
        val subCommand = ListSubCommand(list, serialize, deserialize)
        subCommandList.add(subCommand)
        return subCommand
    }

    fun <T> addListSubCommand(list: List<T>, serialize: T.() -> String = { this.toString() }, deserialize: ListSubCommandInfo<String>.() -> T = { this.value as T }): ListSubCommand<T> {
        val subCommand = ListSubCommand({ list }, serialize, deserialize)
        subCommandList.add(subCommand)
        return subCommand
    }

    fun clearExecute() = generalExecute.clear()
    fun clearPlayerExecute() = playerExecute.clear()
    fun clearConsoleExecute() = consoleExecute.clear()

    val list = listOf(
        PlayerSubCommand::class.java,
        EnumSubCommand::class.java,
        StringSubCommand::class.java,
        NumberSubCommand::class.java,
        BooleanSubCommand::class.java,
        ListSubCommand::class.java
    )

    internal fun getSubCommand(name: String): UndefinedSubCommand? = subCommandList.filter { it.name == name }.getOrNull(0) ?: subCommandList.filter { list.contains(it::class.java) }.getOrNull(0)
    abstract fun getNames(sender: CommandSender): List<String>
    abstract fun runSpecialExecute(args: Array<out String>, commandSender: CommandSender, indexOf: Int): Boolean

}