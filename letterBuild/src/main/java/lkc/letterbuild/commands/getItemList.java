package lkc.letterbuild.commands;

import lkc.letterbuild.consts.letter;
import lkc.letterbuild.consts.material;
import lkc.letterbuild.tools.locationChangeTool;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

import static lkc.letterbuild.consts.message.guide;
import static lkc.letterbuild.consts.message.help;
import static org.bukkit.Bukkit.getLogger;

public class getItemList implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean flag;
        Player player;
        if (sender instanceof Player)
            player = (Player) sender;
        else {
            getLogger().severe("only player can use command");
            return false;
        }
        if(!label.equals("letter"))
            return false;
        switch (args[0]) {
            case "help":
                flag = help(player);
                break;
            case "list":
                flag = list(player);
                break;
            case "build":
                flag = build(player, args);
                break;
            default:
                player.sendMessage(ChatColor.RED + "option error " + guide);
                flag = false;
        }
        return flag;
    }

    private boolean help(Player player){
        player.sendMessage(help);
        return true;
    }

    private boolean list(Player player) {
        Iterator<String> iterator = material.block.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()){
            sb.append(iterator.next() + " ");
        }
        player.sendMessage("블록의 종류 : " + sb.toString());
        return true;
    }

    private boolean build(Player player, @NotNull String[] args){
        int x, y, z;
        String letters, Main, Background = "";
        String[] letterArray;
        if(args.length < 6) {
            player.sendMessage("인자가 부족합니다.");
            return false;
        }
        if(args.length > 7) {
            player.sendMessage("인자가 너무 많습니다.");
            return false;
        }
        try {
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
            z = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e){
            player.sendMessage("x, y, z 자리에는 숫자만 입력할 수 있습니다.");
            return false;
        }
        letters = args[4];
        letterArray = letters.split("");
        Iterator<String> iterator = Arrays.stream(letterArray).iterator();
        while(iterator.hasNext())
        {
            if(!letter.letter.containsKey(iterator.next())) {
                player.sendMessage("해당 문자는 사용되지 않습니다.");
                return false;
            }
        }
        if (!material.block.containsKey(args[5])) {
            player.sendMessage("해당 메인 블록은 사용할 수 없는 블록입니다.");
            return false;
        }
        Main = args[5];
        if(args.length == 7)
        {
            if (!material.block.containsKey(args[6])) {
                player.sendMessage("해당 백그라운드 블록은 사용할 수 없는 블록입니다.");
                return false;
            }
            Background = args[6];
        }

        return buildLetter(player, x, y, z, letterArray, Main, Background);
    }

    private boolean buildLetter(Player player, int x, int y, int z, String[] letters, String Main, String Background){
        boolean[] blockFacing = facingCalc(player);
        int[] direction = blockDirection(blockFacing);
        locationChangeTool LCT =  new locationChangeTool(direction, blockFacing[1]);
        Iterator<String> iterator = Arrays.stream(letters).iterator();
        while (iterator.hasNext()){
            boolean[] letterFlag = letter.letter.get(iterator.next());
            for (int Row = 0; Row < 5; Row ++)
            {
                for (int Column = 0; Column < 5; Column ++)
                {
                    build(player, x, y, z, Main, Background, LCT, letterFlag, Row, Column);
                }
            }
            if (LCT.isColumn('x'))
                x = x + 5 * LCT.getXdirection();
            else if(LCT.isColumn('y'))
                y = y + 5 * LCT.getYdirection();
            else
                z = z + 5 * LCT.getZdirection();

            for (int Row = 0; Row < 5; Row ++)
            {
                build(player, x, y, z, Main, Background, LCT, new boolean[]{false, false, false, false, false}, Row, 0);
            }

            if (LCT.isColumn('x'))
                x = x + 1 * LCT.getXdirection();
            else if(LCT.isColumn('y'))
                y = y + 1 *LCT.getYdirection();
            else
                z = z + 1 *LCT.getZdirection();
        }
        return true;
    }

    private void build(Player player, int x, int y, int z, String Main, String Background, locationChangeTool LCT, boolean[] letterFlag, int Row, int Column) {
        LCT.updateRowAndColumn(Row, Column);
        Location location = new Location(player.getLocation().getWorld(), LCT.addX(x), LCT.addY(y), LCT.addZ(z));
        if(letterFlag[Row *(letterFlag.length/5)+ Column]){
            location.getBlock().setType(material.block.get(Main));
        }
        else {
            if(Background.equals(""))
                location.getBlock().setType(material.block.get("Air"));
            else
                location.getBlock().setType(material.block.get(Background));
        }
    }

    private int[] blockDirection(boolean[] blockFacing) {
        int xDirection = 0, yDirection = 0, zDirection = 0;
        if(blockFacing[0]){
            yDirection = 1;
            if (blockFacing[1]){
                if(blockFacing[2])
                    xDirection = 1;
                else
                    xDirection = -1;
            }
            else
            {
                if(blockFacing[3])
                    zDirection = 1;
                else
                    zDirection = -1;
            }
        }
        else {
            if (blockFacing[2])
                xDirection = 1;
            else
                xDirection = -1;
            if (blockFacing[3])
                zDirection = 1;
            else
                zDirection = -1;
        }
        return new int[]{xDirection, yDirection, zDirection};
    }

    private boolean[] facingCalc(Player player){
        boolean onlyone = false;
        boolean Xfirst = false;
        boolean X = false;
        boolean Z = false;
        double yaw = player.getLocation().getYaw();
        double pitch = player.getLocation().getPitch();

        if(pitch < -45){
            if(yaw > -45 && yaw <= 45){
                X = false;
                Z = false;
                Xfirst = true;
            }

            else if(yaw > 45 && yaw <= 135) {
                Z = false;
                X = true;
                Xfirst = false;
            }
            else if(yaw > 135 || yaw <= -135) {
                X = true;
                Z = true;
                Xfirst = true;
            }
            else {
                Z = true;
                X = false;
                Xfirst = false;
            }
        }
        else if(pitch > 45){
            if(yaw > -45 && yaw <= 45) {
                X = false;
                Z = true;
                Xfirst = true;
            }
            else if(yaw > 45 && yaw <= 135) {
                Z = false;
                X = false;
                Xfirst = false;
            }
            else if(yaw > 135 || yaw <= -135) {
                X = true;
                Z = false;
                Xfirst = true;
            }
            else {
                Z = true;
                X = true;
                Xfirst = false;
            }
        }
        else {
            onlyone = true;
            if (yaw > -45 && yaw <= 45) {
                X = false;
                Xfirst = true;
            }
            else if (yaw > 45 && yaw <= 135) {
                Z = false;
                Xfirst = false;
            }
            else if (yaw > 135 || yaw <= -135) {
                X = true;
                Xfirst = true;
            }
            else {
                Z = true;
                Xfirst = false;
            }
        }
        return new boolean[]{onlyone, Xfirst, X, Z};
    }
}
