package lkc.httpconnection.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lkc.httpconnection.http.httpGet;


public class commandGetHttp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!cmd.getName().equalsIgnoreCase("gethttp"))
            return false;
        if (!(sender instanceof Player))
            return false;
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "The gethttp command needs a topic and a payload as parameters!");
            return false;
        }
        httpGet httpget = new httpGet(args, sender);
        httpget.Gethttp(sender);
        return true;
    }
}
