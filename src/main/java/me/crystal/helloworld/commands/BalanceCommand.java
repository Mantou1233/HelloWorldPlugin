package me.crystal.helloworld.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.listeners.ChestGuiListener;
import me.crystal.helloworld.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand extends AMyCommand {

    public BalanceCommand(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    public BalanceCommand(String name) {
        super(HelloWorldPlugin.getInstance(), name);
    }

    @Override
    public void init() {
        tab(0, "a", "b");
        setDescription("hi");
    }

    @Override
    public boolean exec(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("console cannot use!");
            return false;
        }
        new ChestGuiListener();
        Player player = (Player) sender;
        Essentials ess = HelloWorldPlugin.getEss();
        User user = ess.getUser(player);
        sender.sendMessage(Translator.tr(String.format("&6Balance:&a %.2f &e*ducks*", user.getMoney())));

        return true;
    }
}
