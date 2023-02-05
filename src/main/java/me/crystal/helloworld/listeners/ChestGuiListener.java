package me.crystal.helloworld.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.commands.ItemEntry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChestGuiListener implements Listener {
    private Inventory inv;

    private static ConfigurationSection shop;
    private static ChestGuiListener instance;

    public static ChestGuiListener getInstance () {
        return ChestGuiListener.instance;
    }

    public ChestGuiListener() {
        ConfigurationSection config = HelloWorldPlugin.getInstance().getConfig();
        shop = config.createSection("shop");
        instance = this;

        // Put the items into the inventory
        initializeInv();
    }

    // You can call this whenever you want to put the items in
    public void initializeInv() {
        inv = Bukkit.createInventory(null, (int) shop.get("size", 9), "Shop");
        ConfigurationSection items = shop.createSection("items");
        for(String key : items.getKeys(false)){
            ItemEntry itemEntry = (ItemEntry) items.get(key);
            ItemStack item = itemEntry.item;
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lores = meta.getLore();
            lores.add(String.format("Buy: %i, Sell: %i", itemEntry.buy, itemEntry.sell));
            meta.setLore(lores);

            int slot;
            try{
                slot = Integer.parseInt(key);
            } catch(NumberFormatException e) {
                slot = 0;
            }
            inv.setItem(slot, item);
        };
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Player p = (Player) e.getWhoClicked();

        ConfigurationSection items = shop.createSection("items");
        ItemEntry itemEntry = (ItemEntry) items.get(String.format("%s", e.getRawSlot()));
        if(itemEntry == null) return;
        Essentials ess = HelloWorldPlugin.getEss();
        User user = ess.getUser(p);
        if(!user.canAfford(itemEntry.buy)) {
            p.sendMessage("You cant afford this diamond bozo");
            return;
        }
        p.getInventory().addItem(itemEntry.item);
        user.takeMoney(itemEntry.buy);
        p.sendMessage(String.format("done! u now have %s ducks left", user.getMoney()));
    }


    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}