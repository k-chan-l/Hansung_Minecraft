package lkc.npcplugin.events;

import com.google.gson.JsonObject;
import lkc.lungrow.events.JsonObjectParsingEvent;
import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.npc.Guide;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.io.IOException;

import static org.bukkit.Bukkit.*;

public class PlayerEvent implements Listener {
    public static Player player;
    public static Guide guide;

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        player = event.getPlayer();
    }

    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[NPCPlugin] JsonObejctParsingEvent");
            JsonObject jsonObject = event.getJsonObject().get("playerInfo").getAsJsonObject();
            JsonObject npctarget = jsonObject.get("npctarget").getAsJsonObject();
            NPCPlugin plugin = NPCPlugin.getInstance();
            Location location = new Location(getServer().getWorld("world"),integerFormatter(npctarget, "x"), integerFormatter(npctarget, "y"), integerFormatter(npctarget, "z"));// 버튼 좌표 바꾸기
            guide = new Guide(player, "fullwall", plugin, location);
            guide.init();
        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    @EventHandler
    public static void onPlayerOut(PlayerQuitEvent event) throws IOException {
        guide.getNpc().destroy();
    }

    public static int integerFormatter(JsonObject jsonObject, String name){
        return jsonObject.get(name).getAsInt();
    }
}
