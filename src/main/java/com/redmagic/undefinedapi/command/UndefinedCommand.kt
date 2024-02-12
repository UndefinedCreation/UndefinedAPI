package com.redmagic.undefinedapi.command

import com.google.common.base.Strings
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import org.eclipse.sisu.Description

/**
 * This class represents an abstract command that can be executed in a Bukkit server.
 *
 * @param commandName The name of the command.
 * @param commandType The type of the command.
 * @param description The description of the command.
 * @param aliases The aliases for the command.
 * @param permission The permission required to execute the command.
 */
abstract class UndefinedCommand
(
        private val commandName: String,
        private val commandType: CommandType = CommandType.ALL,
        description: String = "Command created with UndefinedAPI",
        aliases : List<String> = emptyList(),
        permission: String = "",
        ) : BukkitCommand(commandName) {

    init {

        setDescription(description)
        setPermission(permission)
        setAliases(aliases)

        setField()

    }

    /**
     * Sets the field "commandMap" of the Bukkit server object to register the command.
     */
    private fun setField(){
        val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap");
        field.setAccessible(true)
        val commandMap = field.get(Bukkit.getServer()) as CommandMap
        commandMap.register(commandName, this)
    }

    /**
     * Executes a command based on the given command sender, command name, and arguments.
     *
     * @param sender The command sender who executed the command.
     * @param commandName The name of the command.
     * @param args The arguments passed to the command.
     * @return Returns true if the command was executed successfully, false otherwise.
     */
    override fun execute(sender: CommandSender, commandName: String, args: Array<out String>?): Boolean {
        val isCorrectType = when (commandType) {
            CommandType.ALL -> true
            CommandType.PLAYER -> sender is Player
            CommandType.CONSOLE -> sender is ConsoleCommandSender
        }

        return if (isCorrectType) {
            execute(sender, args)
            true
        } else false
    }

    /**
     * Provides tab completion for the tabComplete method in the UndefinedCommand class.
     *
     * @param sender the command sender
     * @param alias the command alias
     * @param args the command arguments
     * @return a list of possible completions for the command
     */
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        val commandUtil = tabComplete(sender, args)

        return StringUtil.copyPartialMatches(args[commandUtil.index], commandUtil.list, ArrayList())
    }

    /**
     * Executes the command.
     *
     * @param sender the command sender
     * @param args the command arguments
     */
    abstract fun execute(sender: CommandSender, args: Array<out String>?)

    /**
     * This method is used to generate a list of tab completions for a command.
     *
     * @param sender the CommandSender that triggered the tab completion
     * @param args the arguments provided by the sender for the command
     * @return a CommandTabUtil object containing a list of tab completions and the index of the argument currently being completed
     */
    abstract fun tabComplete(sender: CommandSender, args: Array<out String>?): CommandTabUtil

}

/**
 * Enum class representing the type of a command.
 */
enum class CommandType{
    PLAYER,
    CONSOLE,
    ALL
}