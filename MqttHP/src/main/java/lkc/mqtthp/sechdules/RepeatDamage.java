package lkc.mqtthp.sechdules;

import lkc.mqtthp.HealthControl;
import lkc.mqtthp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class RepeatDamage {
    public final Player player;
    final Main plugin;
    final HealthControl Hc;
    public boolean flag = false;
    public RepeatDamage(Player player, Main plugin, HealthControl hc){
        this.player = player;
        this.plugin = plugin;
        Hc = hc;
    }
    public void Scheduling(){
        // With BukkitScheduler
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            if(flag){
                flag = false;
                player.setHealth(20);
            }
            else {
                player.sendMessage(ChatColor.GOLD + "5 seconds");
                Hc.Hit();
            }
        }, 20L * 10L /*<-- the initial delay */, 20L * 5L /*<-- the interval */);

    }
}
