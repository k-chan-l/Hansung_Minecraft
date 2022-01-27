package lkc.testa;

import lkc.testa.commands.command;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestA extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("testBMethod").setExecutor(new command());
        getServer().getLogger().info("[Test Plugin A] enable");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("[Test Plugin A] disable");
    }
}
