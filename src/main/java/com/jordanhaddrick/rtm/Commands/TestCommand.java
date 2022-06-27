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

public class TestCommand implements TabCompleter {
    private static final String[] COMMANDS = { "minecraft", "spigot", "bukkit", "google" };
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
}
