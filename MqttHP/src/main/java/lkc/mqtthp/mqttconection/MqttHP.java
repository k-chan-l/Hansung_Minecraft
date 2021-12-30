package lkc.mqtthp.mqttconection;

import lkc.mqtthp.Main;
import org.bukkit.Bukkit;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class MqttHP{

    // MQTT
    public MinecraftMqttClient client;


    public MqttHP(String BROKER_URL, String TOPIC, String CLIENT_ID, Main main) {
        // client
        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
        try {
            client.setup(BROKER_URL, CLIENT_ID, TOPIC);
        } catch (MqttException e) {
            main.getLogger().severe("Failed setting up connection with MQTT BROKER");
        }
    }
}
