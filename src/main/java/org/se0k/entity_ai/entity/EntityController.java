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
                SoundEffect soundEffect = new SoundEffect();
                soundEffect.soundEffect(player, location);
                zombie.setShouldBurnInDay(false);
                zombie.setInvisible(true);
                zombie.lookAt(player);
                zombie.setAI(false);
                zombie.setBaby(false);
                spawnEntity.put(zombie.getUniqueId(), zombie);
                effectControl.effectSet(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
                    zombie.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
                    zombie.setInvisible(false);
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
        player.sendMessage("이동");
        for (Zombie zombie : spawnEntity.values()) {

            if (zombie.getLocation().distance(player.getLocation()) > 30) return;

            zombie.setAI(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.setTarget(null);
                zombie.getPathfinder().moveTo(location, 1.5);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (zombie.getLocation().distance(location) < 3) {
                            zombie.setAI(false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(instance, 20L, 20L);
            }, 20);
        }

    }

    @Override
    public void EntityAttack(Player player, Entity entity) {
        player.sendMessage("공격");
        for (Zombie zombie : spawnEntity.values()) {
            zombie.setAI(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                zombie.setTarget((LivingEntity) entity);
            }, 5);
        }

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
                zombie.setTarget(null);
                zombie.getPathfinder().moveTo(targetLoc, 1.5);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (zombie.getLocation().distance(targetLoc) < 1.5) {

                            zombie.setAI(false);
                            player.sendMessage("ㅁㄴㅇㄹ");
                            this.cancel();

                        }
                    }
                }.runTaskTimer(instance, 20L, 20L);

            }, 20);
            i++;
        }
        player.sendMessage("포위");
    }
}
