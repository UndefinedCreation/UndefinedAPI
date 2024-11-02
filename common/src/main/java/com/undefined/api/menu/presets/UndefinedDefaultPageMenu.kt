package com.undefined.api.menu.presets

import com.undefined.api.extension.string.translateColor
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.button.ClickData
import com.undefined.api.menu.page.UndefinedPageMenu
import com.undefined.api.menu.page.button.PageButton
import com.undefined.api.menu.setRow
import com.undefined.api.utils.item.ItemBuilder
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
class UndefinedDefaultPageMenu(
    name: String,
    list: MutableList<ItemStack>,
    private val createInventoryCon: Inventory.() -> Unit,
    override var clickData: ClickData.() -> Unit
): UndefinedPageMenu(name, MenuSize.LARGE, list) {

    /**
     * Generates an inventory for the menu.
     *
     * @return The generated inventory.
     */
    override fun generateInventory(): Inventory = createPageInventory {
        setBackButton(
            PageButton(
                45,
                ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                    .setName("<reset><#d92323>ʙᴀᴄᴋ ᴀ ᴘᴀɢᴇ")
                    .addPlainLore(" ")
                    .addLore("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀɴ ᴘᴀɢᴇ").build(),
                ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()
            )
        )
        setNextButton(
            PageButton(
                53,
                ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                    .setName("<reset><#32e67d>ɴᴇxᴛ ᴘᴀɢᴇ")
                    .addPlainLore(" ")
                    .addLore("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ᴛᴏ ᴛʜᴇ ɴᴇxᴛ ᴘᴀɢᴇ")
                    .build(),
                ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()
            )
        )

        setRow(5, ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())
        createInventoryCon.invoke(this)
    }

}