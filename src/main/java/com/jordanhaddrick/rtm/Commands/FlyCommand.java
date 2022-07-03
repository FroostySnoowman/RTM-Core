package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class FlyCommand implements TabExecutor {
    private Main main;

    public FlyCommand(Main main) {
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
                    if (target.getAllowFlight()) {
                        target.setAllowFlight(false);
                        String fly_disabled_other_message = main.getConfig().getString("fly-disabled-other-message");
                        String fly_disabled_message = main.getConfig().getString("fly-disabled-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_disabled_other_message, Placeholder.component("player", target.displayName())));
                        target.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_disabled_message));
                    }
                    else {
                        target.setAllowFlight(true);
                        String fly_enabled_other_message = main.getConfig().getString("fly-enabled-other-message");
                        String fly_enabled_message = main.getConfig().getString("fly-enabled-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_enabled_other_message, Placeholder.component("player", target.displayName())));
                        target.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_enabled_message));
                    }
                } else {
                    String invalid_player_message = main.getConfig().getString("invalid-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            } catch (Exception e) {
                if (player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    String fly_disabled_message = main.getConfig().getString("fly-disabled-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_disabled_message));
                } else {
                    player.setAllowFlight(true);
                    String fly_enabled_message = main.getConfig().getString("fly-enabled-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + fly_enabled_message));
                }
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
