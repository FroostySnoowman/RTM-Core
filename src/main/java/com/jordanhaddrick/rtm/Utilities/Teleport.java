package com.jordanhaddrick.rtm.Utilities;

import com.jordanhaddrick.rtm.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class Teleport {

    private static Main main;

    public Teleport(Main main) {
        this.main = main;
    }

    // List of unsafe blocks
    public static HashSet<Material> badBlocks = new HashSet<>();

    static {
        badBlocks.add(Material.LAVA);
        badBlocks.add(Material.FIRE);
        badBlocks.add(Material.CACTUS);
        badBlocks.add(Material.WATER);
    }

    public static Location generateLocation(Player player) {
        // Generate random location
        Random random = new Random();

        int x = 0;
        int z = 0;
        int y = 0;

        if(main.getConfig().getBoolean("world-border")) {
            x = random.nextInt(main.getConfig().getInt("border"));
            z = random.nextInt(main.getConfig().getInt("border"));
            y = 150;
        }
        else {
            x = random.nextInt(25000);
            z = random.nextInt(25000);
            y = 150;
        }

        Location randomLocation = new Location(player.getWorld(), x, y, z).toCenterLocation().toHighestLocation().add(0, 1, 0);

        return randomLocation;
    }

    public static Location findSafeLocation(Player player) {
        Location randomLocation = generateLocation(player);

        while(!isLocationSafe(randomLocation)) {
            // Keep looking for a safe location
            randomLocation = generateLocation(player);
        }

        return randomLocation;
    }

    public static boolean isLocationSafe(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        // Get instances of the blocks around where the player would spawn
        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return (!(badBlocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid()));
    }

}
