package org.se0k.entity_ai.effect;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;

import static org.se0k.entity_ai.Entity_AI.instance;
import static org.se0k.entity_ai.Entity_AI.plugin;

public class EffectController implements EffectControl {
    @Override
    public void effectSet(Player player) {
        World world = player.getWorld();

        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3));
        location.add(0, 3, 0);

        world.spawn(location, ItemDisplay.class, display -> {

            Transformation transformation = display.getTransformation();

            CustomStack customStack = CustomStack.getInstance("circle:circle");
            ItemStack customItem = customStack.getItemStack();

            display.setItemStack(customItem);

            new BukkitRunnable() {
                int angle = 0;
                @Override
                public void run() {
                    if (display.isDead()) {
                        cancel();
                        return;
                    }
                    angle += 5;
                    transformation.getLeftRotation().setAngleAxis(angle, 0, 0, 1);
                    transformation.getScale().add(0.1f, 0.1f, 0.1f);
                    display.setTransformation(transformation);
                }
            }.runTaskTimer(plugin, 0, 5);

            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, display::remove, 20 * 3);
        });
    }
}
