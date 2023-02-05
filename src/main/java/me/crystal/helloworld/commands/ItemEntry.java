package me.crystal.helloworld.commands;

import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class ItemEntry {
    public ItemStack item;
    public BigDecimal buy, sell;

    public ItemEntry(ItemStack _item, BigDecimal _buy, BigDecimal _sell) {
        item = _item;
        buy = _buy;
        sell = _sell;
    }
}
