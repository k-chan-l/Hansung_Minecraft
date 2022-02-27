package lkc.letterbuild;

import lkc.letterbuild.commands.getItemList;
import org.bukkit.plugin.java.JavaPlugin;

public final class Letterbuild extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("letter").setExecutor(new getItemList());
        getLogger().info("[Letterbuild] : Plugin Enable");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("[Letterbuild] : Plugin disable");
    }
}
