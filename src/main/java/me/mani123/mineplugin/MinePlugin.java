package me.mani123.mineplugin;

import me.mani123.mineplugin.Commands.home;
import me.mani123.mineplugin.Commands.sethome;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinePlugin extends JavaPlugin{

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "MinePlugin is enabled");
        getCommand("home").setExecutor(new home());
        getCommand("sethome").setExecutor(new sethome());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "MinePlugin is disabled"); // hard )
        // System.out.println(ChatColor.RED + "MinePlugin is disabled"); easy
    }

}
