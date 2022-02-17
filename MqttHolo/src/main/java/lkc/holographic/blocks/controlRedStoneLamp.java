package lkc.holographic.blocks;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class controlRedStoneLamp {
    private int preinhale = 0;
    private int preexhale = 0;
    private int inhale = 0;
    private int exhale = 0;
    private int blockcount;
    private Location loc;
    private Location btnloc;
    private Plugin plugin;
    private Hologram hologram;
    private TextLine textLine1;
    private TextLine textLine2;
    private TextLine textLine3;
    private boolean inhaleflag = false;
    private boolean inhaleclearflag = false;
    private boolean waitflag = false;
    private boolean waitclearflag = false;
    private boolean exhaleflag = false;
    private boolean exhaleclearflag = false;
    private boolean buildblockflag = false;

    public controlRedStoneLamp(Location location, Location buttonloc, Location holo, Plugin plugin){
        loc = location;
        btnloc = buttonloc;
        this.plugin = plugin;
        fillBlockAir(loc);
        blockcount = 0;
        hologram = HologramsAPI.createHologram(this.plugin, holo);
        textLine1 = hologram.appendTextLine("Lungrow 디바이스를 켜고");
        textLine2 = hologram.appendTextLine("어플리케이션을 실행하면");
        textLine3 = hologram.appendTextLine("시작됩니다.");
    }

    public void updateLungrowValue(int inhale, int exhale){
        this.preinhale = this.inhale;
        this.preexhale = this.exhale;
        this.inhale = inhale;
        this.exhale = exhale;
        textLine1.setText("inhale : "+ this.inhale + " exhale : "+ this.exhale);
    }

    public void updateBlock(){
        measureInhale();
        waitsixseconds();
        measureExhale();
        buildblock();
    }

    public void resetBlock(Block block, Player player){
        Location location = block.getLocation();
        if(!(location.getX() == btnloc.getX() && location.getY() == btnloc.getY() && location.getZ() == btnloc.getZ() && location.getBlock().getType() == Material.STONE_BUTTON)) return;
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "click event 호출");
        if(blockcount < 10) {
            textLine2.setText("아직 블럭을 초기화 할 수 없습니다.");
            return;
        }
        getLogger().info("Reset Block");
        fillBlockAir(loc);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
        blockcount = 0;
    }

    public void fillBlockAir(Location location){
        Location loc = new Location(location.getWorld(),location.getX(),location.getY(),location.getZ());
        for(int i = 0; i < 10; i++){
            loc.setY(loc.getY()+1);
            loc.getBlock().setType(Material.AIR);
        }
    }

    private void measureInhale(){
        if (inhaleflag || inhaleclearflag) return; // flase false
        if(blockcount == 10) {
            textLine2.setText("다시 불고 싶다면");
            textLine3.setText("버튼을 눌러 초기화 하세요");
            return;
        }
        inhaleflag = true;
        textLine2.setText("빨아들이세요");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            int i = 6;
            @Override
            public void run() {
                if(textLine3.getParent().isDeleted())
                    scheduler.cancelTasks(plugin);
                textLine3.setText(""+i);
                if (preinhale < inhale){
                    textLine2.setText("빨아들이세요");
                }
                else{
                    //홀로그램 숫자 x 다시 초기화
                    if(i != 6) textLine3.setText("다시 한번 빨아들이세요");
                    i = 6;
                    return;
                }
                if(i == 0){
                    getLogger().info("inhale clear");
                    inhaleclearflag = true;
                    inhaleflag = false;
                    scheduler.cancelTasks(plugin);
                }
                i = i-1;
            }
        }, 0L, 20L * 1);
    }

    private void waitsixseconds(){
        if (!inhaleclearflag || waitflag || waitclearflag)return;// true false flase
        waitflag = true;
        textLine2.setText("숨을 참으세요");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            int i = 6;
            @Override
            public void run() {
                if(textLine3.getParent().isDeleted())
                    scheduler.cancelTasks(plugin);
                textLine3.setText(""+i);
                if(i == 0){
                    getLogger().info("wait clear");
                    waitflag = false;
                    waitclearflag = true;
                    scheduler.cancelTasks(plugin);
                }
                i = i-1;
            }
        }, 0L, 20L * 1);

    }

    private void measureExhale(){
        if (!waitclearflag || exhaleflag || exhaleclearflag) return; // true flase false
        exhaleflag = true;
        textLine2.setText("내쉬세요");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            int i = 6;
            @Override
            public void run() {
                if(textLine3.getParent().isDeleted())
                    scheduler.cancelTasks(plugin);
                textLine3.setText(""+i);
                if (preexhale < exhale){
                    textLine2.setText("내쉬세요");
                }
                else{
                    //홀로그램 숫자 x 다시 초기화
                    if(i != 6) textLine3.setText("다시 한번 숨을 내쉬세요");
                    i = 6;
                    return;
                }
                if(i == 0){
                    getLogger().info("exhale clear");
                    exhaleclearflag = true;
                    exhaleflag = false;
                    scheduler.cancelTasks(plugin);
                }
                i = i-1;
            }
        }, 0L, 20L * 1);
    }

    private void buildblock(){
        if(!inhaleclearflag || !waitclearflag || !exhaleclearflag)// true true true
            return;
        if(buildblockflag)
            return;
        buildblockflag = true;
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        blockcount = blockcount + 1;
        location.setY(location.getY()+blockcount);
        location.getBlock().setType(Material.REDSTONE_BLOCK);
        textLine2.setText("기다리세요");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            int i = 3;
            @Override
            public void run() {
                if(textLine3.getParent().isDeleted())
                    scheduler.cancelTasks(plugin);
                textLine3.setText(""+i);
                if(i == 0){
                    getLogger().info("wait end");
                    inhaleclearflag = false;
                    waitclearflag = false;
                    exhaleclearflag = false;
                    buildblockflag = false;
                    scheduler.cancelTasks(plugin);
                }
                i = i-1;
            }
        }, 0L, 20L * 1);
    }
    public void destroyhologram(){
        hologram.delete();
    }
}
