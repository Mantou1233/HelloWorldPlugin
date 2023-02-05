package me.crystal.helloworld.commands;

import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.listeners.ChestGuiListener;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("console cannot use!");
            return false;
        }
        Player player = (Player) sender;
        ConfigurationSection config = HelloWorldPlugin.getInstance().getConfig();
        ConfigurationSection shop = config.getConfigurationSection("shop");
        shop.set("size", 9);
        ConfigurationSection items = shop.getConfigurationSection("items");
        items.set("0", new ItemEntry(new ItemStack(Material.DIAMOND), BigDecimal.valueOf(69), BigDecimal.valueOf(69)));
        
        HelloWorldPlugin.getInstance().saveConfig();
        ChestGuiListener.getInstance().initializeInv();
        sender.sendMessage("Reloaded!!");
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> argList = new ArrayList<>();
        argList.add("read");
        argList.add("write");
        return argList;
    }
}

