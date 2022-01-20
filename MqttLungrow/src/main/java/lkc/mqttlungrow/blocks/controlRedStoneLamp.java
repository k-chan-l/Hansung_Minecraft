package lkc.mqttlungrow.blocks;

import org.bukkit.Location;
import org.bukkit.Material;

public class controlRedStoneLamp {
    private int inhale = 0;
    private int exhale = 0;
    private int interval = 1000;
    private Location loc;

    public controlRedStoneLamp(Location location){
        loc = location;
    }

    public void updateLungrowValue(int inhale, int exhale){
        this.inhale = inhale;
        this.exhale = exhale;
    }

    public void updateBlock(){
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        for(int i = 0; i < 5; i++){
            location.setY(location.getY()+1);
            if(i < Math.max(inhale/interval, exhale/interval))
                location.getBlock().setType(Material.REDSTONE_BLOCK);
            else location.getBlock().setType(Material.AIR);
        }
    }


}
