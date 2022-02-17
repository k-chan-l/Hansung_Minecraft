package lkc.mqttlungrow;

import lkc.lungrow.events.lungrowValueUpdateEvent;
import lkc.mqttlungrow.listeners.JsonObjectParsingEventListener;
import lkc.mqttlungrow.listeners.PlayerExitEventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
public final class MqttLungrow extends JavaPlugin {

    private static MqttLungrow instance;
    private static JsonObjectParsingEventListener JOPEL;
    public final static String UserName = "lungrow";
    public final static String Password = "Lungrow12345678";
    private int inhale = 0;
    private int exhale = 0;


    @Override
    public void onEnable() {
        // Plugin startup logic
        JOPEL = new JsonObjectParsingEventListener( UserName, Password, this);
        getServer().getPluginManager().registerEvents(JOPEL, this);
        getServer().getPluginManager().registerEvents(new PlayerExitEventListener(JOPEL), this);
        MqttLungrow.instance = this;
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttLungrow] Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MqttLungrow] Plugin is disabled!");
    }

    public static MqttLungrow getInstance() {
        return MqttLungrow.instance;
    }

    public void updateValue(int inhale, int exhale){
        this.inhale = inhale;
        this.exhale = exhale;
        getLogger().info("inhale : " + this.inhale + " exhale : " + this.exhale);
        lungrowValueUpdateEvent event = new lungrowValueUpdateEvent("Sample Message");
        event.updateValue(this.inhale, this.exhale);
        Bukkit.getServer().getScheduler().runTask(this, () -> Bukkit.getServer().getPluginManager().callEvent(event));

    }

}
