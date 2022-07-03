package com.jordanhaddrick.rtm.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import com.jordanhaddrick.rtm.Main;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

public class KitCommand implements TabExecutor {
    private static final String[] COMMANDS = {"kit1", "kit2", "kit3", "kit4"};
    private Main main;

    public KitCommand(Main main) {
        this.main = main;
    }

    //create a static array of values

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();
        //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
        //sort the list

        Collections.sort(completions);
        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        if (sender instanceof Player) {
            if (args.length >= 1) {

                if(args[0].equalsIgnoreCase("kit1")) {

                    Player player = (Player) sender;

                    ItemStack diamond = new ItemStack(Material.DIAMOND);

                    ItemStack bricks = new ItemStack(Material.BRICK);

                    bricks.setAmount(20);

                    player.getInventory().addItem(bricks, diamond);

                    String kit_message = main.getConfig().getString("kit-message");

                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + kit_message));

                } else {
                    String incorrect_kit_usage_message = main.getConfig().getString("incorrect-kit-usage-message");
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + incorrect_kit_usage_message));
                }

            } else {
                String available_kit_message = main.getConfig().getString("available-kit-message");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + available_kit_message));
            }


        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }

        return true;
    }

}

