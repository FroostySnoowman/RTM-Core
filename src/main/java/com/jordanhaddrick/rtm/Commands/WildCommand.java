package com.jordanhaddrick.rtm.Commands;

import com.jordanhaddrick.rtm.Main;
import com.jordanhaddrick.rtm.Utilities.Teleport;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WildCommand implements CommandExecutor {
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private static Main main;

    public WildCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        String no_permission_message = main.getConfig().getString("no-permission-message");
        if(sender instanceof Player) {
            if (sender.hasPermission("rtm.wild")) {
                int cooldownTime = main.getConfig().getInt("cool-down-time");
                Player player = (Player) sender;

                if (cmd.getName().equalsIgnoreCase("wild")) {
                    // Check if player is in HashMap
                    if (cooldown.containsKey(player.getUniqueId())) {
                        long secondsLeft = ((cooldown.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                        String cool_down_message = main.getConfig().getString("cool-down-message").replace("{seconds-left}", String.valueOf(secondsLeft));

                        // Check if player is in cooling-off period
                        if (secondsLeft > 0) {
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + cool_down_message));
                        }
                        // Remove from hashmap, teleport player
                        else if (secondsLeft <= 0) {
                            cooldown.remove(player.getUniqueId());
                            return teleport(args, player);
                        }
                    } else {
                        // First time command usage, teleport player
                        return teleport(args, player);
                    }
                }
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("rtm.wild"))));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }

        return true;
    }

    private boolean teleport(String[] args, Player player) {
        // Add cool-down to player
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());

        // Check if the correct arguments are supplied
        if(args.length == 0) {
            if(player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")
                    || player.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")) {

                String prefix_message = main.getConfig().getString("prefix-message");
                String incorrect_world_message = main.getConfig().getString("incorrect-world-message");

                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_world_message));
            }
            else {

                String prefix_message = main.getConfig().getString("prefix-message");
                String queued_message = main.getConfig().getString("queued-teleport-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + queued_message));

                // Determine a safe location
                Location randomLocation = Teleport.findSafeLocation(player);

                // Teleport the player
                player.teleport(randomLocation);

                // Notify the player
                String random_location = main.getConfig().getString("random-location");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + random_location));
            }
        }
        else {
            String prefix_message = main.getConfig().getString("prefix-message");
            String incorrect_wild_usage_message = main.getConfig().getString("incorrect-wild-usage-message");
            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_wild_usage_message));
        }
        return true;
    }
}
