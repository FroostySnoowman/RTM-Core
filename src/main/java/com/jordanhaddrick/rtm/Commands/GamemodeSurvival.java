package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GamemodeSurvival implements TabExecutor {
    private Main main;

    public GamemodeSurvival(Main main) {
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
            String type = "survival";
            try {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (target.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                        String prefix_message = main.getConfig().getString("prefix-message");
                        String incorrect_gamemode_other_usage_message = main.getConfig().getString("incorrect-gamemode-other-usage-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_gamemode_other_usage_message, Placeholder.component("player", target.displayName()), Placeholder.component("type", Component.text(type))));
                    }
                    else {
                        target.setGameMode(GameMode.SURVIVAL);
                        String prefix_message = main.getConfig().getString("prefix-message");
                        String gamemode_other = main.getConfig().getString("gamemode-other");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + gamemode_other, Placeholder.component("player", target.displayName()), Placeholder.component("type", Component.text(type))));
                    }
                }
                else {
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String invalid_player_message = main.getConfig().getString("invalid-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            }
            catch(Exception e) {
                if (player.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String incorrect_gamemode_usage_message = main.getConfig().getString("incorrect-gamemode-usage-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_gamemode_usage_message, Placeholder.component("type", Component.text(type))));
                }
                else {
                    player.setGameMode(GameMode.SURVIVAL);
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String gms_message = main.getConfig().getString("gamemode-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + gms_message, Placeholder.component("type", Component.text(type))));
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