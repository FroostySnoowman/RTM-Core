package com.jordanhaddrick.rtm;

import com.jordanhaddrick.rtm.Commands.KitCommand;
import com.jordanhaddrick.rtm.Commands.Wild;
import com.jordanhaddrick.rtm.Utilities.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage("RaidTheMine-Core loaded.");

        // Commands
        getCommand("wild").setExecutor(new Wild(this));

        getCommand("kit").setExecutor(new KitCommand(this));

        // Listeners

        // Utilities
        Teleport teleUtil = new Teleport(this);

        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}
