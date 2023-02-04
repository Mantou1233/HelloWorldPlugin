package me.crystal.helloworld.tasks;

import me.crystal.helloworld.HelloWorldPlugin;
import org.bukkit.Bukkit;

public class ExampleTask implements Runnable {
    @Override
    public void run() {
        final String message = HelloWorldPlugin.getInstance().getConfig().getString("messages.from-task");
        Bukkit.getServer().broadcastMessage(message);
    }
}