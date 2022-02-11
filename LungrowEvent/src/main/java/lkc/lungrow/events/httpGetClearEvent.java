package lkc.lungrow.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class httpGetClearEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final boolean flag;
    private final String message;

    public httpGetClearEvent(String string, boolean state){
        message = string;
        flag = state;
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
}
