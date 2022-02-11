package lkc.npcplugin.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Iterator;

import static org.bukkit.Bukkit.getServer;

public class NPCCheck {
    Iterator<NPC> npcs;

    public NPCCheck() {
        npcs = CitizensAPI.getNPCRegistry().iterator();
    }

    public boolean NPCCheckerByName (String name){
        boolean flag = false;
        while(npcs.hasNext()) {
            if (npcs.next().getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
