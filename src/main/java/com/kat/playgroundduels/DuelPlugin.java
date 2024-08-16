package com.kat.playgroundduels;

import org.bukkit.plugin.java.JavaPlugin;

public class DuelPlugin extends JavaPlugin {

    private DuelManager duelManager;

    @Override
    public void onEnable() {
        this.duelManager = new DuelManager(this);
        getCommand("duel").setExecutor(new DuelCommand(this));
        getServer().getPluginManager().registerEvents(new DuelListener(this), this);
        getLogger().info("DuelPlugin enable.");
    }

    @Override
    public void onDisable() {
        getLogger().info("DuelPlugin disable.");
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
}
