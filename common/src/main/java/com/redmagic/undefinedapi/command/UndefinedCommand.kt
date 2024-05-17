package com.redmagic.undefinedapi.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

/**
 * Abstract class representing an undefined command in Bukkit.
 *
 * @param commandName The name of the command.
 * @param commandType The type of the command. Default is [CommandType.ALL].
 * @param description The description of the command. Default is "Command created with UndefinedAPI".
 * @param aliases The aliases for the command. Default is an empty list.
 * @param permission The permission required to execute the command. Default is an empty string.
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
     * Sets the field "commandMap" of the Bukkit server object to enable registration of commands.
     * This method is private and can only be accessed within the same class.
     */
    private fun setField(){
        val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap");
        field.setAccessible(true)
        val commandMap = field.get(Bukkit.getServer()) as CommandMap
        commandMap.register(commandName, this)
    }

    /**
     * Executes the command based on the command sender, command name, and arguments.
     *
     * @param sender The command sender.
     * @param commandName The name of the command.
     * @param args The command arguments (optional).
     * @return Returns true if the command was executed successfully, false otherwise.
     */

    override fun execute(sender: CommandSender, commandName: String, args: Array<out String>?): Boolean {
        val isCorrectType = when (commandType) {
            CommandType.ALL -> true
            CommandType.PLAYER -> sender is Player
            CommandType.CONSOLE -> sender is ConsoleCommandSender
        }

        return if (isCorrectType) {
            execute(sender, args!!)
            true
        } else false
    }

    /**
     * Generates tab completions for a command.
     *
     * @param sender The command sender.
     * @param alias The command alias.
     * @param args The command arguments.
     * @return A list of tab completions.
     */
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        val commandUtil = tabComplete(sender, args)

        return StringUtil.copyPartialMatches(args[commandUtil.index], commandUtil.list, ArrayList())
    }

    /**
     * Executes the command.
     *
     * @param sender The command sender.
     * @param args The command arguments.
     */
    abstract fun execute(sender: CommandSender, args: Array<out String>)

    /**
     * Performs tab completion for a command.
     *
     * @param sender The command sender.
     * @param args The command arguments.
     * @return The CommandTabUtil object that represents the tab completion results.
     */
    abstract fun tabComplete(sender: CommandSender, args: Array<out String>): CommandTabUtil

}

/**
 * Enum class representing the type of a command.
 */
enum class CommandType{
    PLAYER,
    CONSOLE,
    ALL
}