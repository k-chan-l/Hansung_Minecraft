package lkc.httpconnection.http;

import com.google.gson.JsonObject;
import lkc.httpconnection.files.ConfigureFile;
import lkc.httpconnection.httpconnection;
import okhttp3.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.StringJoiner;

import static org.bukkit.Bukkit.getLogger;

public class httpPost {
    private String subject;
    private static httpconnection httpCon;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public httpPost(@NotNull httpconnection httpConnection){
        httpCon = httpConnection;
    }


    public void Posthttp(@NotNull JsonObject jsonObject) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://kv2s3duleh.execute-api.ap-northeast-2.amazonaws.com/default/lungrow-minecraft-create-data")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getLogger().severe("DB Update Err");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonstring;
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    ResponseBody responseBody = response.body();
                    jsonstring = responseBody.string();
                    getLogger().info(jsonstring);
                }
            }
        });
    }

    public void PosthttpCommand(@NotNull JsonObject jsonObject, Player sender) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://kv2s3duleh.execute-api.ap-northeast-2.amazonaws.com/default/lungrow-minecraft-create-data")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getLogger().severe("DB Update Err");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonstring;
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    ResponseBody responseBody = response.body();
                    jsonstring = responseBody.string();
                    sender.sendMessage(ChatColor.GREEN + "http success");
                    sender.sendMessage(ChatColor.GREEN + "response:" + jsonstring);
                    getLogger().info(jsonstring);
                }
            }
        });
    }
}
