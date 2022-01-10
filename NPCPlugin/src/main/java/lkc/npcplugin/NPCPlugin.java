package lkc.npcplugin;

import lkc.npcplugin.commands.NPCCheckAndSpawn;
import lkc.npcplugin.events.PlayerEvent;
import lkc.npcplugin.traits.npctraits;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class NPCPlugin extends JavaPlugin {
    boolean npccheck = false;
    Iterable<NPC> npcs;

    @Override
    public void onEnable() {
        // Plugin startup logic
//        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
//            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
//        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(npctraits.class).withName("mytraitname"));
        NPCCheckAndSpawn settingnpc = new NPCCheckAndSpawn();//npc 확인후 소환
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);//  PlayerJoinEvent
        getCommand("settingnpc").setExecutor(settingnpc);//npc 확인후 소환 등록
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[NPCPlugin]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[NPCPlugin]: Plugin is disabled!");
    }
}
