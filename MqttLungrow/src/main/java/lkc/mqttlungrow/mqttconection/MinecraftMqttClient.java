package lkc.mqttlungrow.mqttconection;

import lkc.mqttlungrow.MqttLungrow;
import lkc.mqttlungrow.jsons.jsonParsing;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class MinecraftMqttClient implements MqttCallback {
    MqttLungrow mqttLungrow;
    MqttClient client;
    MqttConnectOptions connOpt;
    Logger logger;
    HashMap<String,String> topic = new HashMap<String,String>();//HashMap생성
    HashMap<String,Player> player = new HashMap<String, Player>();//HashMap생성

    public MinecraftMqttClient(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.severe("[MqttLungrow] MQTT - Connection lost.");
        // reconnect when connection is lost.
        while(!client.isConnected()){
            try{
                client.connect(connOpt);
                logger.info("[MqttLungrow] reconecting");
                // Setup subscriber
                Iterator<String> keys = topic.keySet().iterator();
                while(keys.hasNext()){
                    String key = keys.next();
                    subscribe(key, null);
                }

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
        Player user = player.get(topic);
        json.updatemqttValue(user);
        logger.info("[MqttLungrow] Message arrived for topic " + topic + ": " + mm);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("[MqttLungrow] Delivery complete!");
    }

//    public void setup(String brokerUrl, String clientId, String topic, String Username, String Password, MqttLungrow mqttLungrow) throws MqttException {
//        //setup mqttLunggrow
//        this.mqttLungrow = mqttLungrow;
//        // Start connection setup with password
//        Topic = topic;
//        connOpt = new MqttConnectOptions();
//        connOpt.setCleanSession(true);
//        connOpt.setAutomaticReconnect(true);
//        connOpt.setKeepAliveInterval(30);
//        connOpt.setUserName(Username);
//        connOpt.setPassword(Password.toCharArray());
//
//        MemoryPersistence persistence = new MemoryPersistence();
//
//        client = new MqttClient(brokerUrl, clientId);
//        client.setCallback(this);
//        client.connect(connOpt);
//
//        // Setup subscriber
//        int subQoS = 0;
//        client.subscribe(Topic, subQoS);
//    }

    public void setup(String brokerUrl, String clientId,String Username, String Password, MqttLungrow mqttLungrow) throws MqttException {
        //setup mqttLunggrow without topic
        this.mqttLungrow = mqttLungrow;
        // Start connection setup with password
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

    }

    public void subscribe(String Topic, Player user) throws MqttException {
        // Setup subscriber
        if(topic.containsKey(Topic)) {
            getLogger().info("[MqttLungrow] topic "+Topic+" is already exist");
        }
        else {
            topic.put(Topic,Topic);
            if(user != null && !player.containsKey(Topic))
                player.put(Topic,user);
            else
                logger.severe("user already exist of user is null");
        }
        int subQoS = 0;
        client.subscribe(Topic, subQoS);
    }

    public void unsubscribe(String Topic) throws MqttException {
        if(topic.containsKey(Topic)) {
            topic.remove(Topic);
            if(player.containsKey(Topic)){
                player.remove(Topic);
            }
        }
        else {
            getLogger().info("[MqttLungrow] topic " + Topic + " is already removed");
        }
        client.unsubscribe(Topic);
    }

//    public void setup(String brokerUrl, String clientId, String topic) throws MqttException {
//
//        // Start connection
//        Topic = topic;
//        connOpt = new MqttConnectOptions();
//        connOpt.setCleanSession(true);
//        connOpt.setAutomaticReconnect(true);
//        connOpt.setKeepAliveInterval(30);
//
//        MemoryPersistence persistence = new MemoryPersistence();
//
//        client = new MqttClient(brokerUrl, clientId);
//        client.setCallback(this);
//        client.connect(connOpt);
//
//        // Setup subscriber
//        int subQoS = 0;
//        client.subscribe(Topic, subQoS);
//    }

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
            getServer().getLogger().severe("[MqttLungrow] client is disconnected");
        }
    }

//    private String splitTopic(String topic){
//        topic.
//        return
//    }
}
