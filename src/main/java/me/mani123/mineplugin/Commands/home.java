package me.mani123.mineplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class home implements CommandExecutor {

    sethome sethome = new sethome();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location location = ((Player)sender).getLocation();

                player.sendMessage(ChatColor.GOLD + "You teleported to home");
                player.teleport(location.set(sethome.x, sethome.y, sethome.z + 2));
            } else {
                System.out.println(ChatColor.RED + "This command work only in game!");
            }
        return false;
    }
}
