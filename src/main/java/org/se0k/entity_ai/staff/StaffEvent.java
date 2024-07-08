package org.se0k.entity_ai.staff;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.se0k.entity_ai.entity.EntityControl;
import org.se0k.entity_ai.entity.EntityController;

public class StaffEvent implements Listener {

    boolean isSneak = false;

    EntityControl entityControl = new EntityController();

    @EventHandler
    public void staffRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action action = event.getAction();

        if (player.getInventory().getItemInMainHand().getType() != Material.STICK) return;

        if (action.isRightClick()) {
            if (isSneak) entityControl.EntitySpawn(player);
            else entityControl.EntityMove(player);
        }

        if (action.isLeftClick()) {
            if (isSneak) {

            }
            else {

            }
        }
    }

    @EventHandler
    public void staffLeftClick() {

    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) {
        isSneak = event.isSneaking();
    }
}
