package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GamemodeCreative implements TabExecutor {
    private Main main;

    public GamemodeCreative(Main main) {
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
            try {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (target.getPlayer().getGameMode() == GameMode.CREATIVE) {
                        String prefix_message = main.getConfig().getString("prefix-message");
                        String incorrect_gms_other_usage_message = main.getConfig().getString("incorrect-gms-other-usage-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_gms_other_usage_message, Placeholder.component("player", target.displayName())));
                    }
                    else {
                        target.setGameMode(GameMode.CREATIVE);
                        String prefix_message = main.getConfig().getString("prefix-message");
                        String gms_other = main.getConfig().getString("gms-other");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + gms_other, Placeholder.component("player", target.displayName())));
                    }
                }
                else {
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String invalid_player_message = main.getConfig().getString("invalid-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            }
            catch(Exception e) {
                if (player.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String incorrect_gms_usage_message = main.getConfig().getString("incorrect-gms-usage-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_gms_usage_message));
                }
                else {
                    player.setGameMode(GameMode.CREATIVE);
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String gms_message = main.getConfig().getString("gms-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + gms_message));
                }
            }
        }
        else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}