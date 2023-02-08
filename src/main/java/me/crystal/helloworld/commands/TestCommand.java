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
        ChestGuiListener.getInstance().initializeInv();
        sender.sendMessage("Reloaded!!");
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args.length == 0){
            arguments.add("add");
            arguments.add("sus");
        }
        if(args.length == 1){
            switch(args[0]){
                case "add": {
                    for(Material mat : Material.values()) {
                        arguments.add(mat.toString());
                    }
                }
            }
        }
        return arguments;
    }
}

