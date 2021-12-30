package lkc.mqtthp;

import org.bukkit.entity.Player;
public class HealthControl{
    static Player player;
    static double damage = 2;

    public HealthControl(Player user){
        player = user;
    }

    public void Hit(){
        player.damage(damage);
    }


}
