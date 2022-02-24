package lkc.npcplugin.listeners;

import com.google.gson.JsonObject;
import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.npc.Guide;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import java.io.IOException;
import java.util.HashMap;

import static org.bukkit.Bukkit.*;

public class PlayerEvent implements Listener {

    private static HashMap<String, Guide> guideHashMap = new HashMap<String, Guide>();

    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[NPCPlugin] JsonObejctParsingEvent");
            JsonObject jsonObject = event.getJsonObject().get("playerInfo").getAsJsonObject();
            JsonObject npctarget = jsonObject.get("npctarget").getAsJsonObject();
            Player player = event.getPlayer();
            NPCPlugin plugin = NPCPlugin.getInstance();
            Location location = new Location(getServer().getWorld("world"),integerFormatter(npctarget, "x"), integerFormatter(npctarget, "y"), integerFormatter(npctarget, "z"));// 버튼 좌표 바꾸기
            Guide guide = new Guide(player, "fullwall", plugin, location);
            guide.init();
            if(guideHashMap.containsKey(player.getName())) {
                getLogger().info("[NPCPlugin] Guide is already exist");
            }
            else
                guideHashMap.put(player.getName(),guide);
        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    @EventHandler
    public static void onPlayerOut(PlayerQuitEvent event) throws IOException {
        String name = event.getPlayer().getName();
        if(guideHashMap.containsKey(name)) {
            guideHashMap.get(name).getNpc().destroy();
            guideHashMap.remove(name);
            getLogger().info("[NPCPlugin] Remove guide");
        }
        else
            getLogger().info("[NPCPlugin] Guide is already removed");
    }

    public static int integerFormatter(JsonObject jsonObject, String name){
        return jsonObject.get(name).getAsInt();
    }
}
