package lkc.mqttlungrow.eventlisteners;

import lkc.mqttlungrow.blocks.controlRedStoneLamp;
import lkc.mqttlungrow.events.lungrowVauleUpdateEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class lungrowValueUpdateEventListener implements Listener {

    static Location loc = new Location(getServer().getWorld("world"),-309.0, 64.0, -39.0);// 블록 좌표 바꾸기
    static controlRedStoneLamp controlRedStoneLamp = new controlRedStoneLamp(loc);

    @EventHandler
    public void onLungrowValueUpdateEvent(lungrowVauleUpdateEvent event){
        controlRedStoneLamp.updateLungrowValue(event.getInhale(), event.getExhale());
        controlRedStoneLamp.updateBlock();

    }

}
