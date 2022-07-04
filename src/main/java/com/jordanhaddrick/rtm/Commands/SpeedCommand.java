package com.jordanhaddrick.rtm.Commands;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {
    private Main main;

    public SpeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                Integer speed = Integer.parseInt(args[0]);
                if (speed <= 10) {
                    String speed_message = main.getConfig().getString("speed-message");
                    Integer walkspeed = speed / 10;
                    player.setWalkSpeed(walkspeed);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + speed_message, Placeholder.component("speed", Component.text(speed))));
                } else {
                    String too_big_integer = main.getConfig().getString("too-big-integer");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + too_big_integer));
                }
            } catch (Exception e)  {
                String not_valid_integer = main.getConfig().getString("not-valid-integer");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + not_valid_integer));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
