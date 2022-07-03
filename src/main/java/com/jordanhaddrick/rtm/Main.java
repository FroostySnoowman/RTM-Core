package com.jordanhaddrick.rtm;

import com.jordanhaddrick.rtm.Commands.*;
import com.jordanhaddrick.rtm.Utilities.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage("RaidTheMine-Core loaded.");

        // Commands

        getCommand("feed").setExecutor(new FeedCommand(this));

        getCommand("fly").setExecutor(new FlyCommand(this));

        getCommand("gmsp").setExecutor(new GamemodeSpectator(this));
        getCommand("gmc").setExecutor(new GamemodeCreative(this));
        getCommand("gms").setExecutor(new GamemodeSurvival(this));

        getCommand("god").setExecutor(new GodCommand(this));

        getCommand("heal").setExecutor(new HealCommand(this));

        getCommand("kit").setExecutor(new KitCommand(this));

        getCommand("msg").setExecutor(new MsgCommand(this));

        getCommand("wild").setExecutor(new WildCommand(this));

        // Listeners

        // Utilities
        Teleport teleUtil = new Teleport(this);

        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}
