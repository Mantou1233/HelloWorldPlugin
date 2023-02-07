package me.crystal.helloworld;

import com.earth2me.essentials.Essentials;
import me.crystal.helloworld.commands.BalanceCommand;
import me.crystal.helloworld.commands.TestCommand;
import me.crystal.helloworld.listeners.ChestGuiListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.crystal.helloworld.commands.ShopCommand;
import me.crystal.helloworld.listeners.PlayerJoinListener;
import me.crystal.helloworld.tasks.ExampleTask;

import java.util.Objects;

public class HelloWorldPlugin extends JavaPlugin {
    public static Essentials ess = null;
    public static Essentials getEss(){
        return ess;
    }
    @Override
    public void onEnable () {
        // Get dependent plugin instance
        ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        HelloWorldPlugin.instance = this;

        // Register the example command
        Objects.requireNonNull(this.getCommand("shop")).setExecutor(new ShopCommand());
        Objects.requireNonNull(this.getCommand("balance")).setExecutor(new BalanceCommand());
        TestCommand testCommandInstance = new TestCommand();
        Objects.requireNonNull(this.getCommand("test")).setExecutor(testCommandInstance);
        Objects.requireNonNull(this.getCommand("test")).setTabCompleter(testCommandInstance);
        
        // Register the example listener
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChestGuiListener(), this);

        // Register the example task
        this.getServer().getScheduler().runTaskTimer(this, new ExampleTask(), 20L, 20L);
    }
    private static HelloWorldPlugin instance;

    public static HelloWorldPlugin getInstance () {
        return HelloWorldPlugin.instance;
    }
}