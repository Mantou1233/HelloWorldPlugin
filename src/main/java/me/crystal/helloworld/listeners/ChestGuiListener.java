package me.crystal.helloworld.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.Arrays;

public class ChestGuiListener implements Listener {
    private final Inventory inv;

    private static ChestGuiListener instance;

    public static ChestGuiListener getInstance () {
        return ChestGuiListener.instance;
    }

    public ChestGuiListener() {
        instance = this;
        inv = Bukkit.createInventory(null, 9, "Shop");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.DIAMOND, "hi", "§ahi world", "§apress me to buy me for 12 ducks"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
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

        // Using slots click is the best option for your inventory click's
        if(e.getRawSlot() == 0){
            Essentials ess = HelloWorldPlugin.getEss();
            User user = ess.getUser(p);
            if(!user.canAfford(BigDecimal.valueOf(12))) {
                p.sendMessage("You cant afford this diamond bozo");
            }
            p.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
            user.takeMoney(BigDecimal.valueOf(12));
            p.sendMessage(String.format("done! u now have %s ducks left", user.getMoney()));
        }
    }


    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}