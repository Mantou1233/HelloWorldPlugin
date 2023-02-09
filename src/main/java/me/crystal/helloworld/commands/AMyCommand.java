package me.crystal.helloworld.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


interface ArgumentGetter {
    List<String> op(CommandSender sender, String label, String[] args);
}
public abstract class AMyCommand<T extends JavaPlugin> extends Command implements PluginIdentifiableCommand {

    private static CommandMap commandMap;

    private static List<ArgumentGetter> tabs = new ArrayList<>();

    static {
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final T plugin;
    private boolean register = false;


    /**
     * @param plugin plugin responsible of the command.
     * @param name   name of the command.
     */
    AMyCommand(T plugin, String name) {
        super(name);

        assert commandMap != null;
        assert plugin != null;
        assert name.length() > 0;

        setLabel(name);
        init();
        this.plugin = plugin;
    }

    /**
     * @param aliases aliases of the command.
     */
    protected void setAliases(String... aliases) {
        if (aliases != null && (register || aliases.length > 0))
            setAliases(Arrays.stream(aliases).collect(Collectors.toList()));
    }

    abstract public void init();
    abstract public boolean exec(CommandSender sender, Command command, String label, String[] args);


    public void tab(int indice, ArgumentGetter consumer) {
        tabs.set(indice, consumer);
    }

    public void tab(int indice, String... args) {
        tab(indice, (sender, label, _args) -> Arrays.asList(args));
    }

    /**
     * /!\ to do at the end /!\ to save the command.
     *
     * @return true if the command has been successfully registered
     */
    protected boolean registerCommand() {
        if (!register) {
            register = commandMap.register(plugin.getName(), this);
        }
        return register;
    }

    @Override
    public T getPlugin() {
        return this.plugin;
    }


    // this overrides origin
    @Override
    public boolean execute(CommandSender commandSender, String command, String[] arg) {
        if (getPermission() != null) {
            if (!commandSender.hasPermission(getPermission())) {
                if (getPermissionMessage() == null) {
                    commandSender.sendMessage(ChatColor.RED + "no permit!");
                } else {
                    commandSender.sendMessage(getPermissionMessage());
                }
                return false;
            }
        }
        if (exec(commandSender, this, command, arg))
            return true;
        commandSender.sendMessage(ChatColor.RED + getUsage());
        return false;

    }

    // this overrides origin
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

        int indice = args.length - 1;
        List<String> empty = new ArrayList<>();

        ArgumentGetter argFn = tabs.get(indice);
        if(argFn == null) return empty;
        List<String> result = argFn.op(sender, alias, args);
        if(result == null) return empty;
        return result;
    }
}