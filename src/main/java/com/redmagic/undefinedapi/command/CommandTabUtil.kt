package com.redmagic.undefinedapi.command


/**
 * Utility class for Command and TabCompletion functionalities.
 *
 * @property list The list of strings.
 * @property index The index of the current element.
 */
data class CommandTabUtil(val list: List<String>, val index: Int)