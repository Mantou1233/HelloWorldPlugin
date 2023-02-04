package me.crystal.helloworld.tasks;

import me.crystal.helloworld.utils.Translator;
import org.bukkit.Bukkit;

public class ExampleTask implements Runnable {
    @Override
    public void run() {
        final String message = Translator.get("messages.from-task");
        Bukkit.getServer().broadcastMessage(message);
    }
}