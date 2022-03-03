package lkc.checkpath.Listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lkc.checkpath.Users.User;
import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.httpconnection.events.playerExitWithJsonObjectEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPluginManager;

public class CheckpathListener implements Listener {
    Plugin plugin;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    HashMap<String, User> Userlist = new HashMap<String, User>();

    public CheckpathListener(Plugin main){
        plugin = main;
        getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[Checkpath] JsonObejctParsingEvent");
            JsonObject jsonObject = event.getJsonObject();
            Player player = event.getPlayer();
            JsonArray path = new JsonArray();
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    path.add(getPlayerLocation(player));
                }
            }, 0L, 20L * 60);
            User user = new User(jsonObject,  scheduler, player, path);
            if (Userlist.containsKey(player.getName()))
                getLogger().severe("[Checkpath] already exist user");
            else {
                Userlist.put(player.getName(),user);
            }

        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }


    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        String name = event.getPlayer().getName();
        if (!Userlist.containsKey(name))
            getLogger().severe("[Checkpath] already user removed");
        else {
            User user = Userlist.get(name);
            BukkitScheduler scheduler = user.getScheduler();
            JsonObject jsonObject = user.getJsonObject();
            JsonArray path = user.getPath();
            scheduler.cancelTasks(plugin);
            jsonObject.get("playerInfo").getAsJsonObject().add("path", path);// 기존 있던 경로 덮어쓰기
            jsonObject.addProperty("SK","playerName#"+event.getPlayer().getName()+"#date#"+getNow());
            playerExitWithJsonObjectEvent PEWJOEvent = new playerExitWithJsonObjectEvent(jsonObject,event.getPlayer());
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getServer().getPluginManager().callEvent(PEWJOEvent));
            Userlist.remove(name);
        }
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
        LocalDateTime now = LocalDateTime.now().minusHours(9);
        String formatedNow = now.format(formatter);
        return formatedNow;
    }

}
