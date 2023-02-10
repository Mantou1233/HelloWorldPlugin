package me.crystal.helloworld.ifaces;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ArgumentGetter {
    List<String> op(CommandSender sender, String label, String[] args);
}
