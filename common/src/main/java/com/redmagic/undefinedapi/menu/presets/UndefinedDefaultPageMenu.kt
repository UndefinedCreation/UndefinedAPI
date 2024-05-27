package com.redmagic.undefinedapi.menu.presets

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.extension.string.translateColor
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.button.ClickData
import com.redmagic.undefinedapi.menu.page.UndefinedPageMenu
import com.redmagic.undefinedapi.menu.page.button.PageButton
import com.redmagic.undefinedapi.menu.setRow
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Represents a menu with undefined default page behavior.
 *
 * @property name The name of the menu.
 * @property list The list of items in the menu.
 * @property createInventoryCon The lambda function used to customize the inventory.
 * @property clickData The lambda function used to handle click events.
 */
class UndefinedDefaultPageMenu(name: String, list: MutableList<ItemStack>, private val createInventoryCon: Inventory.() -> Unit,
                               override var clickData: ClickData.() -> Unit
): UndefinedPageMenu(name, MenuSize.LARGE, list) {
    /**
     * Generates an inventory for the menu.
     *
     * @return The generated inventory.
     */
    override fun generateInventory(): Inventory = createPageInventory {


        setBackButton(
            PageButton(45, com.redmagic.undefinedapi.builders.ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("<reset><#d92323>ʙᴀᴄᴋ ᴀ ᴘᴀɢᴇ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀɴ ᴘᴀɢᴇ".translateColor()).build(),
                com.redmagic.undefinedapi.builders.ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())
        )

        setNextButton(
            PageButton(53, com.redmagic.undefinedapi.builders.ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName("<reset><#32e67d>ɴᴇxᴛ ᴘᴀɢᴇ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ᴛᴏ ᴛʜᴇ ɴᴇxᴛ ᴘᴀɢᴇ".translateColor()).build(), com.redmagic.undefinedapi.builders.ItemBuilder(
                Material.GRAY_STAINED_GLASS_PANE
            ).setName(" ").build())
        )

        setRow(5, com.redmagic.undefinedapi.builders.ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        createInventoryCon.invoke(this)
    }

}