package lkc.npcplugin.events;

import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.npc.Guide;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.bukkit.Bukkit.getServer;

public class PlayerEvent implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        NPCPlugin plugin = NPCPlugin.getInstance();
        Player player = event.getPlayer();
        Guide guide = new Guide(player, "fullwall", plugin);
        guide.init();
    }
}
