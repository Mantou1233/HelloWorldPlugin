package me.crystal.helloworld.commands;

import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.listeners.ChestGuiListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final String message = HelloWorldPlugin.getInstance().getConfig().getString("messages.from-command");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        ChestGuiListener.getInstance().openInventory((Player) sender);
        return true;
    }
}
