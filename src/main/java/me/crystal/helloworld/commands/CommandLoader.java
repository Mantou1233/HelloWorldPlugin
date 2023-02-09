package me.crystal.helloworld.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader {
    private static List<AMyCommand> commands = new ArrayList<>();

    public static <T extends AMyCommand> void register(T command){
        commands.add(command);
    }

    public static void load(){
        commands.forEach((v) -> {
            v.registerCommand();
        });
    }


}
