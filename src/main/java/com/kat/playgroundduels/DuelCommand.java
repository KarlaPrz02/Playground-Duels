package com.kat.playgroundduels;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final DuelPlugin plugin;

    public DuelCommand(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Use: /duel <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("The player is not online.");
            return true;
        }

        if (plugin.getDuelManager().isInDuel(player) || plugin.getDuelManager().isInDuel(target)) {
            player.sendMessage("You are already on a duel or the player is on a duel.");
            return true;
        }

        if (plugin.getDuelManager().isInDuel(target)) {
            player.sendMessage("The player is already on a duel.");
            return true;
        }

        if (plugin.getDuelManager().isInDuel(player)) {
            plugin.getDuelManager().acceptDuel(player);
        } else {
            plugin.getDuelManager().sendDuelRequest(player, target);
        }

        return true;
    }
}