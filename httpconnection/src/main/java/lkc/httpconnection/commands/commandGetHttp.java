package lkc.httpconnection.commands;

import com.google.gson.JsonObject;
import lkc.lungrow.events.JsonObjectParsingEvent;
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
        if(!cmd.getName().equalsIgnoreCase("gethttp"))
            return false;
        if (!(sender instanceof Player))
            return false;
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "The gethttp command needs a topic and a payload as parameters!");
            return false;
        }
        JsonObjectParsingEvent JOPEvent = new JsonObjectParsingEvent();
        httpGet httpget = new httpGet(args, sender, httpCon);
        httpget.Gethttp(sender);
        JsonObject jsonObject = stringToJsonFormatter.JsonParsing(ConfigureFile.get().getString("jsonQuerry"));
        if(jsonObject == null){
            Bukkit.getLogger().severe("Invalid jsonObject");
            JOPEvent.setFlag(false);
            Bukkit.getScheduler().runTask(httpCon, () -> Bukkit.getServer().getPluginManager().callEvent(JOPEvent));
            return false;
        }
        else {
            JOPEvent.setFlag(true);
            JOPEvent.setJsonObject(jsonObject);
            Bukkit.getScheduler().runTask(httpCon, () -> Bukkit.getServer().getPluginManager().callEvent(JOPEvent));
            return true;
        }
    }
}
