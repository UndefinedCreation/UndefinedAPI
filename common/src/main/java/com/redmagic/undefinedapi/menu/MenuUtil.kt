package com.redmagic.undefinedapi.menu

import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import org.bukkit.entity.Player

/**
 * Represents a menu action that can be performed by a player.
 *
 * @property undefinedMenu The undefined menu associated with the action.
 * @property player The player performing the action.
 */
class MenuAction(undefinedMenu: UndefinedMenu, player: Player)