package lkc.mqtthp;

import lkc.mqtthp.commands.commands;
import lkc.mqtthp.mqttconection.MqttHP;
import lkc.mqtthp.mqttconection.commandSendMqtt;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main extends JavaPlugin {


    private static Main instance;
    public static MqttHP mqtt1;
    MqttHP mqtt2;

    public final static String BROKER_URL = "tcp://www.mqtt-dashboard.com:1883";
    public final static String TOPIC1 = "minecraftcomunication";
    public final static String TOPIC2 = "usersminecrafting";
    public final static String CLIENT_ID1;
    public final static String CLIENT_ID2;


    static {
        CLIENT_ID1 = "Message2MQTT";
        CLIENT_ID2 = "usertest2MQTT";
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        mqtt1 = new MqttHP(BROKER_URL, TOPIC1, CLIENT_ID1, this);
        mqtt2 = new MqttHP(BROKER_URL, TOPIC2, CLIENT_ID2, this);
        // register command
        getCommand("sendmqtt").setExecutor(new commandSendMqtt());
        getCommand("hit").setExecutor(new commands());
        Main.instance = this;
    }

    @Override
    public void onDisable() {

        try {
            mqtt1.client.disconnect();
            mqtt2.client.disconnect();
        } catch (MqttException e) {
            getLogger().severe("Failed disconnecting from MQTT BROKER !");
        }
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttHP]: Plugin is disabled!");
    }


    public static Main getInstance() {
        return Main.instance;
    }

}
