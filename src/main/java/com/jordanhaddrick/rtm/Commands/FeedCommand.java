package com.jordanhaddrick.rtm.Commands;

import java.util.List;

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
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String prefix_message = main.getConfig().getString("prefix-message");
            try {
                Player target = Bukkit.getPlayer(args[0]);
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
            } catch (Exception e) {
                player.setFoodLevel(20);
                player.setSaturation(10);
                player.setExhaustion(0F);
                String feed_message = main.getConfig().getString("feed-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + feed_message));
            }
        }
        return true;
    }
}
