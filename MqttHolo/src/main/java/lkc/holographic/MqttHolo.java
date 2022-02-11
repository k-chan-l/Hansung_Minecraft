package lkc.holographic;

import lkc.holographic.eventlisteners.lungrowValueUpdateEventListener;
import lkc.holographic.eventlisteners.playerJoinEventLIstener;
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
        lungrowValueUpdateEventListener lVUEL = new lungrowValueUpdateEventListener(this);
        getServer().getPluginManager().registerEvents(new playerJoinEventLIstener(lVUEL, this),this);

        // 홀로그래픽 디스플레이 생성 --> Http 요청 생성 완료시 해당 이벤트 리스너로 위치변경

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
