package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class FeedCommand implements TabExecutor {
    private Main main;

    public FeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        String no_permission_message = main.getConfig().getString("no-permission-message");
        if (sender instanceof Player) {
            if (sender.hasPermission("rtm.feed")) {
                Player player = (Player) sender;
                try {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (sender.hasPermission("rtm.feed.other")) {
                        if (target != null) {
                            target.setFoodLevel(20);
                            target.setSaturation(10);
                            target.setExhaustion(0F);
                            String feed_other_message = main.getConfig().getString("feed-other-message");
                            String feed_message = main.getConfig().getString("feed-message");
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + feed_other_message, Placeholder.component("player", target.displayName())));
                            target.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + feed_message));
                        } else {
                            String invalid_player_message = main.getConfig().getString("invalid-player-message");
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                        }
                    } else {
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("rtm.feed.other"))));
                    }
                } catch (Exception e) {
                    player.setFoodLevel(20);
                    player.setSaturation(10);
                    player.setExhaustion(0F);
                    String feed_message = main.getConfig().getString("feed-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + feed_message));
                }
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("rtm.feed"))));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
