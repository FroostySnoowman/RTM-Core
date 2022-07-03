package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MsgCommand implements TabExecutor {
    private Main main;

    public MsgCommand(Main main) {
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
                    try {
                        String message = args[1];
                        String msg_sent_message = main.getConfig().getString("msg-sent-message");
                        String msg_received_message = main.getConfig().getString("msg-received-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(msg_sent_message, Placeholder.component("sender", player.displayName()), Placeholder.component("message", Component.text(message))));
                        target.sendMessage(MiniMessage.miniMessage().deserialize(msg_received_message, Placeholder.component("sender", player.displayName()), Placeholder.component("message", Component.text(message))));
                    } catch (Exception e) {
                        String msg_sent_no_content_message = main.getConfig().getString("msg-sent-no-content-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + msg_sent_no_content_message));
                    }
                } else {
                    String invalid_player_message = main.getConfig().getString("invalid-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            } catch (Exception e) {
                String msg_sent_no_player_message = main.getConfig().getString("msg-sent-no-player-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + msg_sent_no_player_message));
            }
        }
        return true;
    }
}
