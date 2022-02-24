package lkc.mqttlungrow.commands;

import lkc.mqttlungrow.mqttconection.Mqtt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getLogger;

public class unsubscribe implements CommandExecutor {
    private static Mqtt MQTT;
    public unsubscribe(Mqtt mqtt){
        MQTT = mqtt;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("only Player can use command");
            return false;
        }
        if (!label.equals("unsubscribe")) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("there is no topic");
            return false;
        }
        try {
            MQTT.unsubscribe(args[0]);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        sender.sendMessage("unsubscribe " + args[0]);
        return true;
    }
}
