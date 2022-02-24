package lkc.holographic.blocks;

import org.bukkit.Location;

public class Userdata {

    private Location loc;
    private Location buttonloc;
    private Location hologramloc;
    private controlRedStoneLamp controlRedStoneLamp;

    public Userdata(Location location, Location button, Location hologram, controlRedStoneLamp CRSL){
        loc = location;
        buttonloc = button;
        hologramloc = hologram;
        controlRedStoneLamp = CRSL;
    }

    public Location getLoc() {
        return loc;
    }


    public Location getButtonloc() {
        return buttonloc;
    }

    public Location getHologramloc() {
        return hologramloc;
    }

    public  controlRedStoneLamp getControlRedStoneLamp() {
        return controlRedStoneLamp;
    }
}
