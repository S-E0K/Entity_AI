package org.se0k.entity_ai.entity;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface EntityControl {
    void EntitySpawn(Player player);
    void EntityMove(Player player);
    void EntityAttack(Player player);
    void EntitySurround(Player player);
}
