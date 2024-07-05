package org.se0k.entity_ai.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Commands extends BukkitCommand {
    public Commands(String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        World world = player.getWorld();

        world.spawn(player.getLocation(), Zombie.class, entity -> {



        });




        return false;
    }






    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
//        if (args.length == 1) return Arrays.asList("in", "out", "set", "start", "end", "stage", "start");
//
//        if (args[0].equals("set") && args.length == 2) return Arrays.asList("normal", "hard");

        return null;
    }

}
