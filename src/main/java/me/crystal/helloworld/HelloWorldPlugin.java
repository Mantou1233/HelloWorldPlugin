package me.crystal.helloworld;

import me.crystal.helloworld.commands.BalanceCommand;
import me.crystal.helloworld.listeners.ChestGuiListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import me.crystal.helloworld.commands.ExampleCommand;
import me.crystal.helloworld.listeners.PlayerJoinListener;
import me.crystal.helloworld.tasks.ExampleTask;

import java.util.Objects;

public class HelloWorldPlugin extends JavaPlugin {
    private static Economy econ = null;

    public static Economy getEconomy() {
        return econ;
    }
    @Override
    public void onEnable () {
        // Save default config
        if(!setupVault()){
            getLogger().info("no vault ;-;");
        }
        this.saveDefaultConfig();

        // Set static instance
        HelloWorldPlugin.instance = this;

        // Register the example command
        Objects.requireNonNull(this.getCommand("example")).setExecutor(new ExampleCommand());
        Objects.requireNonNull(this.getCommand("balance")).setExecutor(new BalanceCommand());
        
        // Register the example listener
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChestGuiListener(), this);

        // Register the example task
        final long taskRepeatEvery = this.getConfig().getInt("task-repeat-every") * 20L;
        this.getServer().getScheduler().runTaskTimer(this, new ExampleTask(), taskRepeatEvery, taskRepeatEvery);
    }

    private boolean setupVault(){
        getLogger().info("ae!!");
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().info("ae");
            return false;
        }
        econ = rsp.getProvider();
        getLogger().info("ae??");
        return econ != null;
    }


    private static HelloWorldPlugin instance;

    public static HelloWorldPlugin getInstance () {
        return HelloWorldPlugin.instance;
    }
}