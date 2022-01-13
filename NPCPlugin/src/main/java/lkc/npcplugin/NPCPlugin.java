package lkc.npcplugin;

import lkc.npcplugin.chats.ChatBubbles;
import lkc.npcplugin.chats.ChatBuffer;
import lkc.npcplugin.commands.NPCCheckAndSpawn;
import lkc.npcplugin.events.NPCEvent;
import lkc.npcplugin.events.PlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class NPCPlugin extends JavaPlugin {
    private static NPCPlugin instance;
    private boolean disableChatWindow;
    private ChatBuffer buffer;
    public ChatBubbles bubbles;

    @Override
    public void onEnable() {
        NPCPlugin.instance = this;
        //-------------------npc 대화 관련 코드 ----------------------------
        saveDefaultConfig();
        disableChatWindow = getConfig().getBoolean("disableChatWindow");
        bubbles = new ChatBubbles(this);
        buffer = new ChatBuffer(this);
        getServer().getPluginManager().registerEvents(new NPCEvent(), this);
        //----------------------------------------------------------------


        NPCCheckAndSpawn settingnpc = new NPCCheckAndSpawn();//npc 확인후 소환
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);//  PlayerJoinEvent
        getCommand("settingnpc").setExecutor(settingnpc);//npc 확인후 소환 등록 명령어
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[NPCPlugin]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[NPCPlugin]: Plugin is disabled!");
    }
    public ChatBuffer getbuffer(){
        return buffer;
    }

    public ChatBubbles getBubbles(){
        return bubbles;
    }

    public static NPCPlugin getInstance() {
        return NPCPlugin.instance;
    }
}
