package lkc.httpconnection.http;

import com.google.gson.JsonObject;
import lkc.httpconnection.files.ConfigureFile;
import lkc.httpconnection.httpconnection;
import okhttp3.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.StringJoiner;

public class httpPut {
    private StringJoiner joiner;
    private String subject;
    private static httpconnection httpCon;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public httpPut(@NotNull httpconnection httpConnection){
        httpCon = httpConnection;
    }


    public void Puthttp(@NotNull String JsonString) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonString, JSON);
        Request request = new Request.Builder()
                .url("https://738x8rfm12.execute-api.ap-northeast-2.amazonaws.com/default/lungrow-minecraft-get-info?" + subject + "=" + joiner.toString())
                .put(body)
                .build();


//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                sender.sendMessage(ChatColor.RED + "http failure" + e);
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                String jsonstring;
//                if (!response.isSuccessful()) {
//                    ConfigureFile.get().addDefault("jsonQuerry", "");
//                    ConfigureFile.get().options().copyDefaults(true);
//                    ConfigureFile.save();
//                    ConfigureFile.reload();
//                    throw new IOException("Unexpected code " + response);
//                } else {
//                    // do something wih the result
//                    ResponseBody responseBody = response.body();
//                    jsonstring = responseBody.string();
//                    sender.sendMessage(ChatColor.GREEN + "http success");
//                    sender.sendMessage(ChatColor.GREEN + "response:" + jsonstring);
//                    ConfigureFile.get().addDefault("jsonQuerry", jsonstring);
//                    ConfigureFile.get().options().copyDefaults(true);
//                    ConfigureFile.save();
//                    ConfigureFile.reload();
//
//                }
//            }
//        });
    }
}
