package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
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
            if (player.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                String prefix_message = main.getConfig().getString("prefix-message");
                String incorrect_gms_usage_message = main.getConfig().getString("incorrect-gms-usage-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_gms_usage_message));
            }
            else {
                player.setGameMode(GameMode.SURVIVAL);
                String prefix_message = main.getConfig().getString("prefix-message");
                String gms_message = main.getConfig().getString("gms-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + gms_message));
            }
        }
        return true;
    }
}