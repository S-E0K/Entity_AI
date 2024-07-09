package org.se0k.entity_ai.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commands extends BukkitCommand {
    public Commands(String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        ItemStack staff = new ItemStack(Material.STICK, 1);
        ItemMeta meta = staff.getItemMeta();
        meta.setDisplayName("마법의 지팡이");
        staff.setItemMeta(meta);

        if (player.getInventory().contains(staff)) return false;

        player.getInventory().addItem(staff);
        player.sendMessage("마법의 지팡이 지급");

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
