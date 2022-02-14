package lkc.npcplugin.schdulings;

import lkc.lungrow.events.npcSpeakEvent;
import lkc.npcplugin.NPCPlugin;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.bukkit.Bukkit.getServer;

public class waitfiveseconds {
    private final BukkitScheduler scheduler;
    private final NPCPlugin plugin;
    private final Player player;
    private final NPC npc;
    private final Location loc;
    private AtomicBoolean flag;

    public waitfiveseconds(NPCPlugin plugin, Player player, NPC npc, Location location){
        this.plugin = plugin;
        this.player = player;
        this.npc = npc;
        scheduler = Bukkit.getScheduler();
        loc = location;
        flag = new AtomicBoolean(false);
    }

    public void Scheduling(){
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                npcSpeakEvent event = new npcSpeakEvent(npc.getEntity(), "안녕");
                getServer().getPluginManager().callEvent(event);
                repeatDistanceCheck rdc = new repeatDistanceCheck(plugin, player, npc, loc, flag);
                rdc.Scheduling();

            }
        }, 20L * 5); //20 Tick (1 Second) * 5 delay before run() is called
    }
}
