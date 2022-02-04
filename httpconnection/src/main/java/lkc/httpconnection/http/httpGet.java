package lkc.httpconnection.http;

import lkc.httpconnection.files.ConfigureFile;
import lkc.httpconnection.httpconnection;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class httpGet {
    private StringJoiner joiner;
    private String subject;
    private static httpconnection httpCon;

    public httpGet(@NotNull String[] args, @NotNull CommandSender sender, httpconnection httpConnection){
        httpCon = httpConnection;
        joiner = new StringJoiner(" ");
        for (int i = 1; i < args.length; i++) {
            joiner.add(args[i]);
        }
        subject = args[0];
        sender.sendMessage(ChatColor.GREEN + "Sending payload " + joiner.toString() + " to topic " + args[0]);
    }


    public void Gethttp(CommandSender sender) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://738x8rfm12.execute-api.ap-northeast-2.amazonaws.com/default/lungrow-minecraft-get-info?"+subject+ "="+ joiner.toString())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "http failure" + e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonstring;
                if (!response.isSuccessful()) {
                    ConfigureFile.get().addDefault("jsonQuerry", "");
                    ConfigureFile.get().options().copyDefaults(true);
                    ConfigureFile.save();
                    ConfigureFile.reload();
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    ResponseBody responseBody = response.body();
                    jsonstring = responseBody.string();
                    sender.sendMessage(ChatColor.GREEN + "http success");
                    sender.sendMessage(ChatColor.GREEN + "response:" + jsonstring);
                    ConfigureFile.get().addDefault("jsonQuerry", jsonstring);
                    ConfigureFile.get().options().copyDefaults(true);
                    ConfigureFile.save();
                    ConfigureFile.reload();

                }
            }
        });
        return;
    }
}
