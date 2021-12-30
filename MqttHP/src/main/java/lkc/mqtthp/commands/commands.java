package lkc.mqtthp.commands;

import lkc.mqtthp.HealthControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class commands implements CommandExecutor {
    Player player;
    HealthControl HC;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        player = (Player)sender;
        HC = new HealthControl(player);
        return false;
    }
}
