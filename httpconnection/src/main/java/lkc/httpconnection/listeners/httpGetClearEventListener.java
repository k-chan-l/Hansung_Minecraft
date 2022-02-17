package lkc.httpconnection.listeners;

import com.google.gson.JsonObject;
import lkc.lungrow.events.JsonObjectParsingEvent;
import lkc.lungrow.events.httpGetClearEvent;
import lkc.httpconnection.formatters.stringToJsonFormatter;
import lkc.httpconnection.httpconnection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getLogger;

public class httpGetClearEventListener implements Listener {
    private static httpconnection httpCon;
    public httpGetClearEventListener(httpconnection httpConnection) { httpCon = httpConnection;}

    @EventHandler
    public static void OnhttpGetClearEventEvent(httpGetClearEvent event){
        if(event.isFlag()){
            JsonObjectParsingEvent JOPEvent = new JsonObjectParsingEvent();
            JsonObject jsonObject = stringToJsonFormatter.JsonParsing(event.getMessage());
            if(jsonObject == null){
                getLogger().severe("Invalid jsonObject");
                JOPEvent.setFlag(false);
                Bukkit.getScheduler().runTask(httpCon, () -> Bukkit.getServer().getPluginManager().callEvent(JOPEvent));
            }
            else {
                JOPEvent.setFlag(true);
                JOPEvent.setJsonObject(jsonObject);
                JOPEvent.setPlayer(event.getPlayer());
                Bukkit.getScheduler().runTask(httpCon, () -> Bukkit.getServer().getPluginManager().callEvent(JOPEvent));
            }
        }
        else{
            getLogger().severe("httpConnection fail");
        }

    }
}
