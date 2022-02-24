package lkc.holographic.eventlisteners;

import com.google.gson.JsonObject;
import lkc.holographic.blocks.Userdata;
import lkc.holographic.blocks.controlRedStoneLamp;
import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.mqttlungrow.events.lungrowValueUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.security.PublicKey;
import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class lungrowValueUpdateEventListener implements Listener {

    private static lungrowValueUpdateEventListener listener;
    private static Plugin plugin;
    private static HashMap<String, Userdata> User = new HashMap<String, Userdata>();

    public Userdata getUserdata(Player player){
        return User.get(player.getName());
    }

    public lungrowValueUpdateEventListener(Plugin plugin){
        listener = this;
        this.plugin = plugin;
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "event 등록");
    }

    @EventHandler
    public void onLungrowValueUpdateEvent(lungrowValueUpdateEvent event){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "event 호출");
        String name = event.getPlayer().getName();
        if (User.containsKey(name)) {
            controlRedStoneLamp CRSL = User.get(name).getControlRedStoneLamp();
            CRSL.updateLungrowValue(event.getInhale(), event.getExhale());
            CRSL.updateBlock();
        }

    }

    @EventHandler
    public void onButtonClickEvent(PlayerInteractEvent event){
        if(event.getClickedBlock() == null)
            return;
        String name = event.getPlayer().getName();
        if (User.containsKey(name))
            User.get(name).getControlRedStoneLamp().resetBlock(event.getClickedBlock(), event.getPlayer());
    }


    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[MqttHolo] JsonObejctParsingEvent");
            JsonObject jsonObject = event.getJsonObject().get("playerInfo").getAsJsonObject();
            JsonObject button = jsonObject.get("button").getAsJsonObject();
            JsonObject redStone = jsonObject.get("redstone").getAsJsonObject();
            JsonObject hologram = jsonObject.get("hologram").getAsJsonObject();

            Location loc = new Location(getServer().getWorld("world"),integerFormatter(redStone, "x"), integerFormatter(redStone, "y"), integerFormatter(redStone, "z"));// 블록 좌표 바꾸기
            Location buttonloc = new Location(getServer().getWorld("world"),integerFormatter(button, "x"), integerFormatter(button, "y"), integerFormatter(button, "z"));// 버튼 좌표 바꾸기
            Location hologramloc = new Location(getServer().getWorld("world"),integerFormatter(hologram, "x"), integerFormatter(hologram, "y"), integerFormatter(hologram, "z"));// 버튼 좌표 바꾸기
            String name = event.getPlayer().getName();
            controlRedStoneLamp controlRedStoneLamp = new controlRedStoneLamp(loc, buttonloc, hologramloc, plugin, name);
            Userdata userdata = new Userdata(loc, buttonloc, hologramloc, controlRedStoneLamp);
            if(User.containsKey(name))
                getLogger().info("[MqttHolo] the userDB is already exist");
            else {
                User.put(name, userdata);
                getLogger().info("[MqttHolo] Create userDB");
            }


        }
        else {
            Bukkit.getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    @EventHandler
    public static void OnPlayerExitEvent(PlayerQuitEvent event){
        String name = event.getPlayer().getName();
        if (User.containsKey(name)) {
            User.get(name).getControlRedStoneLamp().destroyhologram();
            User.remove(name);
            getLogger().info("[MqttHolo] removing UserDB");
        }
        else
            getLogger().info("[MqttHolo] the userDB is already removed");
    }

    public static int integerFormatter(JsonObject jsonObject, String name){
        return jsonObject.get(name).getAsInt();
    }
}
