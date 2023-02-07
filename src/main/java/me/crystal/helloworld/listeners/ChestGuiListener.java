package me.crystal.helloworld.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.utils.Translator;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static me.crystal.helloworld.utils.InventoryUtils.consumeItem;
import static me.crystal.helloworld.utils.InventoryUtils.hasItem;

public class ChestGuiListener implements Listener {
    private Inventory inv;

    private static ConfigurationSection shop;
    private static ChestGuiListener instance;

    public static ChestGuiListener getInstance () {
        return ChestGuiListener.instance;
    }

    public ChestGuiListener() {
        ConfigurationSection config = HelloWorldPlugin.getInstance().getConfig();
        shop = config.getConfigurationSection("shop");
        instance = this;

        // Put the items into the inventory
        initializeInv();
    }

    // You can call this whenever you want to put the items in
    public void initializeInv() {
        inv = Bukkit.createInventory(null, (int) shop.get("size", 9), "Shop");
        ConfigurationSection items = shop.getConfigurationSection("items");
        for(String key : items.getKeys(false).toArray(new String[0])){
            MemorySection itemGetter = (MemorySection) items.get(key);
            if(itemGetter == null) continue;
            int buy = itemGetter.getInt("buy", 0);
            int sell = itemGetter.getInt("sell", 0);
            ItemStack item = itemGetter.getItemStack("item").clone();
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lores = meta.getLore();
            if(lores == null) lores = new ArrayList<>();
            lores.add(String.format(Translator.tr("&bbuy for %s &0-&r &a sell for %s"), buy, sell));
            meta.setLore(lores);
            item.setItemMeta(meta);

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
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Player p = (Player) e.getWhoClicked();

        ConfigurationSection items = shop.getConfigurationSection("items");
        MemorySection itemGetter = (MemorySection) items.get(String.format("%s", e.getRawSlot()));
        if(itemGetter == null) return;
        int buy = itemGetter.getInt("buy", 0);
        int sell = itemGetter.getInt("sell", 0);
        ItemStack item = itemGetter.getItemStack("item").clone();
        Essentials ess = HelloWorldPlugin.getEss();
        User user = ess.getUser(p);

        if (e.getAction().name() == "PICKUP_ALL") {
            if(user.getMoney().compareTo(BigDecimal.valueOf(buy)) == -1) {
                p.sendMessage(String.format("sorry but u don't have enough money 2 buy it"));
                return;
            }
            p.getInventory().addItem(item.clone());
            user.takeMoney(BigDecimal.valueOf(buy));
            p.sendMessage(String.format("done! u now have %s ducks left", user.getMoney()));

        } else if (e.getAction().name() == "PICKUP_HALF") {
            Inventory inv = p.getInventory();
            if(!hasItem(p, item)) {
                p.sendMessage(String.format("u have no item bro"));
                return;
            }
            boolean result = consumeItem(p, 1, item);

            try{
                user.giveMoney(BigDecimal.valueOf(sell));
            } catch (MaxMoneyException err){
                p.sendMessage("uh, you have too much money...");
            }
            p.sendMessage("done!!");
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