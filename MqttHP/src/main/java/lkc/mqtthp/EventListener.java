package lkc.mqtthp;

import lkc.mqtthp.sechdules.RepeatDamage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to the Server! :)");
        final Main plugin = Main.getInstance();
        HealthControl HC = new HealthControl(player);
        RepeatDamage RD = new RepeatDamage(player, plugin, HC);
        RD.Scheduling();
        plugin.mqtt2.client.addRepeatDamage(RD);
    }

}
