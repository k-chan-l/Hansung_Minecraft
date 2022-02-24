package lkc.httpconnection.commands;

import lkc.httpconnection.http.httpGet;
import lkc.httpconnection.httpconnection;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getLogger;

public class commandPostHttp implements CommandExecutor {

    private static httpconnection httpCon;

    public commandPostHttp(httpconnection httpConnection) {
        httpCon = httpConnection;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // label 명령어
        // args 인자
        if (!label.equals("posthttp"))
            return false;
        if(!(sender instanceof Player))
            return false;
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "The posthttp command need no parameters");
            return false;
        }
        httpGet httpget = new httpGet("playerName", (Player) sender, httpCon);
        httpget.PosthttpCommand((Player) sender);




        return true;
    }
}
