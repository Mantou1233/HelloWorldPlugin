package me.crystal.helloworld;

import me.crystal.helloworld.commands.BalanceCommand;
import me.crystal.helloworld.listeners.ChestGuiListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.crystal.helloworld.commands.ExampleCommand;
import me.crystal.helloworld.listeners.PlayerJoinListener;
import me.crystal.helloworld.tasks.ExampleTask;

public class HelloWorldPlugin extends JavaPlugin {
    private static Economy econ = null;

    public static Economy getEconomy() {
        return econ;
    }
    @Override
    public void onEnable () {
        // Save default config
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            if(!setVault()){
                getLogger().info("no vault ;-;");
            }
        }
        this.saveDefaultConfig();

        // Set static instance
        HelloWorldPlugin.instance = this;

        // Register the example command
        this.getCommand("example").setExecutor(new ExampleCommand());
        this.getCommand("balance").setExecutor(new BalanceCommand());
        
        // Register the example listener
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChestGuiListener(), this);

        // Register the example task
        final long taskRepeatEvery = this.getConfig().getInt("task-repeat-every") * 20L;
        this.getServer().getScheduler().runTaskTimer(this, new ExampleTask(), taskRepeatEvery, taskRepeatEvery);
    }

    private boolean setVault(){
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    private static HelloWorldPlugin instance;

    public static HelloWorldPlugin getInstance () {
        return HelloWorldPlugin.instance;
    }
}