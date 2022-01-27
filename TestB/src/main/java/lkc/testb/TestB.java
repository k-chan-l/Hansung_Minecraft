package lkc.testb;

import lkc.testb.something.Something;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestB extends JavaPlugin {
    public Something something;

    @Override
    public void onEnable() {
        // Plugin startup logic
        something = new Something();
        getServer().getLogger().info("[Test Plugin B] enable");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("[Test Plugin B] disable");
    }
}
