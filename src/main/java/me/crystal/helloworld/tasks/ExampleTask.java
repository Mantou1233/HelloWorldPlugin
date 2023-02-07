package me.crystal.helloworld.tasks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.crystal.helloworld.HelloWorldPlugin;
import me.crystal.helloworld.utils.Translator;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExampleTask implements Runnable {
    private static Random random = new Random();

    private static Map<String, Integer> map = new HashMap<>();
    @Override
    public void run() {
        List<String> players = Bukkit
                .getServer()
                .getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList());
        Arrays.stream(map.keySet().toArray(new String[0])).filter(key -> {
            if(players.contains(key)) return true;
            map.remove(key);
            return false;
        });
        for(String key : players){
            int ticks;
            if(!map.containsKey(key)){
                ticks = 0;
            }
            else ticks = map.get(key);

            ticks += random.nextInt(2);
            if(ticks > 2000){
                Player p = Bukkit.getServer().getPlayerExact(key);
                if(p == null) continue;
                int money = random.nextInt(500);
                p.sendMessage(
                        Translator.tr(
                                String.format(
                                        "fdy &#CFF2FF»&r omg u so active so imma gift u *money*!! have fun with %s bucks",
                                        money
                                )));
                Essentials ess = HelloWorldPlugin.getEss();
                User user = ess.getUser(p);
                try{
                    user.giveMoney(BigDecimal.valueOf(money));
                } catch (MaxMoneyException err){
                    p.sendMessage("uh, you have too much money...");
                }
            }
            map.put(key, ticks);
        }
    }
}