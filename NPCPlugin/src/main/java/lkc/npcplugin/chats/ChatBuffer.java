package lkc.npcplugin.chats;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import lkc.npcplugin.NPCPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatBuffer
{
    // properties
    private NPCPlugin plugin;
    private int maxBubbleHeight;
    private int maxBubbleWidth;
    private int bubblesInterval;
    private Map<String, Queue<String>> chatQueue = new HashMap<String, Queue<String>>();

    // constructor
    public ChatBuffer(NPCPlugin plugin)
    {
        maxBubbleHeight = plugin.getConfig().getInt("maxBubbleHeight");
        maxBubbleWidth = plugin.getConfig().getInt("maxBubbleWidth");
        bubblesInterval = plugin.getConfig().getInt("bubblesInterval");
        this.plugin = plugin;
    }

    // wrap pre-trimmed chat and put in a player buffer
    public void receiveChat(Entity entity, String msg)
    {
        // most probable case, 1 line chat
        if (msg.length() <= maxBubbleWidth)
        {
            queueChat(entity, msg+"\n");
            return;
        }

        // longer chat, prepare word wrap
        msg = msg+" ";
        String chat = "";
        int delimPos;
        int lineCount = 0;

        // word wrap chat
        while (msg.length() > 0)
        {
            // search delimiter (space) before or after bubble width, or finishing
            delimPos = msg.lastIndexOf(' ', maxBubbleWidth);
            if (delimPos < 0) delimPos = msg.indexOf(' ', maxBubbleWidth);
            if (delimPos < 0) delimPos = msg.length();

            // pull sized text from chat message
            chat += msg.substring(0, delimPos);
            msg = msg.substring(delimPos+1);

            // line wrap chat in multiple messages if exceeds max lines
            ++lineCount;
            if (lineCount % maxBubbleHeight == 0 || msg.length() == 0)
            {
                queueChat(entity, chat+(msg.length() == 0 ? "\n" : "...\n"));
                chat = "";
            }
            else
                chat += "\n";
        }
    }

    // get word wrapped chat and queues in a player buffer, creates if not exists
    private void queueChat(Entity entity, String chat)
    {
        // if no player buffer yet, create it and schedule this message
        String entityId = ""+entity.getUniqueId();
        if (!chatQueue.containsKey(entityId))
        {
            chatQueue.put(entityId, new LinkedList<String>());
            scheduleMessageUpdate(entity, entityId, 0);
        }

        // queue this message
        chatQueue.get(entityId).add(chat);
    }

    // ... and this method will take previously queued chat messages by one for display
    private void scheduleMessageUpdate(Entity entity, String entityId, int timer)
    {
        //BukkitTask chatTimer = new BukkitRunnable()
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // player could be not chatting or offline, check him and collect his garbage
                if (chatQueue.get(entityId).size() < 1 || entity.isDead())
                {
                    chatQueue.remove(entityId);
                    return;
                }

                // pull queued message, send it to be displayed and get the duration, schedule the next message
                String chat = chatQueue.get(entityId).poll();
                int bubbleDuration = plugin.bubbles.receiveMessage(entity, entityId, chat);
                scheduleMessageUpdate(entity, entityId, bubbleDuration+bubblesInterval);
            }
        }.runTaskLater(plugin, timer);
    }

}