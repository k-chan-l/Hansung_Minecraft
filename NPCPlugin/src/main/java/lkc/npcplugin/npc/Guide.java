package lkc.npcplugin.npc;

import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.schdulings.waitfiveseconds;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class Guide {
    NPC npc;
    Player player;
    NPCPlugin plugin;
    String npcName;

    public Guide (Player player, String npcName, NPCPlugin plugin)
    {
        nullChecker(player);
        nullChecker(npcName);
        nullChecker(plugin);
        this.player = player;
        this.plugin = plugin;
        this.npcName = npcName;
    }

    public void init(){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.spawn(this.player.getLocation());
        npc.getNavigator().getLocalParameters().range(255f);
        npc.getDefaultSpeechController().speak(new SpeechContext((String)"hi"));
        waitfiveseconds wfs = new waitfiveseconds(plugin, player, npc);
        wfs.Scheduling();
    }

    private boolean isnull(Object obj){
        return obj == null;
    }

    private void nullChecker(Object obj){
        if(isnull(obj)) {
            getServer().getConsoleSender().sendMessage("Guide->"+ obj.toString() +" is null");
            return;
        }
    }

}
