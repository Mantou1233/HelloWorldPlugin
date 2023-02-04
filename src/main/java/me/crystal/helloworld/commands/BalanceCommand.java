package me.crystal.helloworld.commands;

import me.crystal.helloworld.HelloWorldPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("console cannot use!");
            return false;
        }
        Player player = (Player) sender;
        Economy econ = HelloWorldPlugin.getEconomy();
        if(econ == null) {
            sender.sendMessage("vault not found...");
            return false;
        }
        sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
        EconomyResponse r = econ.depositPlayer(player, 1.05);
        if(r.transactionSuccess()) {
            sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
        } else {
            sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
        }
        return true;
    }
}
