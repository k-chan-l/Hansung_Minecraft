package lkc.testb;

import lkc.testb.something.Something;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestB extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info("[Test Plugin B] enable");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("[Test Plugin B] disable");
    }

    public static int num(){
        return 300;
    }
}
