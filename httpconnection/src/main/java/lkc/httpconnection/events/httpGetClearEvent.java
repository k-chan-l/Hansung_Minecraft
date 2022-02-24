package lkc.httpconnection.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class httpGetClearEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final boolean flag;
    private final String message;
    private boolean command = false;

    private boolean post = false;

    private Player player;

    public httpGetClearEvent(String string, boolean state){
        message = string;
        flag = state;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public boolean isCommand() {
        return command;
    }

    public void setCommand(boolean command) {
        this.command = command;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
