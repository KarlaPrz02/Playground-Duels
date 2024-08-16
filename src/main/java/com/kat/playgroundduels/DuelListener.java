package com.kat.playgroundduels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DuelListener implements Listener {

    private final DuelPlugin plugin;

    public DuelListener(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        if (plugin.getDuelManager().isInDuel(deadPlayer)) {
            plugin.getDuelManager().endDuel(deadPlayer);
        }
    }
}
