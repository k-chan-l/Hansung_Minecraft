package lkc.npcplugin.schdulings;

import lkc.npcplugin.events.npcSpeakEvent;
import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.npc.NPCCheck;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static lkc.npcplugin.calculatings.CalculateDistance.isDistanceOverNum;
import static org.bukkit.Bukkit.getServer;

public class repeatDistanceCheck {
    private BukkitScheduler scheduler;
    private NPCPlugin plugin;
    private Player player;
    private NPC npc;
    private Location loc;
    private AtomicBoolean flag;
    private String name;

    public repeatDistanceCheck(NPCPlugin plugin, Player player, NPC npc, Location loc, AtomicBoolean flag){
        scheduler = Bukkit.getScheduler();
        this.plugin = plugin;
        this.player = player;
        this.npc = npc;
        this.loc = loc;
        this.flag = flag;
        this.name = npc.getName();

    }

    public void Scheduling(){
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                NPCChecker();
                PlayerChecker();
                if (!npc.getNavigator().isNavigating()) {//움직이지 않을 경우
                    whileNPCStay();
                }
                else {// 움직일 경우
                    whileNPCMove();
                }
            }
        }, 20L * 0 /*<-- the initial delay */, 20L * 1 /*<-- the interval */);

    }

    private void whileNPCStay(){
        if(NPCChecker()){
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The NPC is not exist");
                scheduler.cancelTasks(plugin);
                return;
            }
        if(isDistanceOverNum(npc.getEntity().getLocation(), player.getLocation(), 7)) {//움직이지 않는데 거리가 7이상일 경우
            flag.set(false);
        }
        else{//움직이지 않는데 거리가 10미만일경우
            NPCChecker();
            if (isDistanceOverNum(npc.getEntity().getLocation(), loc, 5)) {//움직이지 않고 거리가 7미만이면서 목표와의 거리가 5이상 차이나는 경우
                whileNPCStayAndFarawayGoal();
            } else {//움직이지 않고 거리가 10미만이면서 목표와의 거리가 5미만일 경우
                whileNPCStayAndNearbyGoal();
            }
        }
    }

    private void whileNPCStayAndFarawayGoal(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "NPC move again");
        if(flag.get()) {
            whileNPCStayAndFarawayGoalFlagTrue();
        }
        else {
            whileNPCStayAndFarawayGoalFlagFalse();
        }
    }

    private void whileNPCStayAndFarawayGoalFlagTrue(){
        Location temp = npc.getEntity().getLocation();
        temp.setY(temp.getY() + 0.5);
        npc.despawn();
        npc.spawn(temp);
        flag.set(false);
        pathto(npc, loc);
    }

    private void whileNPCStayAndFarawayGoalFlagFalse(){
        pathto(npc, loc);
        flag.set(true);
    }

    private void whileNPCStayAndNearbyGoal(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Exit npc move");
        flag.set(false);
        scheduler.cancelTasks(plugin);
    }


    private void whileNPCMove(){
        if(isDistanceOverNum(npc.getEntity().getLocation(), player.getLocation(), 10)) {//움직이면서 캐릭터와 거리가 10이상일경우
            npc.getNavigator().cancelNavigation();
            npc.faceLocation(player.getLocation());
            npcSpeakEvent event = new npcSpeakEvent(npc.getEntity(), "얼른 이리로와");
            getServer().getPluginManager().callEvent(event);
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Stop and Wait");
        }
        else
            flag.set(false);
    }


    public static void pathto(NPC npc, Location destiantion) {
        Location loc = npc.getStoredLocation();
        loc.setX(destiantion.getX());
        loc.setY(destiantion.getY());
        loc.setZ(destiantion.getZ());
        npc.getNavigator().setTarget(loc);
    }

    private boolean NPCChecker(){
        NPCCheck npcCheck = new NPCCheck();
        return !npcCheck.NPCCheckerByName(name);
    }

    private void PlayerChecker(){
        boolean PCflag = false;
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for(int i =0; i < list.size(); i ++){
            if(list.get(i) == player) {
                PCflag = true;
                break;
            }
        }
        if (!PCflag) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The player is not logged in");
            scheduler.cancelTasks(plugin);
        }
    }

    private boolean npcEntityNullChecker(Entity entity){
        if (entity == null)
            return false;
        return true;
    }


}
