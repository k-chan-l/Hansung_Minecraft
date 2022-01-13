package lkc.npcplugin.events;

import lkc.npcplugin.NPCPlugin;
import lkc.npcplugin.chats.ChatBubbles;
import lkc.npcplugin.chats.ChatBuffer;
import net.citizensnpcs.api.ai.speech.event.NPCSpeechEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class NPCEvent implements Listener {

    private boolean disableChatWindow;
    private ChatBuffer buffer;
    public ChatBubbles bubbles;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onNPCSpeech(NPCSpeechEvent e)
    {

        disableChatWindow = getServer().getPluginManager().getPlugin("NPCPlugin").getConfig().getBoolean("disableChatWindow");
        bubbles = ((NPCPlugin) getServer().getPluginManager().getPlugin("NPCPlugin")).getBubbles();
        buffer = ((NPCPlugin) getServer().getPluginManager().getPlugin("NPCPlugin")).getbuffer();

        if (!e.isCancelled())
        {
            buffer.receiveChat(e.getNPC().getEntity(), e.getContext().getMessage());

            if(disableChatWindow)
                e.setCancelled(true);
        }
    }
}
