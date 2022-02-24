package lkc.mqttlungrow;

import lkc.httpconnection.commands.commandGetHttp;
import lkc.mqttlungrow.commands.subscribe;
import lkc.mqttlungrow.commands.unsubscribe;
import lkc.mqttlungrow.events.lungrowValueUpdateEvent;
import lkc.mqttlungrow.listeners.JsonObjectParsingEventListener;
import lkc.mqttlungrow.listeners.PlayerExitEventListener;
import lkc.mqttlungrow.mqttconection.Mqtt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class MqttLungrow extends JavaPlugin {

    private static JsonObjectParsingEventListener JOPEL;
    private static Mqtt MQTT;
    public final static String BROKER_URL = "wss://0b22b873841d48049e4c3e6331bc584a.s1.eu.hivemq.cloud:8884";
    public final static String UserName = "lungrow";
    public final static String Password = "Lungrow12345678";
    public static String ClientID = "client_12345678";


    @Override
    public void onEnable() {
        // Plugin startup logic
        MQTT = new Mqtt(BROKER_URL, ClientID, UserName, Password, this);
        JOPEL = new JsonObjectParsingEventListener(MQTT);
        getServer().getPluginManager().registerEvents(JOPEL, this);
        getServer().getPluginManager().registerEvents(new PlayerExitEventListener(JOPEL), this);
        getCommand("subscribe").setExecutor(new subscribe(MQTT));
        getCommand("unsubscribe").setExecutor(new unsubscribe(MQTT));
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttLungrow] Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttLungrow] Plugin is disabled!");
    }

    public void updateValue(int inhale, int exhale, Player player){
        getLogger().info("inhale : " + inhale + " exhale : " + exhale);
        lungrowValueUpdateEvent event = new lungrowValueUpdateEvent("Sample Message");
        event.updateValue(inhale, exhale);
        event.setPlayer(player);
        Bukkit.getServer().getScheduler().runTask(this, () -> Bukkit.getServer().getPluginManager().callEvent(event));

    }

    public static void disconnect() throws MqttException {
        MQTT.client.disconnect();
    }
}
