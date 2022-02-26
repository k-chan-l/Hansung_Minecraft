package lkc.letterbuild;

import lkc.letterbuild.commands.getItemList;
import org.bukkit.plugin.java.JavaPlugin;

public final class Letterbuild extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("getitemlist").setExecutor(new getItemList());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
