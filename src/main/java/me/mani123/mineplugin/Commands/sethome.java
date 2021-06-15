package me.mani123.mineplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class sethome implements CommandExecutor {
    int x;
    int y;
    int z;
    float Yaw;
    float Pitch;
    World world;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location location = ((Player)sender).getLocation();
                x = (int) location.getX();
                y = (int) location.getY();
                z = (int) location.getZ();
                Yaw = location.getYaw();
                Pitch = location.getPitch();
                world = location.getWorld();
                player.sendMessage(ChatColor.GOLD + "You set location the home " + ChatColor.AQUA + "(" + x + ","
                        + y + "," + z + ")");
            } else {
                System.out.println(ChatColor.RED + "This command work only in game!");
            }
        return false;
    }
}
