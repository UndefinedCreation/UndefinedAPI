package com.undefined.api

import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.UndefinedMenu
import com.undefined.api.menu.normal.button.ClickData
import com.undefined.api.menu.page.UndefinedPageMenu
import com.undefined.api.menu.page.button.PageButton
import com.undefined.api.utils.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.Inventory

class TestPage : UndefinedPageMenu("title", MenuSize.MINI, { Main.INSTANCE.value }) {
    override var clickData: ClickData.() -> Unit = {
        val page = object : UndefinedMenu("title", MenuSize.MINI) {
            override fun generateInventory() = createInventory {
                setItem(1, ItemBuilder(Material.EMERALD).setName("test").build()) {
                    println("click!")
                    Main.INSTANCE.value.add(ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("<reset><#ef4444>Test1").build())
                    println(Main.INSTANCE.value)
                    this@TestPage.update()
                    println(Main.INSTANCE.value)
                    player.openMenu(this@TestPage)
                }
            }
        }
        player.openMenu(page)
    }

    override fun generateInventory() = createPageInventory {
        setBackButton(PageButton(18, ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("<reset><#ef4444>ᴘʀᴇᴠɪᴏᴜѕ ᴘᴀɢᴇ").build(), ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("<reset><#ef4444>ᴘʀᴇᴠɪᴏᴜѕ ᴘᴀɢᴇ").build())) // TODO("Light red")
        setNextButton(PageButton(26, ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("<reset><#1ff04c>ɴᴇxᴛ ᴘᴀɢᴇ").build(), ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("<reset><#ef4444>ᴘʀᴇᴠɪᴏᴜѕ ᴘᴀɢᴇ").build())) // TODO("Greener green")
    }

}