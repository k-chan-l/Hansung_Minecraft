package lkc.holographic.commands;

import lkc.holographic.blocks.Userdata;
import lkc.holographic.blocks.controlRedStoneLamp;
import lkc.holographic.eventlisteners.lungrowValueUpdateEventListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getLogger;

public class buildblock implements CommandExecutor {
    lungrowValueUpdateEventListener LVUEL;

    public buildblock (lungrowValueUpdateEventListener listener){
        LVUEL = listener;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("only Player can use command");
            return false;
        }
        if (!label.equals("buildblock")) {
            return false;
        }
        if (args.length > 0) {
            sender.sendMessage("too many args");
            return false;
        }
        sender.sendMessage("Build block");
        getLogger().info("Build block");
        Userdata userdata = LVUEL.getUserdata((Player) sender);
        controlRedStoneLamp CRSL = userdata.getControlRedStoneLamp();
        CRSL.makeRedstone();
        return true;
    }
}
