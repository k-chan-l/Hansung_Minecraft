package lkc.checkpath.Listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lkc.lungrow.events.JsonObjectParsingEvent;
import lkc.lungrow.events.playerExitWithJsonObjectEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPluginManager;

public class CheckpathListener implements Listener {
    Plugin plugin;
    JsonObject jsonObject;
    BukkitScheduler scheduler;
    Player player;
    JsonArray path;
    Boolean flag = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public CheckpathListener(Plugin main){
        plugin = main;
        getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[Checkpath] JsonObejctParsingEvent");
            flag = true;
            jsonObject = event.getJsonObject();
            player = event.getPlayer();
            path = new JsonArray();
            scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    path.add(getPlayerLocation(player));
                }
            }, 0L, 20L * 60);
        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }


    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(!flag)
            return;
        scheduler.cancelTasks(plugin);
        jsonObject.get("playerInfo").getAsJsonObject().add("path", path);// 기존 있던 경로 덮어쓰기
        jsonObject.addProperty("SK","playerName#"+event.getPlayer().getName()+"#date#"+getNow());
        playerExitWithJsonObjectEvent PEWJOEvent = new playerExitWithJsonObjectEvent(jsonObject,event.getPlayer());
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getServer().getPluginManager().callEvent(PEWJOEvent));
    }

    private JsonObject getPlayerLocation(Player user){
        JsonObject object = new JsonObject();

        object.addProperty("x",user.getLocation().getX());
        object.addProperty("y",user.getLocation().getY());
        object.addProperty("z",user.getLocation().getZ());
        getLogger().info(user.getName() + " location update\nX : " + user.getLocation().getX()+" Y : "+user.getLocation().getY()+" Z : "+user.getLocation().getZ());
        return object;
    }

    private String getNow(){
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(formatter);
        return formatedNow;
    }

}
