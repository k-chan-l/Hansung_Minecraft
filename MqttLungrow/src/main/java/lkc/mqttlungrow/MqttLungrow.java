package lkc.mqttlungrow;

import lkc.mqttlungrow.eventlisteners.lungrowValueUpdateEventListener;
import lkc.mqttlungrow.events.lungrowVauleUpdateEvent;
import lkc.mqttlungrow.mqttconection.Mqtt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class MqttLungrow extends JavaPlugin {

    private static MqttLungrow instance;
    Mqtt mqtt;
    public final static String BROKER_URL = "wss://0b22b873841d48049e4c3e6331bc584a.s1.eu.hivemq.cloud:8884";
    public final static String TOPIC = "Lungrow/bgloh.doctorr@gmail.com/data";
    public final static String CLIENT_ID = "clientID-ABCSDFE32af";;
    public final static String UserName = "lungrow";
    public final static String Password = "Lungrow12345678";
    private int inhale = 0;
    private int exhale = 0;


    @Override
    public void onEnable() {
        // Plugin startup logic
        mqtt = new Mqtt(BROKER_URL, TOPIC, CLIENT_ID, UserName, Password, this);
        MqttLungrow.instance = this;
        Bukkit.getServer().getPluginManager().registerEvents(new lungrowValueUpdateEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            mqtt.client.disconnect();
        } catch (MqttException e) {
            getLogger().severe("Failed disconnecting from MQTT BROKER !");
        }
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttHP]: Plugin is disabled!");
    }

    public static MqttLungrow getInstance() {
        return MqttLungrow.instance;
    }

    public void updateValue(int inhale, int exhale){
        this.inhale = inhale;
        this.exhale = exhale;
        getLogger().info("exhale inhale value is updated\ninhale : " + this.inhale + " exhale : " + this.exhale);
        lungrowVauleUpdateEvent event = new lungrowVauleUpdateEvent("Sample Message");
        event.updateValue(this.inhale, this.exhale);
        Bukkit.getScheduler().runTask(this, () -> Bukkit.getPluginManager().callEvent(event));
    }

}
