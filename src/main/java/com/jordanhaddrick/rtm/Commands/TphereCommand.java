package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TphereCommand implements TabExecutor {
    private Main main;

    public TphereCommand(Main main) {
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
            if (sender.hasPermission("rtm.tphere")) {
                Player player = (Player) sender;
                try {
                    Player target = Bukkit.getPlayer(args[0]);
                    Location targetlocation = player.getLocation();
                    if (target != null) {
                        player.teleport(targetlocation);
                        String tp_message = main.getConfig().getString("tphere-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + tp_message, Placeholder.component("player", target.displayName())));
                    } else {
                        String invalid_player_message = main.getConfig().getString("invalid-player-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                    }
                } catch (Exception e) {
                    String tp_no_player_message = main.getConfig().getString("tphere-no-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + tp_no_player_message));
                }
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("rtm.tphere"))));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
