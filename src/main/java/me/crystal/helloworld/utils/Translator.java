package me.crystal.helloworld.utils;

import me.crystal.helloworld.HelloWorldPlugin;
import org.bukkit.ChatColor;

import java.math.BigDecimal;

public class Translator {
    public static String get(String path, Boolean translate){
        String message = HelloWorldPlugin.getInstance().getConfig().getString(path);
        if(translate) {
            assert message != null;
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        return message;
    }
    public static String get(String path){
        return get(path, true);
    }
    public static String tr(final String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
