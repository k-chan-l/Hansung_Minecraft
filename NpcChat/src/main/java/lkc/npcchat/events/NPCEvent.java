package lkc.npcchat.events;

import lkc.npcchat.NpcChat;
import lkc.npcchat.chats.ChatBubbles;
import lkc.npcchat.chats.ChatBuffer;
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

        disableChatWindow = getServer().getPluginManager().getPlugin("NpcChat").getConfig().getBoolean("disableChatWindow");
        bubbles = ((NpcChat) getServer().getPluginManager().getPlugin("NpcChat")).getBubbles();
        buffer = ((NpcChat) getServer().getPluginManager().getPlugin("NpcChat")).getbuffer();

        if (!e.isCancelled())
        {
            buffer.receiveChat(e.getNPC().getEntity(), e.getContext().getMessage());

            if(disableChatWindow)
                e.setCancelled(true);
        }
    }
}
