package lkc.lungrow.events;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class playerExitWithJsonObjectEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private JsonObject jsonObject;
    private Player player;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public Player getPlayer() {
        return player;
    }

    public playerExitWithJsonObjectEvent(JsonObject object, Player user){
        player = user;
        jsonObject = object;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
