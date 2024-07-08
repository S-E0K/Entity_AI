package org.se0k.entity_ai.staff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.se0k.entity_ai.entity.EntityControl;
import org.se0k.entity_ai.entity.EntityController;

import java.util.Objects;

import static org.se0k.entity_ai.entity.EntityController.targetEntity;

public class StaffEvent implements Listener {

    boolean isSneak = false;

    EntityControl entityControl = new EntityController();

    @EventHandler
    public void staffRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action action = event.getAction();

        Entity entity = player.getTargetEntity(30);

        if (player.getInventory().getItemInMainHand().getType() != Material.STICK) return;

        if (action.isRightClick()) {
            if (isSneak) entityControl.EntitySpawn(player);
            else {

                Location location;
                if (action == Action.RIGHT_CLICK_BLOCK) {
                    location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                }
                else {
                    location = player.getLocation().add(player.getLocation().getDirection().multiply(10));
                }
                entityControl.EntityMove(player, location);
            }
        }

        if (action.isLeftClick()) {
            if (isSneak) {
                entityControl.EntitySurround(player);
            }
            else {
                if (!targetEntity.isEmpty()) {
                    player.sendMessage("아직 타겟이 살아있습니다");
                    return;
                }
                entityControl.EntityAttack(player, entity);
            }
        }
    }

    @EventHandler
    public void staffLeftClick(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) return;

        Entity entity = event.getEntity();

        if (isSneak) {
            entityControl.EntitySurround(player);
        }
        else {
            if (!targetEntity.isEmpty()) {
                player.sendMessage("아직 타겟이 살아있습니다");
                return;
            }
            targetEntity.put(entity.getUniqueId(), entity);
            entityControl.EntityAttack(player, entity);
        }
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) {
        isSneak = event.isSneaking();
    }
}
