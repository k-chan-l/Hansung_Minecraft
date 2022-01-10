package lkc.npcplugin.events;

import lkc.npcplugin.traits.npctraits;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

import static org.bukkit.Bukkit.getServer;

public class PlayerEvent implements Listener {
    static SpeechContext sp;
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "fullwall");
        npc.spawn(player.getLocation());
//        npctraits asdf = new npctraits();
//        asdf.add("Hello Test");
//        npc.addTrait(asdf);

//        npc.getDefaultSpeechController().speak(new SpeechContext((String)"hi", player));
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "npc name : " + CitizensAPI.getNPCRegistry().getName());
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "key : " + player.getLocation().getWorld().getName());
    }
}
