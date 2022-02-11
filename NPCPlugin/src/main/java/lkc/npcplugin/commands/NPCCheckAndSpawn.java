package lkc.npcplugin.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static org.bukkit.Bukkit.getServer;

public class NPCCheckAndSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()){
            sender.sendMessage("Only Operators can use that command");
            return true;
        }
        Iterator<NPC> npcs = CitizensAPI.getNPCRegistry().iterator();
        NPCChecker(npcs, "qwer");
        return true;
    }

    public boolean NPCChecker (Iterator<NPC> npcs, String name){
        boolean flag = false;
        while(npcs.hasNext()) {
            if (npcs.next().getName().equals(name)) {
                flag = true;
                break;
            }
        }
        if(!flag){
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER,name);
            npc.spawn(new Location(getServer().getWorld("world"), -36, 65, 160));
            npc.getDefaultSpeechController().speak(new SpeechContext((String)"hi"));

            return false;
        }
            return true;
    }
}
