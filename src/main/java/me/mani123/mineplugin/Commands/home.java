package me.mani123.mineplugin.Commands;

import me.mani123.mineplugin.MinePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class home implements CommandExecutor {

    MinePlugin plugin;

    public home(MinePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Location location = ((Player) sender).getLocation();
            Player player = (Player) sender;
            //World world = ((Player)sender).getWorld();
            if (plugin.getConfig().getBoolean("Enable")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
                    if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName())) {
                        player.sendMessage(ChatColor.YELLOW + "Your home location set to:" + ChatColor.AQUA
                                + Math.round(location.getX()) + "," + Math.round(location.getY()) +
                                "," + Math.round(location.getZ()));
                        saveToConfig(location, player);
                    } else {
                        saveToConfig(location, player);
                        player.sendMessage(ChatColor.GOLD + "You set location the home " + ChatColor.AQUA +
                                "(" + Math.round(location.getX()) + "," + Math.round(location.getY()) +
                                "," + Math.round(location.getZ()) + ")");
                    }
                } else if (args.length == 1 && args[0].equalsIgnoreCase("tp")) {
                    if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName())) {
                        Location go_home = new Location(player.getWorld()
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + ".x")
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + ".y")
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + ".z"));
                        player.teleport(go_home);
                        player.sendMessage(ChatColor.GREEN + "You teleported to home location");
                    }
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                player.sendMessage(ChatColor.RED + "Plugin reloaded");
            } else {
                notCommand(player);
            }
        } else {
            System.out.println(ChatColor.RED + "This command work only in game!");
        }
        return true;
    }

    private void notCommand(Player player) {
        player.sendMessage(ChatColor.RED + "You not have home, please enter " + ChatColor.GREEN
                + "'/home <set, tp>'");
    }

    private void saveToConfig(Location location, Player player) {
        plugin.getConfig().createSection("savedLocations." + player.getName());
        plugin.getConfig().set("savedLocations." + player.getName() + ".x", location.getX());
        plugin.getConfig().set("savedLocations." + player.getName() + ".y", location.getY());
        plugin.getConfig().set("savedLocations." + player.getName() + ".z", location.getZ());
        plugin.getConfig().set("savedLocations." + player.getName() + ".pitch", location.getPitch());
        plugin.getConfig().set("savedLocations." + player.getName() + ".yaw", location.getYaw());
        plugin.getConfig().set("savedLocations." + player.getName() + ".world", player.getWorld());
        plugin.saveConfig();
    }
}
