package lkc.holographic.eventlisteners;

import com.google.gson.JsonObject;
import lkc.holographic.blocks.controlRedStoneLamp;
import lkc.lungrow.events.JsonObjectParsingEvent;
import lkc.lungrow.events.lungrowValueUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class lungrowValueUpdateEventListener implements Listener {

    private static lungrowValueUpdateEventListener listener;
    private static Plugin plugin;
    static Location loc;
    static Location buttonloc;
    static Location hologramloc;
    static controlRedStoneLamp controlRedStoneLamp;

    public lungrowValueUpdateEventListener(Plugin plugin){
        listener = this;
        this.plugin = plugin;
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "event 등록");
    }

    @EventHandler
    public void onLungrowValueUpdateEvent(lungrowValueUpdateEvent event){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "event 호출");
        controlRedStoneLamp.updateLungrowValue(event.getInhale(), event.getExhale());
        controlRedStoneLamp.updateBlock();

    }

    @EventHandler
    public void onButtonClickEvent(PlayerInteractEvent event){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "click event 호출");
        controlRedStoneLamp.resetBlock(event.getClickedBlock(), event.getPlayer());
    }


    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("JsonObjectParsingEventArrived");
            JsonObject jsonObject = event.getJsonObject().get("playerInfo").getAsJsonObject();
            JsonObject button = jsonObject.get("button").getAsJsonObject();
            JsonObject redStone = jsonObject.get("redstone").getAsJsonObject();
            JsonObject hologram = jsonObject.get("hologram").getAsJsonObject();
            loc = new Location(getServer().getWorld("world"),integerFormatter(redStone, "x"), integerFormatter(redStone, "y"), integerFormatter(redStone, "z"));// 블록 좌표 바꾸기
            buttonloc = new Location(getServer().getWorld("world"),integerFormatter(button, "x"), integerFormatter(button, "y"), integerFormatter(button, "z"));// 버튼 좌표 바꾸기
            hologramloc = new Location(getServer().getWorld("world"),integerFormatter(hologram, "x"), integerFormatter(hologram, "y"), integerFormatter(hologram, "z"));// 버튼 좌표 바꾸기
            controlRedStoneLamp = new controlRedStoneLamp(loc, buttonloc, hologramloc, plugin);
        }
        else {
            Bukkit.getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    @EventHandler
    public static void OnPlayerExitEvent(PlayerQuitEvent event){
        controlRedStoneLamp.destroyhologram();
        HandlerList.unregisterAll(listener);
    }

    public static int integerFormatter(JsonObject jsonObject, String name){
        return jsonObject.get(name).getAsInt();
    }
}
