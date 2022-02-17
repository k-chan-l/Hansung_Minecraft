package lkc.checkpath;

import lkc.checkpath.Listeners.CheckpathListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Checkpath extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info("[Checkpath] Enable");
        CheckpathListener checkpathListener = new CheckpathListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("[Checkpath] Disable");
    }
}
