package lkc.httpconnection.listeners;

import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.httpconnection.formatters.jsonToStringFormatter;
import lkc.httpconnection.http.httpGet;
import lkc.httpconnection.http.httpPut;
import lkc.httpconnection.httpconnection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JsonObjectParsingEventListener implements Listener {
    private static httpconnection httpCon;

    public JsonObjectParsingEventListener(httpconnection httpConnection) {
        httpCon = httpConnection;
    }

    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            httpPut httpput = new httpPut(httpCon);
            httpput.Puthttp(jsonToStringFormatter.StringFormatting(event.getJsonObject()));
        }
        else {
            Bukkit.getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }
}
