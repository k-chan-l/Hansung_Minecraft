package lkc.holographic.commands;

import lkc.holographic.blocks.Userdata;
import lkc.holographic.blocks.controlRedStoneLamp;
import lkc.holographic.eventlisteners.lungrowValueUpdateEventListener;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getLogger;

public class tpRedStone implements CommandExecutor {
    lungrowValueUpdateEventListener LVUEL;

    public tpRedStone (lungrowValueUpdateEventListener listener){
        LVUEL = listener;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("only Player can use command");
            return false;
        }
        if (!label.equals("tpblock")) {
            return false;
        }
        if (args.length > 0) {
            sender.sendMessage("too many args");
            return false;
        }
        sender.sendMessage("TP block");
        getLogger().info("TP block");
        Userdata userdata = LVUEL.getUserdata((Player) sender);
        Location Block = userdata.getLoc();
        Player player = (Player) sender;
        player.teleport(Block);
        return true;
    }
}
