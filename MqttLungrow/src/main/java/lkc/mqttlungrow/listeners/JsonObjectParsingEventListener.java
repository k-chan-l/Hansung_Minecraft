package lkc.mqttlungrow.listeners;

import com.google.gson.JsonObject;
import lkc.lungrow.events.JsonObjectParsingEvent;
import lkc.mqttlungrow.MqttLungrow;
import lkc.mqttlungrow.mqttconection.Mqtt;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttException;

import static org.bukkit.Bukkit.getLogger;

public class JsonObjectParsingEventListener implements Listener {

    public static Mqtt MQTT;
    public static MqttLungrow mqttlungrow;
    public final static String BROKER_URL = "wss://0b22b873841d48049e4c3e6331bc584a.s1.eu.hivemq.cloud:8884";
    public static String TOPIC;
    public final static String TopicHeader = "Lungrow/";
    public final static String TopicFooter = "/data";
    public static String ClientID;
    public final static String CLIENT_ID_Header = "clientID_";
    public static String Name;
    public static String password;

    public JsonObjectParsingEventListener(String UserName, String Password, MqttLungrow mqttLungrow){
        Name = UserName;
        password = Password;
        mqttlungrow = mqttLungrow;
    }
    @EventHandler
    public static void OnJsonObjectParsingEvent(JsonObjectParsingEvent event){
        if(event.isFlag()) {
            getLogger().info("[MqttLungrow] JsonObejctParsingEvent");
            JsonObject jsonObject = event.getJsonObject();
            TopicFormatter(jsonObject.get("playerID").toString());
            ClientIDFormatter(jsonObject.get("playerInfo").getAsJsonObject().get("playerName").toString());
            MQTT = new Mqtt(BROKER_URL, TOPIC, ClientID, Name, password, mqttlungrow);
        }
        else {
            getLogger().severe("DB 오류 또는 등록되지 않은 유저입니다.");
        }
    }

    private static void TopicFormatter(String topic){
        TOPIC = TopicHeader + topic.substring(1,topic.length()-1) + TopicFooter;
    }

    private static void ClientIDFormatter(String ID) {
        ClientID = CLIENT_ID_Header + ID.substring(1,ID.length()-1);
    }

    public static void closeMqtt() throws MqttException {
        MQTT.client.disconnect();
    }
}
