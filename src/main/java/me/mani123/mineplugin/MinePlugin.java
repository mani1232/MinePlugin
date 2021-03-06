package me.mani123.mineplugin;

import me.mani123.mineplugin.Commands.home;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MinePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("home")).setExecutor(new home(this));
        //Objects.requireNonNull(getCommand("home")).setExecutor(new home(this));
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "MinePlugin is enabled");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "MinePlugin is disabled"); // hard )
        // System.out.println(ChatColor.RED + "MinePlugin is disabled"); easy
    }

}
