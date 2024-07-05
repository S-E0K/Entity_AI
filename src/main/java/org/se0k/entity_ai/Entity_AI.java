package org.se0k.entity_ai;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.se0k.entity_ai.commands.Commands;

public final class Entity_AI extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getCommandMap().register("mob", new Commands("mob"));




    }

    @Override
    public void onDisable() {

    }
}
