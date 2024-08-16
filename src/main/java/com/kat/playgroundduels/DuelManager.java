package com.kat.playgroundduels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DuelManager {

    private final DuelPlugin plugin;
    private final HashMap<UUID, UUID> duelRequests = new HashMap<>();
    private final HashMap<UUID, UUID> ongoingDuels = new HashMap<>();

    public DuelManager(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    public void sendDuelRequest(Player sender, Player receiver) {
        duelRequests.put(receiver.getUniqueId(), sender.getUniqueId());
        sender.sendMessage("You send a duel request to " + receiver.getName());
        receiver.sendMessage(sender.getName() + " challenged you to a duel. Use /duel " + sender.getName() + " for accept.");
    }

    public void acceptDuel(Player receiver) {
        UUID senderUUID = duelRequests.remove(receiver.getUniqueId());
        if (senderUUID != null) {
            Player sender = Bukkit.getPlayer(senderUUID);
            if (sender != null) {
                ongoingDuels.put(sender.getUniqueId(), receiver.getUniqueId());
                ongoingDuels.put(receiver.getUniqueId(), sender.getUniqueId());
                startDuel(sender, receiver);
            } else {
                receiver.sendMessage("The player is not online.");
            }
        } else {
            receiver.sendMessage("You don`t have pending duel requests.");
        }
    }

    private void startDuel(Player player1, Player player2) {
        player1.sendMessage("The duel with " + player2.getName() + " has started!");
        player2.sendMessage("The duel with " + player1.getName() + " has started!");

        // Teleport players to a duel arena
        // Arena coords here
        player1.teleport(player1.getWorld().getSpawnLocation());
        player2.teleport(player1.getWorld().getSpawnLocation());

        // Pennding to add more logic to the match
    }

    public void endDuel(Player loser) {
        UUID winnerUUID = ongoingDuels.remove(loser.getUniqueId());
        if (winnerUUID != null) {
            Player winner = Bukkit.getPlayer(winnerUUID);
            if (winner != null) {
                ongoingDuels.remove(winner.getUniqueId());
                winner.sendMessage("You won the duel against " + loser.getName() + "!");
                loser.sendMessage("You lost the duel against " + winner.getName() + "!");
            }
        }
    }

    public boolean isInDuel(Player player) {
        return ongoingDuels.containsKey(player.getUniqueId());
    }
}