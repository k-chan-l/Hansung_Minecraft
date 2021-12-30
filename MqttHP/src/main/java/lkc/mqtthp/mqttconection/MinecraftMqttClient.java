package lkc.mqtthp.mqttconection;

import lkc.mqtthp.Main;
import lkc.mqtthp.sechdules.RepeatDamage;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class MinecraftMqttClient implements MqttCallback {
    MqttClient client;
    MqttConnectOptions connOpt;
    Logger logger;
    String Topic;
    ArrayList<RepeatDamage> RepeatDamageList;

    public MinecraftMqttClient(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
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
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        String message = mm.toString();
        logger.info("Message arrived for topic " + topic + ": " + message);
        if (topic.equals(Main.getInstance().TOPIC2)){
            Player player = getPlayer(message);
            if (player == null){
                logger.info("player " + message + " is not found");
            }
            else{
                Iterator<RepeatDamage> iterator = RepeatDamageList.iterator();
                while (iterator.hasNext()){
                    RepeatDamage RD = iterator.next();
                    if(RD.player == player){
                        RD.flag = true;
                        break;
                    }
                }

            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        logger.info("Delivery complete!");
    }

    public void setup(String brokerUrl, String clientId, String topic) throws MqttException {
        if (topic.equals(Main.getInstance().TOPIC2)) {
            RepeatDamageList = new ArrayList<RepeatDamage>();
        }
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

    public void addRepeatDamage(RepeatDamage repeatDamage) {
        RepeatDamageList.add(repeatDamage);
    }
}
