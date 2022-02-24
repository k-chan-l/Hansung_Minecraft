package lkc.holographic.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

import static org.bukkit.Bukkit.getServer;

public class playerJoinEventLIstener implements Listener {

    private static lungrowValueUpdateEventListener lVUEL;
    private static Plugin plugin;

    public playerJoinEventLIstener(lungrowValueUpdateEventListener LVUEL, Plugin p){
        plugin = p;
        lVUEL = LVUEL;
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event){
        getServer().getPluginManager().registerEvents(lVUEL,plugin);
    }
}
