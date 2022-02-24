package lkc.httpconnection.listeners;

import com.google.gson.JsonObject;
import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.httpconnection.events.httpGetClearEvent;
import lkc.httpconnection.formatters.stringToJsonFormatter;
import lkc.httpconnection.http.httpPost;
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
                if(!event.isCommand())
                    Bukkit.getScheduler().runTask(httpCon, () -> Bukkit.getServer().getPluginManager().callEvent(JOPEvent));
                else
                    if(event.isPost()) {
                        httpPost httppost = new httpPost(httpCon);
                        httppost.PosthttpCommand(jsonObject, event.getPlayer());
                    }


            }
        }
        else{
            getLogger().severe("httpConnection fail");
        }

    }
}
