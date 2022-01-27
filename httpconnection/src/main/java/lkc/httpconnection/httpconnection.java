package lkc.httpconnection;

import lkc.httpconnection.commands.commandGetHttp;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class httpconnection extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("gethttp").setExecutor(new commandGetHttp());
        getServer().getLogger().info(ChatColor.AQUA + "[mqtt plugin]" + ChatColor.GREEN + "http plugin is working");

    }

    @Override
    public void onDisable() {
        getServer().getLogger().info(ChatColor.AQUA + "[mqtt plugin]" + ChatColor.GREEN + "http plugin is working");
        // Plugin shutdown logic
    }
}
