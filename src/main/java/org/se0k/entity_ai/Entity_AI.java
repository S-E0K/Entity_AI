package org.se0k.entity_ai;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.se0k.entity_ai.commands.Commands;
import org.se0k.entity_ai.entity.EntityEvent;
import org.se0k.entity_ai.staff.StaffEvent;

public final class Entity_AI extends JavaPlugin {

    public static Entity_AI instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getCommandMap().register("magic", new Commands("staff"));

        getServer().getPluginManager().registerEvents(new StaffEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityEvent(), this);
    }

    @Override
    public void onDisable() {

    }
}
