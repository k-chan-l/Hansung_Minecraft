package lkc.npcchat;

import lkc.npcchat.chats.ChatBubbles;
import lkc.npcchat.chats.ChatBuffer;
import lkc.npcchat.events.NPCEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class NpcChat extends JavaPlugin {

    private boolean disableChatWindow;
    private ChatBuffer buffer;
    public ChatBubbles bubbles;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //-------------------npc 대화 관련 코드 ----------------------------
        saveDefaultConfig();
        disableChatWindow = getConfig().getBoolean("disableChatWindow");
        bubbles = new ChatBubbles(this);
        buffer = new ChatBuffer(this);
        getServer().getPluginManager().registerEvents(new NPCEvent(), this);
        //----------------------------------------------------------------
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[NpcChat]: Plugin is Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[NpcChat]: Plugin is disabled!");
    }

    public ChatBuffer getbuffer(){
        return buffer;
    }

    public ChatBubbles getBubbles(){
        return bubbles;
    }

}
