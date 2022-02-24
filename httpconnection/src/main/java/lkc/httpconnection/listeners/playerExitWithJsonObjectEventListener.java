package lkc.httpconnection.listeners;

import com.google.gson.JsonObject;
import lkc.httpconnection.http.httpPost;
import lkc.httpconnection.httpconnection;
import lkc.httpconnection.events.playerExitWithJsonObjectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class playerExitWithJsonObjectEventListener implements Listener {
    private static httpconnection httpCon;
    private JsonObject jsonObject;
    private Player player;
    public playerExitWithJsonObjectEventListener(httpconnection httpConnection){
        httpCon = httpConnection;
    }

    @EventHandler
    public void onplayerExitWithJsonObject(playerExitWithJsonObjectEvent event){
        jsonObject = event.getJsonObject();
        player = event.getPlayer();
        httpPost httppost = new httpPost(httpCon);
        httppost.Posthttp(jsonObject);
    }
}
