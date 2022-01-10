package lkc.npcplugin.traits;

import lkc.npcplugin.NPCPlugin;
import net.citizensnpcs.Settings;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class npctraits extends Trait {
    public npctraits() {
        super("mytraitname");
        plugin = JavaPlugin.getPlugin(NPCPlugin.class);
    }

    NPCPlugin plugin = null;

    boolean SomeSetting = false;
    private String itemInHandPattern = "default";
    private int delay = -1;
    private double range = Settings.Setting.DEFAULT_TALK_CLOSE_RANGE.asDouble();
    private boolean randomTalker = Settings.Setting.DEFAULT_RANDOM_TALKER.asBoolean();
    private boolean talkClose = Settings.Setting.DEFAULT_TALK_CLOSE.asBoolean();
    private boolean realisticLooker = Settings.Setting.DEFAULT_REALISTIC_LOOKING.asBoolean();
    private final List<String> text = new ArrayList<String>();


    // see the 'Persistence API' section
    @Persist("mysettingname") boolean automaticallyPersistedSetting = false;

    // Here you should load up any values you have previously saved (optional).
    // This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
    // This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
    // This is called BEFORE onSpawn, npc.getEntity() will return null.
    public void load(DataKey key) {
        SomeSetting = key.getBoolean("SomeSetting", false);
        text.clear();
        for (DataKey sub : key.getRelative("text").getIntegerSubKeys()) {
            text.add(sub.getString(""));
        }
        if (text.isEmpty()) {
            populateDefaultText();
        }

        talkClose = key.getBoolean("talk-close", talkClose);
        realisticLooker = key.getBoolean("realistic-looking", realisticLooker);
        randomTalker = key.getBoolean("random-talker", randomTalker);
        range = key.getDouble("range", range);
        delay = key.getInt("delay", delay);
        itemInHandPattern = key.getString("talkitem", itemInHandPattern);
    }

    // Save settings for this NPC (optional). These values will be persisted to the Citizens saves file
    public void save(DataKey key) {
        key.setBoolean("SomeSetting",SomeSetting);
        key.setInt("delay", delay);
        key.setBoolean("talk-close", talkClose);
        key.setBoolean("random-talker", randomTalker);
        key.setBoolean("realistic-looking", realisticLooker);
        key.setDouble("range", range);
        key.setString("talkitem", itemInHandPattern);
        key.removeKey("text");
        for (int i = 0; i < text.size(); i++) {
            key.setString("text." + String.valueOf(i), text.get(i));
        }
    }

    private void populateDefaultText() {
        text.addAll(Settings.Setting.DEFAULT_TEXT.asList());
    }

    public void add(String string) {
        text.add(string);
    }

    // An example event handler. All traits will be registered automatically as Bukkit Listeners.
    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        //Handle a click on a NPC. The event has a getNPC() method.
        //Be sure to check event.getNPC() == this.getNPC() so you only handle clicks on this NPC!

    }


    // Called every tick
    @Override
    public void run() {
    }

    //Run code when your trait is attached to a NPC.
    //This is called BEFORE onSpawn, so npc.getEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
    @Override
    public void onAttach() {
        plugin.getServer().getLogger().info(npc.getName() + "has been assigned MyTrait!");
//        load();
    }

    // Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getEntity() is still valid.
    @Override
    public void onDespawn() {
    }

    //Run code when the NPC is spawned. Note that npc.getEntity() will be null until this method is called.
    //This is called AFTER onAttach and AFTER Load when the server is started.
    @Override
    public void onSpawn() {

    }

    //run code when the NPC is removed. Use this to tear down any repeating tasks.
    @Override
    public void onRemove() {
    }

}