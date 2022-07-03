package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpCommand implements TabExecutor {
    private Main main;

    public TpCommand(Main main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                Player target = Bukkit.getPlayer(args[0]);
                Location targetlocation = target.getLocation();
                if (target != null) {

                    player.teleport(targetlocation);
                    String tp_message = main.getConfig().getString("tp-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + tp_message, Placeholder.component("player", target.displayName())));
                } else {
                    String invalid_player_message = main.getConfig().getString("invalid-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            } catch (Exception e) {
                String tp_no_player_message = main.getConfig().getString("tp-no-player-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + tp_no_player_message));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
