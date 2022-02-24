package lkc.holographic;

import lkc.holographic.commands.buildblock;
import lkc.holographic.commands.reset;
import lkc.holographic.commands.tpRedStone;
import lkc.holographic.eventlisteners.lungrowValueUpdateEventListener;
import lkc.holographic.eventlisteners.playerJoinEventLIstener;
import lkc.mqttlungrow.commands.subscribe;
import lkc.mqttlungrow.commands.unsubscribe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MqttHolo extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // HolographicDisplay
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        lungrowValueUpdateEventListener LVUEL = new lungrowValueUpdateEventListener(this);
        getServer().getPluginManager().registerEvents(LVUEL, this);
        getCommand("buildblock").setExecutor(new buildblock(LVUEL));
        getCommand("reset").setExecutor(new reset(LVUEL));
        getCommand("tpblock").setExecutor(new tpRedStone(LVUEL));
        getLogger().info("[MqttHolo] Enable");

        // 홀로그래픽 디스플레이 생성 --> Http 요청 생성 완료시 해당 이벤트 리스너로 위치변경

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("[MqttHolo] Disable");
    }
}
