package lkc.mqttlungrow.mqttconection;

import lkc.mqttlungrow.MqttLungrow;
import lkc.mqttlungrow.jsons.jsonParsing;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

public class MinecraftMqttClient implements MqttCallback {
    MqttLungrow mqttLungrow;
    MqttClient client;
    MqttConnectOptions connOpt;
    Logger logger;
    String Topic;

    public MinecraftMqttClient(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.severe("MQTT - Connection lost.");
        // reconnect when connection is lost.
        while(!client.isConnected()){
            try{
                client.connect(connOpt);
                logger.info("reconnected....");
                // Setup subscriber
                int subQoS = 0;
                client.subscribe(Topic, subQoS);
            }
            catch (MqttException ex){
                logger.info(ex.getMessage());
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String mm = message.toString();
        jsonParsing json = new jsonParsing(mm, logger, mqttLungrow);
        // update mqttLunggrow -> inhale, exhale
        json.updatemqttValue();
        logger.info("Message arrived for topic " + topic + ": " + mm);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Delivery complete!");
    }

    public void setup(String brokerUrl, String clientId, String topic, String Username, String Password, MqttLungrow mqttLungrow) throws MqttException {
        //setup mqttLunggrow
        this.mqttLungrow = mqttLungrow;
        // Start connection setup with password
        Topic = topic;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setAutomaticReconnect(true);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName(Username);
        connOpt.setPassword(Password.toCharArray());

        MemoryPersistence persistence = new MemoryPersistence();

        client = new MqttClient(brokerUrl, clientId);
        client.setCallback(this);
        client.connect(connOpt);

        // Setup subscriber
        int subQoS = 0;
        client.subscribe(Topic, subQoS);
    }

    public void setup(String brokerUrl, String clientId, String topic) throws MqttException {

        // Start connection
        Topic = topic;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setAutomaticReconnect(true);
        connOpt.setKeepAliveInterval(30);

        MemoryPersistence persistence = new MemoryPersistence();

        client = new MqttClient(brokerUrl, clientId);
        client.setCallback(this);
        client.connect(connOpt);

        // Setup subscriber
        int subQoS = 0;
        client.subscribe(Topic, subQoS);
    }

    public void publish(String topic, String payload) throws MqttException {
        int pubQoS = 0;
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        if (client.isConnected()) {
            client.publish(topic, message);
        }
    }

    public void disconnect() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();
            getServer().getLogger().severe("client : " + Topic + " is disconnected");
        }
    }
}
