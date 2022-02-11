package lkc.mqttlungrow.mqttconection;

import lkc.mqttlungrow.MqttLungrow;
import org.bukkit.Bukkit;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Mqtt {

    // MQTT
    public MinecraftMqttClient client;

    public Mqtt(String BROKER_URL, String TOPIC, String CLIENT_ID, String UserName, String Password, MqttLungrow mqttLungrow) {
        // client with password
        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
        try {
            client.setup(BROKER_URL, CLIENT_ID, TOPIC, UserName, Password, mqttLungrow);
        } catch (MqttException e) {
            mqttLungrow.getLogger().severe("Failed setting up connection with MQTT BROKER "+ e.getLocalizedMessage());
        }
    }

    public Mqtt(String BROKER_URL, String TOPIC, String CLIENT_ID, MqttLungrow mqttLungrow) {
        // client
        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
        try {
            client.setup(BROKER_URL, CLIENT_ID, TOPIC);
        } catch (MqttException e) {
            mqttLungrow.getLogger().severe("Failed setting up connection with MQTT BROKER "+ e.getLocalizedMessage());
        }
    }
}
