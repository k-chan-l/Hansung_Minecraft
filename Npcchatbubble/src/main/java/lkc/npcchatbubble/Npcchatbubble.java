package lkc.npcchatbubble;

import lkc.npcchatbubble.listeners.npcSpeechEventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Npcchatbubble extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        getServer().getPluginManager().registerEvents(new npcSpeechEventListener(this),this);
        getLogger().info("npcSpeechPlugin");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
