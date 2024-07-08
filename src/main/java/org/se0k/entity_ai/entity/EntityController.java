package org.se0k.entity_ai.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.se0k.entity_ai.Entity_AI.instance;
import static org.se0k.entity_ai.Entity_AI.plugin;

public class EntityController implements EntityControl {

    static Map<UUID, Zombie> spawnEntity = new HashMap<>();

    int spawnDelay = 0;

    @Override
    public void EntitySpawn(Player player) {

        World world = player.getWorld();

        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3));

        if (spawnDelay == 0) {

            if (spawnEntity.size() >= 5) {
                player.sendMessage("전부 소환했습니다");
                return;
            }

            world.spawn(location, Zombie.class, zombie -> {
                zombie.setAI(true);
                zombie.setBaby(false);
                zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
                spawnEntity.put(zombie.getUniqueId(), zombie);
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    zombie.setAI(false);
                }, 20);
            });

            spawnDelay = 3;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (spawnDelay > 0) {
                        spawnDelay--;
                    }
                    if (spawnDelay == 0) {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        } else {
            player.sendMessage(spawnDelay + "초 남았습니다");
        }
    }

    @Override
    public void EntityMove(Player player) {

        if (spawnEntity.isEmpty()) return;

        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(10));

        for (Zombie zombie : spawnEntity.values()) {

            if (zombie.getLocation().distance(player.getLocation()) > 30) return;

            zombie.setAI(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.getPathfinder().moveTo(location, 2);
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    zombie.setAI(false);
                }, 20 * 5);
            }, 5);
        }
    }

    @Override
    public void EntityAttack(Player player) {

    }

    @Override
    public void EntitySurround(Player player) {

    }
}
