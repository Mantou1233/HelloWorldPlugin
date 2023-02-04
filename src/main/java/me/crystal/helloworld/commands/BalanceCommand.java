package me.crystal.helloworld.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.utils.Translator;
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
        Essentials ess = HelloWorldPlugin.getEss();
        User user = ess.getUser(player);
        sender.sendMessage(Translator.tr(String.format("&6You have %.2ffdy coins!!", user.getMoney())));

        return true;
    }
}
