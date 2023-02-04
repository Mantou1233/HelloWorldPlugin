package me.crystal.helloworld.commands;

import com.earth2me.essentials.User;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.ess3.api.MaxMoneyException;
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

        try {
            sender.sendMessage(String.format("You have %s", Economy.getMoney(player.getName())));
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }
        try {
            Economy.add(player.getName(), 10.0);
        } catch (NoLoanPermittedException | MaxMoneyException | UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }
        try {
            sender.sendMessage(String.format("You were given %s and now have %s", Economy.format(10.0), Economy.getMoney(player.getName())));
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
