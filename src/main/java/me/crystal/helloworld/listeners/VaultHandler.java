package me.crystal.helloworld.listeners;

import me.crystal.helloworld.HelloWorldPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;

import java.util.Objects;

public class VaultHandler implements Listener {
    private static VaultHandler instance;

    public static VaultHandler getInstance () {
        return VaultHandler.instance;
    }

    public VaultHandler() {
        instance = this;
    }

    @EventHandler
    public void onServiceRegister(ServiceRegisterEvent event) {
        if (event.getProvider().getService() == Economy.class) {
            HelloWorldPlugin.econ = Objects.requireNonNull(Bukkit.getServer().getServicesManager().getRegistration(Economy.class)).getProvider();
            HelloWorldPlugin.getInstance().getLogger().info("Successfully hooked into Vault for economy!");
        }
    }
}