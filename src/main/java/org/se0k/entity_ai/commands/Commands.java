package org.se0k.entity_ai.commands;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Commands extends BukkitCommand {
    public Commands(String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        CustomStack customStack = CustomStack.getInstance("staff:staff");
        ItemStack customItem = customStack.getItemStack();

        if (player.getInventory().contains(customItem)) return false;

        player.getInventory().addItem(customItem);
        player.sendMessage("마법의 지팡이 지급");

        return false;
    }
}
