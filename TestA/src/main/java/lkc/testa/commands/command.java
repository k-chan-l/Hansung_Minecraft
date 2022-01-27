package lkc.testa.commands;



import lkc.testb.TestB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.Bukkit.getServer;

public class command implements CommandExecutor {
    public TestB testB;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("testBMethod"))
            return false;
        if (!(sender instanceof Player))
            return false;
        getServer().getLogger().info("[Test Plugin A]테스트");
        testB = (TestB) Bukkit.getPluginManager().getPlugin("TestB");




        return true;
    }
}
