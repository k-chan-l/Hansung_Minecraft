package lkc.npcplugin;

import lkc.npcplugin.commands.NPCCheckAndSpawn;
import lkc.npcplugin.events.PlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class NPCPlugin extends JavaPlugin {
    private static NPCPlugin instance;

    @Override
    public void onEnable() {
        NPCPlugin.instance = this;
//        NPCCheckAndSpawn settingnpc = new NPCCheckAndSpawn();//npc 확인후 소환
//        getCommand("settingnpc").setExecutor(settingnpc);//npc 확인후 소환 등록 명령어
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);//  PlayerJoinEvent
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[NPCPlugin]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[NPCPlugin]: Plugin is disabled!");
    }
    public static NPCPlugin getInstance() {
        return NPCPlugin.instance;
    }
}
