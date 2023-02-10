package me.crystal.helloworld.commands.cmds;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.commands.AMyCommand;
import me.crystal.helloworld.listeners.ChestGuiListener;
import me.crystal.helloworld.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BalanceCommand extends AMyCommand {

    public BalanceCommand(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    public BalanceCommand(String name) {
        super(HelloWorldPlugin.getInstance(), name);
    }

    @Override
    public void init() {
        tab(0, (sender, _alias, args) -> {
            List<String> arguments = new ArrayList<>();
            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                arguments.add(p.getName());
            }
            return arguments;
        });

        setDescription("hi");
    }

    @Override
    public boolean exec(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        Player self = null;
        if(sender instanceof Player){
            self = (Player) sender;
        }
        Player player;
        if(args.length > 0){
            player = Bukkit.getServer().getPlayer(args[0]);
            if(player == null) player = self;
        }else {
            if(!(sender instanceof Player)){
                sender.sendMessage("cannot use bal in console");
            }
            player = self;
        }
        if(player == null) sender.sendMessage("doesnt work");
        Essentials ess = HelloWorldPlugin.getEss();
        User user = ess.getUser(player);
        sender.sendMessage(Translator.tr(String.format("&6Balance:&a %.2f &e*ducks*", user.getMoney())));

        return true;
    }
}
