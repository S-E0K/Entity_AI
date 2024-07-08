package org.se0k.entity_ai.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface EntityControl {
    void EntitySpawn(Player player);
    void EntityMove(Player player, Location location);
    void EntityAttack(Player player, Entity entity);
    void EntitySurround(Player player);
}
