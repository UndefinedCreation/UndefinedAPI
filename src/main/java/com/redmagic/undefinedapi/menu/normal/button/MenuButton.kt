package com.redmagic.undefinedapi.menu.normal.button

import com.redmagic.undefinedapi.menu.normal.UndefinedMenu

class MenuButton(override val slot: Int, val undefinedMenu: UndefinedMenu, override val consumer: ClickData.() -> Unit): Button(slot, consumer)