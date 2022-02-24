package lkc.httpconnection.commands;

import com.google.gson.JsonObject;
import lkc.httpconnection.files.ConfigureFile;
import lkc.httpconnection.httpconnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lkc.httpconnection.http.httpGet;
import lkc.httpconnection.formatters.stringToJsonFormatter;



public class commandGetHttp implements CommandExecutor {
    private static httpconnection httpCon;

    public commandGetHttp(httpconnection httpConnection) {
        httpCon = httpConnection;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return false;
        if (!label.equals("gethttp"))
            return false;
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "The gethttp command need no parameters");
            return false;
        }
        httpGet httpget = new httpGet("playerName", (Player) sender, httpCon);
        httpget.GethttpCommand((Player) sender);
        return true;
    }
}
