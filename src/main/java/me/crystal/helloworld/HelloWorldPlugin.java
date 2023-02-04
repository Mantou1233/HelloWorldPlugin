package me.crystal.helloworld;

import me.crystal.helloworld.listeners.ChestGuiListener;
import org.bukkit.plugin.java.JavaPlugin;

import me.crystal.helloworld.commands.ExampleCommand;
import me.crystal.helloworld.listeners.PlayerJoinListener;
import me.crystal.helloworld.tasks.ExampleTask;

public class HelloWorldPlugin extends JavaPlugin {
    
    @Override
    public void onEnable () {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        HelloWorldPlugin.instance = this;

        // Register the example command
        this.getCommand("example").setExecutor(new ExampleCommand());
        
        // Register the example listener
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChestGuiListener(), this);

        // Register the example task
        final long taskRepeatEvery = this.getConfig().getInt("task-repeat-every") * 20L;
        this.getServer().getScheduler().runTaskTimer(this, new ExampleTask(), taskRepeatEvery, taskRepeatEvery);
    }

    private static HelloWorldPlugin instance;

    public static HelloWorldPlugin getInstance () {
        return HelloWorldPlugin.instance;
    }
}