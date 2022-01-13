package lkc.npcplugin.events;

import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.chats.ChatBuffer;
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
    static SpeechContext sp;
    private static boolean disableChatWindow;
    private static ChatBuffer buffer;

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        NPCPlugin plugin = NPCPlugin.getInstance();
        Player player = event.getPlayer();
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "fullwall");
        npc.spawn(player.getLocation());
        npc.getNavigator().getLocalParameters().range(255f);
        player.sendMessage(ChatColor.GOLD +""+ npc.getNavigator().getLocalParameters().range());
        npc.getDefaultSpeechController().speak(new SpeechContext((String)"hi"));
        Scheduling(plugin, player, npc);





    }

    public static void pathto(NPC npc, Location destiantion) {
        Location loc = npc.getStoredLocation();
        loc.setX(destiantion.getX());
        loc.setY(destiantion.getY());
        loc.setZ(destiantion.getZ());
        npc.getNavigator().setTarget(loc);
    }


    public static void Scheduling(NPCPlugin plugin, Player player, NPC npc){
        // With BukkitScheduler
        BukkitScheduler scheduler = Bukkit.getScheduler();
        BukkitScheduler scheduler2 = Bukkit.getScheduler();
        Location loc = new Location(getServer().getWorld("world"),-26.0, 64.0, 170.0);
        AtomicBoolean flag = new AtomicBoolean(false);
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                pathto(npc, loc);
                scheduler2.runTaskTimer(plugin, () -> {
                        if (!npc.getNavigator().isNavigating()) {//움직이지 않을 경우
                            if(isDistanceOverNum(npc.getEntity().getLocation(), player.getLocation(), 10)) {//움직이지 않는데 거리가 10이상일 경우
                                flag.set(false);
                            }
                            else{//움직이지 않는데 거리가 10미만일경우
                                if (isDistanceOverNum(npc.getEntity().getLocation(), loc, 5)) {//움직이지 않고 거리가 10미만이면서 목표와의 거리가 5이상 차이나는 경우
                                    getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "다시 출발");
                                    if(flag.get()) {
                                        Location temp = npc.getEntity().getLocation();
                                        temp.setY(temp.getY()+0.2);
                                        npc.despawn();
                                        npc.spawn(temp);
                                        flag.set(false);
                                        pathto(npc, loc);
                                    }
                                    else {
                                        pathto(npc, loc);
                                        flag.set(true);
                                    }
                                } else {//움직이지 않고 거리가 10미만이면서 목표와의 거리가 5미만일 경우
                                    getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "종료");
                                    flag.set(false);
                                    scheduler2.cancelTasks(plugin);
                                }
                            }
                        }
                    else {// 움직일 경우
                        if(isDistanceOverNum(npc.getEntity().getLocation(), player.getLocation(), 10)) {//움직이면서 캐릭터와 거리가 10이상일경우
                            npc.getNavigator().cancelNavigation();
                            npc.faceLocation(player.getLocation());
                            npc.getDefaultSpeechController().speak(new SpeechContext((String) "얼른 이리로와"));
                            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "멈추고 대기");
                        }
                        else
                            flag.set(false);
                    }

                }, 20L * 0 /*<-- the initial delay */, 20L * 1 /*<-- the interval */);

            }
        }, 20L * 5); //20 Tick (1 Second) delay before run() is called

    }

    public static boolean isDistanceOverNum(Location a, Location b, double num){

        if(DistanceCaculating(a,b) >= num)
            return true;
        else
            return false;
    }

    public static Double DistanceCaculating(Location a, Location b){
        double a_x = a.getX();
        double a_y = a.getY();
        double a_z = a.getZ();
        double b_x = b.getX();
        double b_y = b.getY();
        double b_z = b.getZ();
        double ans = Math.sqrt(Math.pow(a_x - b_x,2) + Math.pow(a_y - b_y,2) + Math.pow(a_z - b_z,2));
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "" + ans);
        return ans;
    }

}
