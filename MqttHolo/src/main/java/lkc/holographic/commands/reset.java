package lkc.holographic.commands;

import lkc.holographic.blocks.Userdata;
import lkc.holographic.blocks.controlRedStoneLamp;
import lkc.holographic.eventlisteners.lungrowValueUpdateEventListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class reset implements CommandExecutor {
    lungrowValueUpdateEventListener LVUEL;

    public reset (lungrowValueUpdateEventListener listener){
        LVUEL = listener;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("only Player can use command");
            return false;
        }
        if (!label.equals("reset")) {
            return false;
        }
        if (args.length > 0) {
            sender.sendMessage("too many args");
            return false;
        }
        sender.sendMessage("Reset block");
        Userdata userdata = LVUEL.getUserdata((Player) sender);
        controlRedStoneLamp CRSL = userdata.getControlRedStoneLamp();
        CRSL.removeBlock();
        return true;
    }
}
