package me.crystal.helloworld.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryUtils {
    public static boolean consumeItem(Player player, int count, ItemStack is) {
        Map<Integer, ? extends ItemStack> ammo = player
                .getInventory()
                .all(is.getType())
                .entrySet()
                .stream()
                .filter(map -> is.isSimilar(map.getValue()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

        player.updateInventory();
        return true;
    }

    public static boolean hasItem(Player player, ItemStack is) {
        Map<Integer, ? extends ItemStack> ammo = player
                .getInventory()
                .all(is.getType())
                .entrySet()
                .stream()
                .filter(map -> is.isSimilar(map.getValue()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        boolean found = false;
        for (ItemStack ignored : ammo.values()) found = true;
        return found;
    }

    public static ItemStack removeTags(ItemStack is, List<String> tags){

        return is;
    }
}
