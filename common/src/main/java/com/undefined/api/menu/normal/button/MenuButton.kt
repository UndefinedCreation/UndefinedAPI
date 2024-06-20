package com.undefined.api.menu.normal.button

import com.undefined.api.menu.normal.UndefinedMenu

/**
 * Represents a menu button that can be clicked.
 *
 * @property slot The slot number where the button should be placed.
 * @property undefinedMenu The undefined menu associated with the button.
 * @property consumer The lambda expression that takes a [ClickData] object as an argument and performs actions based on the click event.
 */
class MenuButton(override val slot: Int, val undefinedMenu: UndefinedMenu, override val consumer: ClickData.() -> Unit = {}): Button(slot, consumer)
