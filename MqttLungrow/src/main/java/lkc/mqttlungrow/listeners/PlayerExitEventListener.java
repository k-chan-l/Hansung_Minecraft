package lkc.mqttlungrow.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.eclipse.paho.client.mqttv3.MqttException;

import static org.bukkit.Bukkit.getLogger;

public class PlayerExitEventListener implements Listener {
    private static JsonObjectParsingEventListener JOPEL;

    public PlayerExitEventListener(JsonObjectParsingEventListener Jopel){
        JOPEL = Jopel;
    }

    @EventHandler
    public static void onPlayerExitEvent(PlayerQuitEvent event){
        // Plugin shutdown logic
        try {
            JOPEL.unsubscribe(event.getPlayer());
        } catch (MqttException e) {
            getLogger().severe("Failed disconnecting from MQTT BROKER !");
        }
    }
}
