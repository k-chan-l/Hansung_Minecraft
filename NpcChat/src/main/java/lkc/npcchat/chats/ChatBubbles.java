package lkc.npcchat.chats;

import lkc.npcchat.NpcChat;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class ChatBubbles
{
    private int handicapChars;
    private int readSpeed;
    private String chatFormat;


    // constructor
    public ChatBubbles(NpcChat plugin)
    {
        handicapChars = plugin.getConfig().getInt("handicapChars");
        readSpeed = plugin.getConfig().getInt("readSpeed");
        chatFormat = plugin.getConfig().getString("chatFormat").replaceAll("(.)", "\u00A7$1");
    }

    // recieve chat to be displayed, return display duration in ticks so previous method can schedule next
    int receiveMessage(Entity entity, String entityId, String chat)
    {
        // prepare chat message and empty bubble
        String[] chatLines = chat.split("\n");
        new ArrayList<LivingEntity>();

        // calculate bubble duration, 1200 = ticks per minute, to convert readSpeed to ticks
        int duration = (chat.length()+(handicapChars*chatLines.length))*1200/readSpeed;
        Location spawnPoint = entity.getLocation();
        spawnPoint.setY(0);

        // spawn name tags from bottom to top
        Entity vehicle = entity;
        vehicle = spawnNameTag(vehicle, "", spawnPoint, duration, false);
        for (int i = chatLines.length -1 ; i >= 0 ; --i)
            vehicle = spawnNameTag(vehicle, chatLines[i], spawnPoint, duration, true);
        return duration;
    }

    // spawn a nameplate and return it to caller so it can stack together
    private AreaEffectCloud spawnNameTag(Entity vehicle, String text, Location spawnPoint, int duration, boolean visible)
    {
        // spawn name tag away from player in same chunk, then set invisible
        AreaEffectCloud nameTag = (AreaEffectCloud) spawnPoint.getWorld().spawnEntity(spawnPoint, EntityType.AREA_EFFECT_CLOUD);
        nameTag.setParticle(Particle.TOWN_AURA); // ITEM_TAKE was deprecated so i found mycelium (TOWN_AURA) has the tiniest particle
        nameTag.setRadius(0);

        // mount over vehicle and set name
        vehicle.addPassenger(nameTag);
        nameTag.setCustomName(chatFormat+text);
        nameTag.setCustomNameVisible(visible);

        // set duration and return
        nameTag.setWaitTime(0);
        nameTag.setDuration(duration);
        return nameTag;
    }

}
