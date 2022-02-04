package lkc.httpconnection;

import lkc.httpconnection.commands.commandGetHttp;
import lkc.httpconnection.files.ConfigureFile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class httpconnection extends JavaPlugin {
    private static httpconnection httpCon;

    @Override
    public void onEnable() {
        // Plugin startup logic
        httpCon = this;
        getCommand("gethttp").setExecutor(new commandGetHttp(httpCon));
        ConfigureFile.setup();
        getServer().getLogger().info(ChatColor.AQUA + "[mqtt plugin]" + ChatColor.GREEN + "http plugin is working");

    }

    @Override
    public void onDisable() {
        getServer().getLogger().info(ChatColor.AQUA + "[mqtt plugin]" + ChatColor.GREEN + "http plugin is working");
        // Plugin shutdown logic
    }

    public static httpconnection getHttpCon() {
        return httpCon;
    }
}

