package com.redmagic.undefinedapi.menu.button

import com.redmagic.undefinedapi.menu.UndefinedMenu

class MenuButton(override val slot: Int, val undefinedMenu: UndefinedMenu, override val consumer: ClickData.() -> Unit): Button(slot, consumer)