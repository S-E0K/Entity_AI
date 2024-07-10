package org.se0k.entity_ai.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.se0k.entity_ai.effect.EffectControl;
import org.se0k.entity_ai.effect.EffectController;
import org.se0k.entity_ai.sound.SoundEffect;

import java.util.*;

import static org.se0k.entity_ai.Entity_AI.instance;
import static org.se0k.entity_ai.Entity_AI.plugin;

public class EntityController implements EntityControl {

    static Map<UUID, Zombie> spawnEntity = new HashMap<>();
    public static Map<UUID, Entity> targetEntity = new HashMap<>();

    EffectControl effectControl = new EffectController();


    int spawnDelay = 0;

    @Override
    public void EntitySpawn(Player player) {

        World world = player.getWorld();

        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3));
        location.add(0, 2, 0);

        if (spawnDelay == 0) {

            if (spawnEntity.size() >= 4) {
                player.sendMessage("전부 소환했습니다");
                return;
            }

            world.spawn(location, Zombie.class, zombie -> {
                zombie.lookAt(player);
                zombie.setAI(false);
                zombie.setBaby(false);
                zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
                spawnEntity.put(zombie.getUniqueId(), zombie);
                effectControl.effectSet(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    zombie.setAI(true);
                }, 20 * 2);
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    zombie.setAI(false);
                }, 20 * 3);

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
    public void EntityMove(Player player, Location location) {

        if (spawnEntity.isEmpty()) return;

        for (Zombie zombie : spawnEntity.values()) {

            if (zombie.getLocation().distance(player.getLocation()) > 30) return;

            zombie.setAI(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.getPathfinder().moveTo(location, 2);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (zombie.getLocation().distance(location) <= 2) {
                            zombie.setAI(false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(instance, 0L, 20L);
            }, 10);
        }
        player.sendMessage("이동");
    }

    @Override
    public void EntityAttack(Player player, Entity entity) {
        for (Zombie zombie : spawnEntity.values()) {
            zombie.setAI(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.setTarget((LivingEntity) entity);
            }, 5);
        }
        player.sendMessage("공격");
    }

    @Override
    public void EntitySurround(Player player, Entity entity) {
        int i = 0;

        Location entityLoc = entity.getLocation();

        List<Location> direction = Arrays.asList(
                entityLoc.clone().add(2.0, 0, 0),
                entityLoc.clone().add(-2.0, 0, 0),
                entityLoc.clone().add(0, 0, 2.0),
                entityLoc.clone().add(0, 0, -2.0)
        );

        for (Zombie zombie : spawnEntity.values()) {
            zombie.setAI(true);
            Location targetLoc = direction.get(i);

            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.getPathfinder().moveTo(targetLoc, 2);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (zombie.getLocation().distance(targetLoc) <= 2) {
                            zombie.setAI(false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(instance, 0L, 20L);

            }, 10);
            i++;
        }
        player.sendMessage("포위");
    }
}
