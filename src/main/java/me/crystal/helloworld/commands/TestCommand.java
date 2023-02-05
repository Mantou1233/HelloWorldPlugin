package me.crystal.helloworld.commands;

import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.listeners.ChestGuiListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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

        HelloWorldPlugin.getInstance().getConfig().set("itemstack-test", player.getInventory().getItemInMainHand());

        ItemStack item = HelloWorldPlugin.getInstance().getConfig().getItemStack("itemstack-test");
        if(item == null) return false;
        player.getInventory().addItem(item);
        sender.sendMessage("OK");
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
