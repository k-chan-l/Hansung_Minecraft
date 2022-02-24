package lkc.mqttlungrow.listeners;

import com.google.gson.JsonObject;
import lkc.httpconnection.events.JsonObjectParsingEvent;
import lkc.mqttlungrow.MqttLungrow;
import lkc.mqttlungrow.mqttconection.Mqtt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;

public class JsonObjectParsingEventListener implements Listener {

    public static Mqtt MQTT;
    public static MqttLungrow mqttlungrow;
    private static HashMap<String,String> TOPIC = new HashMap<String, String>();
    public final static String TopicHeader = "Lungrow/";
    public final static String TopicFooter = "/data";

//    public JsonObjectParsingEventListener(String UserName, String Password, MqttLungrow mqttLungrow){
//        Name = UserName;
//        password = Password;
//        mqttlungrow = mqttLungrow;
//    }

    public JsonObjectParsingEventListener(Mqtt mqtt){
        MQTT = mqtt;
    }

    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event) throws MqttException {
        if(event.isFlag()) {
            getLogger().info("[MqttLungrow] JsonObejctParsingEvent");
            String name = event.getPlayer().getName();
            JsonObject jsonObject = event.getJsonObject();
            TopicFormatter(jsonObject.get("playerID").toString(), name);
            MQTT.subscribe(TOPIC.get(name), event.getPlayer());
        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    private static void TopicFormatter(String topic, String name){
        if(TOPIC.containsKey(name))
            getLogger().severe("already topic exist");
        else
            TOPIC.put(name, TopicHeader + topic.substring(1,topic.length()-1) + TopicFooter);
    }

    public static void unsubscribe(Player player) throws MqttException {
        String name = player.getName();
        if(TOPIC.containsKey(name)) {
            String topic = TOPIC.get(name);
            MQTT.unsubscribe(topic);
            TOPIC.remove(name);
        }
        else
            getLogger().severe("no topic exist");
    }
}
