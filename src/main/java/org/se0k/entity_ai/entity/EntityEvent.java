package org.se0k.entity_ai.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

import static org.se0k.entity_ai.entity.EntityController.spawnEntity;

public class EntityEvent implements Listener {

    @EventHandler
    public void EntityDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        UUID entityUUID = entity.getUniqueId();

        if (!spawnEntity.containsKey(entityUUID)) return;

        spawnEntity.remove(entityUUID);

        if (spawnEntity.isEmpty()) Bukkit.getConsoleSender().sendMessage("다 죽었음");
    }

}
