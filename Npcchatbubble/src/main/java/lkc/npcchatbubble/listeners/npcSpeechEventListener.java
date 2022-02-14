package lkc.npcchatbubble.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lkc.lungrow.events.npcSpeakEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class npcSpeechEventListener implements Listener {

    Plugin plugin;
    private boolean flag = false;
    private String message = "";

    public npcSpeechEventListener(Plugin p){
        plugin = p;
    }

    @EventHandler
    public void onNPCSpeech(npcSpeakEvent e)
    {
        if(message.equals(e.getText()) && flag)
            return;
        flag = true;
        Entity npc = e.getNpc();
        message = e.getText();
        Location holo = new Location(getServer().getWorld("world"), npc.getLocation().getX(), npc.getLocation().getY()+2.7 , npc.getLocation().getZ());
        Hologram hologram = HologramsAPI.createHologram(plugin, holo);
        hologram.size();
        hologram.appendTextLine(message);
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            int i = 0;
            @Override
            public void run() {
                hologram.teleport(new Location(getServer().getWorld("world"), npc.getLocation().getX(), npc.getLocation().getY()+2.7 , npc.getLocation().getZ()));
                i += 1;
                if(i >= 20L*3)
                {
                    hologram.delete();
                    flag = false;
                    scheduler.cancelTasks(plugin);
                }
            }
        }, 0L, 1L);

    }
}
