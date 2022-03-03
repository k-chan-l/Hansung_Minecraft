package lkc.checkpath.Users;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class User {

    private JsonObject jsonObject;
    private BukkitScheduler scheduler;
    private Player player;
    private JsonArray path;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public BukkitScheduler getScheduler() {
        return scheduler;
    }

    public Player getPlayer() {
        return player;
    }

    public JsonArray getPath() {
        return path;
    }

    public User(JsonObject jsonObject, BukkitScheduler scheduler, Player player, JsonArray path) {
        this.jsonObject = jsonObject;
        this.scheduler = scheduler;
        this.player = player;
        this.path = path;
    }
}
