package lkc.mqttlungrow.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class lungrowVauleUpdateEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private String message;
    private int inhale = 0;
    private int exhale = 0;


    public lungrowVauleUpdateEvent(String message) {
        this.message = message;
        Bukkit.getServer().getLogger().info("lungrowEventEnable");
    }

    public String getMessage() {
        return message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void updateValue(int inhale, int exhale){
        this.inhale = inhale;
        this.exhale = exhale;
    }

    public int getInhale(){
        return inhale;
    }

    public int getExhale(){
        return exhale;
    }
}
