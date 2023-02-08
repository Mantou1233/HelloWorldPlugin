package me.crystal.helloworld.utils;

import me.crystal.helloworld.HelloWorldPlugin;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    public static String get(String path, Boolean translate){
        String message = HelloWorldPlugin.getInstance().getConfig().getString(path);
        if(translate && message == null) {
            message = Translator.tr(message);
        }
        return message;
    }
    public static String get(String path){
        return get(path, true);
    }
    public static String tr(String message){
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, "" + ChatColor.of(color));
            matcher = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
