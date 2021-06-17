package me.mani123.mineplugin.Commands;

import me.mani123.mineplugin.MinePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class home implements CommandExecutor, TabCompleter {

    MinePlugin plugin;

    public String defaultHome = "home";

    public home(MinePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && args.length != 0) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            World world = player.getWorld();
            if (args.length == 2 || args.length == 1 && args[0].equalsIgnoreCase("set")) {
                if (args.length == 1 && !plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + defaultHome) && args[0].equalsIgnoreCase("set")) {
                    saveToConfig(location, player, world, defaultHome);
                    player.sendMessage(ChatColor.GOLD + "You set location the home, by default name " + ChatColor.AQUA +
                            "(" + Math.round(location.getX()) + "," + Math.round(location.getY()) +
                            "," + Math.round(location.getZ()) + ")");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                    if (!plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + args[1])) {
                        player.sendMessage(ChatColor.YELLOW + "Your home location set to:" + ChatColor.AQUA
                                + Math.round(location.getX()) + "," + Math.round(location.getY()) +
                                "," + Math.round(location.getZ()));
                        saveToConfig(location, player, world, args[1]);
                    }
                }
            }
            if (args.length == 2 || args.length == 1 && args[0].equalsIgnoreCase("tp")) {
                if (args.length == 1 && plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + defaultHome) && args[0].equalsIgnoreCase("tp")) {
                    Location go_home = new Location(getServer().getWorld(Objects.requireNonNull(plugin.getConfig()
                            .getString("savedLocations." + player.getName() + "." + defaultHome + ".world")))
                            , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + defaultHome + ".x")
                            , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + defaultHome + ".y")
                            , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + defaultHome + ".z"),
                            (float) plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + defaultHome + ".yaw"),
                            (float) plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + defaultHome + ".pitch"));
                    player.teleport(go_home);
                    player.sendMessage(ChatColor.GREEN + "You teleported to home location by default name");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("tp") ) {
                    if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + args[1])) {
                        Location go_home_custom = new Location(getServer().getWorld(Objects.requireNonNull(plugin.getConfig()
                                .getString("savedLocations." + player.getName() + "." + args[1] + ".world")))
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + args[1] + ".x")
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + args[1] + ".y")
                                , plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + args[1] + ".z"),
                                (float) plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + args[1] + ".yaw"),
                                (float) plugin.getConfig().getDouble("savedLocations." + player.getName() + "." + args[1] + ".pitch"));
                        player.teleport(go_home_custom);
                        player.sendMessage(ChatColor.GREEN + "You teleported to home location");
                    } else if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + args[1])) {
                        player.sendMessage(ChatColor.RED + "House with that name " + args[1] + " does not exist");
                    }
                }
            }
            if (args.length == 2 || args.length == 1 && args[0].equalsIgnoreCase("del")) {
                if (args.length == 1 && plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + defaultHome) && args[0].equalsIgnoreCase("del")) {
                    plugin.getConfig().set("savedLocations." + player.getName() + "." + defaultHome, null);
                    plugin.saveConfig();
                    player.sendMessage(ChatColor.YELLOW + "You deleted home: " + defaultHome);
                }
                if (args.length == 2 && args[0].equalsIgnoreCase("del")) {
                    if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + args[1])) {
                        plugin.getConfig().set("savedLocations." + player.getName() + "." + args[1], null);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.YELLOW + "You deleted home: " + args[1]);
                    } else if (plugin.getConfig().isConfigurationSection("savedLocations." + player.getName() + "." + args[1])) {
                        player.sendMessage(ChatColor.RED + "House with that name " + args[1] + " does not exist");
                    }
                }
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!(sender instanceof ConsoleCommandSender) && args[0].equalsIgnoreCase("reload") ) {
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.DARK_GREEN + "Reloading the plugin via the game");
                } else if (sender instanceof ConsoleCommandSender && args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    System.out.println(ChatColor.DARK_GREEN + "Reloading the plugin via the console");
                }
            }
        } else if (sender instanceof ConsoleCommandSender && !args[0].equalsIgnoreCase("reload")) {
            System.out.println(ChatColor.RED + "This command work only in game!");
        } else {
            assert sender instanceof Player;
            Player player = (Player) sender;
            notCommand(player);
        }
        return true;
    }

    private void notCommand(Player player) {
        player.sendMessage(ChatColor.RED + "You not have home, please enter " + ChatColor.GREEN
                + "'/home <set, tp, del, reload>'");
    }

    private void saveToConfig(Location location, Player player, World world, String homeName) {
        plugin.getConfig().createSection("savedLocations." + player.getName() + "." + homeName);
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".x", location.getX());
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".y", location.getY());
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".z", location.getZ());
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".pitch", location.getPitch());
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".yaw", location.getYaw());
        plugin.getConfig().set("savedLocations." + player.getName() + "." + homeName + ".world", world.getName());
        plugin.saveConfig();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("tp");
            arguments.add("set");
            arguments.add("reload");
            arguments.add("del");
            return arguments;
        }
        return null;
    }
}
