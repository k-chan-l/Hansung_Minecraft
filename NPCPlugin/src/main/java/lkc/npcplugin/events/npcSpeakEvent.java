package lkc.npcplugin.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class npcSpeakEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Entity npc;
    String text;

    public Entity getNpc() {
        return npc;
    }

    public String getText() {
        return text;
    }

    public npcSpeakEvent(Entity entity, String string){
        npc = entity;
        text = string;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
