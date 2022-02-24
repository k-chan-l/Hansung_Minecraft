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

import java.time.LocalTime;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class controlRedStoneLamp {
    private int preinhale = 0;
    private int preexhale = 0;
    private int inhale = 0;
    private int exhale = 0;
    private int inhaleCount = 4;
    private int waitCount = 2;
    private int exhaleCount = 4;
    private int clearCount = 3;
    private int blockcount;
    private Location loc;
    private Location btnloc;
    private Plugin plugin;
    private Hologram hologram;
    private TextLine textLine0;
    private TextLine textLine1;
    private TextLine textLine2;
    private TextLine textLine3;
    private String userName;
    private boolean inhaleflag = false;
    private boolean inhaleclearflag = false;
    private boolean waitflag = false;
    private boolean waitclearflag = false;
    private boolean exhaleflag = false;
    private boolean exhaleclearflag = false;
    private boolean buildblockflag = false;
    private LocalTime standard;

    public controlRedStoneLamp(Location location, Location buttonloc, Location holo, Plugin plugin, String name){
        loc = location;
        btnloc = buttonloc;
        userName = name;
        this.plugin = plugin;
        fillBlockAir(loc);
        blockcount = 0;
        hologram = HologramsAPI.createHologram(this.plugin, holo);
        textLine0 = hologram.appendTextLine(ChatColor.BOLD + "" + ChatColor.GOLD + userName);
        textLine1 = hologram.appendTextLine(ChatColor.BOLD + "" + ChatColor.GREEN +"Lungrow 디바이스를 켜고");
        textLine2 = hologram.appendTextLine(ChatColor.BOLD + "" + ChatColor.GREEN + "어플리케이션을 실행하면");
        textLine3 = hologram.appendTextLine(ChatColor.BOLD + "" + ChatColor.GREEN + "시작됩니다.");
    }

    public void updateLungrowValue(int inhale, int exhale){
//        preexhale = this.exhale;
//        preinhale = this.inhale;
        this.inhale = inhale;
        this.exhale = exhale;
        textLine1.setText(ChatColor.BOLD + "" + ChatColor.BLUE + "inhale : "+ this.inhale + " exhale : "+ this.exhale);
    }

    public void updateBlock(){
        measureInhale();
        waittwoseconds();
        measureExhale();
        buildblock();
    }

    public void resetBlock(Block block, Player player){
        Location location = block.getLocation();
        if(!(location.getX() == btnloc.getX() && location.getY() == btnloc.getY() && location.getZ() == btnloc.getZ() && location.getBlock().getType() == Material.STONE_BUTTON)) return;
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "click event 호출");
        if(blockcount < 10) {
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.RED + "아직 블럭을 초기화 할 수 없습니다.");
            return;
        }
        removeBlock();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
    }

    public void removeBlock(){
        getLogger().info("Reset Block");
        fillBlockAir(loc);
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
        if (inhaleclearflag) return; // flase false
        if(blockcount == 10) {
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.GREEN + "다시 불고 싶다면");
            textLine3.setText(ChatColor.BOLD + "" + ChatColor.GREEN + "버튼을 눌러 초기화 하세요");
            return;
        }
        if (!inhaleflag) {
            inhaleflag = true;
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "빨아들이세요");
            inhaleupdate();
        }
        if (LocalTime.now().toSecondOfDay() - standard.toSecondOfDay() >= 1)
            inhaleupdate();
    }

    private void waittwoseconds(){
        if (!inhaleclearflag || waitclearflag)return;// true false flase
        if (!waitflag){
            waitflag = true;
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "숨을 참으세요");
            waitupdate();
        }
        if (LocalTime.now().toSecondOfDay() - standard.toSecondOfDay() >= 1)
            waitupdate();
    }

    private void measureExhale(){
        if (!waitclearflag || exhaleclearflag) return; // true flase false
        if(!exhaleflag) {
            exhaleflag = true;
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "내쉬세요");
            exhaleupdate();
        }
        if (LocalTime.now().toSecondOfDay() - standard.toSecondOfDay() >= 1)
            exhaleupdate();
    }

    public void makeRedstone(){
        if(blockcount == 10) {
            getLogger().info("Block reset needed");
            return;
        }
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        blockcount = blockcount + 1;
        location.setY(location.getY()+blockcount);
        location.getBlock().setType(Material.REDSTONE_BLOCK);
    }

    private void buildblock(){
        if(!inhaleclearflag || !waitclearflag || !exhaleclearflag)// true true true
            return;
        if(!buildblockflag)
        {
            buildblockflag = true;
            makeRedstone();
            textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "기다리세요");
            clearupdate();
        }
        if (LocalTime.now().toSecondOfDay() - standard.toSecondOfDay() >= 1)
            clearupdate();

    }
    public void destroyhologram(){
        hologram.delete();
    }

    private void inhaleupdate(){

        textLine3.setText(ChatColor.BOLD + "" + inhaleCount);
        textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "빨아들이세요");
        if (preinhale >= inhale){
            //홀로그램 숫자 x 다시 초기화
            if(inhaleCount != 4) textLine3.setText(ChatColor.BOLD + "" + ChatColor.RED + "다시 한번 빨아들이세요");
            inhaleCount = 4;
            standard = LocalTime.now();
            getLogger().info(ChatColor.YELLOW + userName + "inhale fail preinhale : " + preinhale + " exhale : " + inhale);
            return;
        }
        if(inhaleCount == 0){
            getLogger().info("inhale clear");
            inhaleclearflag = true;
            inhaleflag = false;
            inhaleCount = 4;
            haleValueUpdate();
            return;
        }
        inhaleCount = inhaleCount-1;
        haleValueUpdate();
        standard = LocalTime.now();
    }
    private void waitupdate(){
        textLine3.setText(ChatColor.BOLD + "" +  waitCount);
        textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "숨을 참으세요");
        if (waitCount == 0) {
            getLogger().info("wait clear");
            waitflag = false;
            waitclearflag = true;
            waitCount = 2;
            haleValueUpdate();
            return;
        }
        waitCount = waitCount - 1;
        haleValueUpdate();
        standard = LocalTime.now();
    }
    private void exhaleupdate(){
        textLine3.setText(ChatColor.BOLD + "" + exhaleCount);
        textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "내쉬세요");
        if (preexhale >= exhale) {
            //홀로그램 숫자 x 다시 초기화
            if (exhaleCount != 4) textLine3.setText(ChatColor.BOLD + "" + ChatColor.RED + "다시 한번 숨을 내쉬세요");
            exhaleCount = 4;
            standard = LocalTime.now();
            getLogger().info(ChatColor.YELLOW + userName + ": exhale fail preexhale : " + preexhale + " exhale : " + exhale);
            return;
        }
        if (exhaleCount == 0) {
            getLogger().info("exhale clear");
            exhaleclearflag = true;
            exhaleflag = false;
            exhaleCount = 4;
            haleValueUpdate();
            return;
        }
        exhaleCount = exhaleCount - 1;
        haleValueUpdate();
        standard = LocalTime.now();
    }
    private void clearupdate(){
        textLine3.setText(ChatColor.BOLD + "" + clearCount);
        textLine2.setText(ChatColor.BOLD + "" + ChatColor.YELLOW + "기다리세요");
        if (clearCount == 0) {
            getLogger().info("wait end");
            inhaleclearflag = false;
            waitclearflag = false;
            exhaleclearflag = false;
            buildblockflag = false;
            clearCount = 3;
            haleValueUpdate();
            return;
        }
        clearCount = clearCount - 1;
        haleValueUpdate();
        standard = LocalTime.now();
    }

    private void haleValueUpdate(){
        preexhale = exhale;
        preinhale = inhale;
    }

}
