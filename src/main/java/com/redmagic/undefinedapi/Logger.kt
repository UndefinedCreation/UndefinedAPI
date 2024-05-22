package com.redmagic.undefinedapi

import org.bukkit.Bukkit
import java.util.logging.Level

/**
 * Sends a log message to the server console.
 *
 * @param message the log message to send
 */
fun sendLog(message: String) = Bukkit.getLogger().info(message)
/**
 * Sends each string in the [list] as a log message.
 *
 * @param list The list of log messages to send.
 */
fun sendLog(list: List<String>) = list.forEach{ sendLog(it) }
/**
 * Sends a warning message to the server log.
 *
 * @param message The warning message to be logged.
 */
fun sendWarming(message: String) = Bukkit.getLogger().warning(message)
/**
 * Sends a warning message to the server's logger for each string in the specified list.
 *
 * @param list the list of strings to send warnings for
 */
fun sendWarming(list: List<String>) = list.forEach{ sendWarming(it) }
/**
 * Sends an error message to the server log.
 *
 * @param message the error message to be logged
 */
fun sendError(message: String) = Bukkit.getLogger().log(Level.SEVERE, message)
/**
 * Sends an error message for each string in the given list.
 *
 * @param list the list of error messages to send
 */
fun sendError(list: List<String>) = list.forEach{ sendError(it) }