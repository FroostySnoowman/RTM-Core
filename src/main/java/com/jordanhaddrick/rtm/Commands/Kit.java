package com.jordanhaddrick.rtm.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class Kit implements CommandExecutor {
    private static Main main;

    public Kit(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {

            if(args[0].equalsIgnoreCase("test")) {

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    ItemStack diamond = new ItemStack(Material.DIAMOND);

                    ItemStack bricks = new ItemStack(Material.BRICK);

                    bricks.setAmount(20);

                    player.getInventory().addItem(bricks, diamond);

                    String prefix_message = main.getConfig().getString("prefix-message");
                    String kit_message = main.getConfig().getString("kit-message");

                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + kit_message));

                    return true;

                } else {
                    String prefix_message = main.getConfig().getString("prefix-message");
                    String non_player_message = main.getConfig().getString("non-player-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + non_player_message));
                }

                return true;

            } else {
                String prefix_message = main.getConfig().getString("prefix-message");
                String incorrect_kit_usage_message = main.getConfig().getString("incorrect-kit-usage-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_kit_usage_message));
            }

        } else {
                String prefix_message = main.getConfig().getString("prefix-message");
                String available_kit_message = main.getConfig().getString("available-kit-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + available_kit_message));
            }

            return true;
    }

}
