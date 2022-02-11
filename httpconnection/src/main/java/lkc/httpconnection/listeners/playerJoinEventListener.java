package lkc.httpconnection.listeners;

import lkc.httpconnection.http.httpGet;
import lkc.httpconnection.httpconnection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEventListener implements Listener {
    private static httpconnection httpCon;
    private static String args = "playerName";

    public playerJoinEventListener(httpconnection httpConnection) { httpCon = httpConnection;}

    @EventHandler
    public static void onPlayerJoinEvent(PlayerJoinEvent event){
        Player sender = event.getPlayer();
        httpGet httpget = new httpGet(args, sender, httpCon);
        httpget.Gethttp(sender);
    }
}
