package lkc.mqttlungrow.mqttconection;

import lkc.mqttlungrow.MqttLungrow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.MqttException;

import static org.bukkit.Bukkit.getServer;

public class Mqtt {

    // MQTT
    public MinecraftMqttClient client;


    public Mqtt(String BROKER_URL, String CLIENT_ID, String UserName, String Password, MqttLungrow mqttLungrow) {
        // client with password
        client = new MinecraftMqttClient(getServer().getLogger());
        try {
            client.setup(BROKER_URL, CLIENT_ID, UserName, Password, mqttLungrow);
        } catch (MqttException e) {
            mqttLungrow.getLogger().severe("Failed setting up connection with MQTT BROKER "+ e.getLocalizedMessage());
        }
    }
    public void subscribe(String topic, Player player) throws MqttException {
        getServer().getLogger().info("[MqttLungrow] subscribe topic : "+ topic);
        client.subscribe(topic, player);
    }

    public void unsubscribe(String Topic) throws MqttException {
        getServer().getLogger().info("[MqttLungrow] unsubscribe topic : "+ Topic);
        client.unsubscribe(Topic);
    }

//    public Mqtt(String BROKER_URL, String TOPIC, String CLIENT_ID, String UserName, String Password, MqttLungrow mqttLungrow) {
//        // client with password
//        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
//        try {
//            client.setup(BROKER_URL, CLIENT_ID, TOPIC, UserName, Password, mqttLungrow);
//        } catch (MqttException e) {
//            mqttLungrow.getLogger().severe("Failed setting up connection with MQTT BROKER "+ e.getLocalizedMessage());
//        }
//    }


//
//    public Mqtt(String BROKER_URL, String TOPIC, String CLIENT_ID, MqttLungrow mqttLungrow) {
//        // client
//        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
//        try {
//            client.setup(BROKER_URL, CLIENT_ID, TOPIC);
//        } catch (MqttException e) {
//            mqttLungrow.getLogger().severe("Failed setting up connection with MQTT BROKER "+ e.getLocalizedMessage());
//        }
//    }
}
