package lkc.npcplugin.calculatings;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import static org.bukkit.Bukkit.getServer;

public class CalculateDistance {
    public static boolean isDistanceOverNum(Location a, Location b, double num){
        if(DistanceCaculating(a,b) >= num)
            return true;
        else
            return false;
    }


    public static Double DistanceCaculating(Location a, Location b){
        double a_x = a.getX();
        double a_y = a.getY();
        double a_z = a.getZ();
        double b_x = b.getX();
        double b_y = b.getY();
        double b_z = b.getZ();
        double ans = Math.sqrt(Math.pow(a_x - b_x,2) + Math.pow(a_y - b_y,2) + Math.pow(a_z - b_z,2));
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "" + ans);
        return ans;
    }

}
