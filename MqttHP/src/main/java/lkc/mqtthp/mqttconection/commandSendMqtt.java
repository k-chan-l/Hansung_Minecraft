package lkc.mqtthp.mqttconection;

import lkc.mqtthp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

import static org.bukkit.Bukkit.getServer;

public class commandSendMqtt implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("sendmqtt")){
            if (sender instanceof Player){

            }
        }


        return this.sendMqtt(sender,args);
    }

    // sendMqtt
    private boolean sendMqtt(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "The sendmqtt command needs a topic and a payload as parameters!");
            return false;
        }

        // http request
       // this.whenAsynchronousGetRequest_thenCorrect(sender);

        try {

            // topic <=== args[0]
            // message preparation
            StringJoiner joiner = new StringJoiner(" ");
            for (int i = 1; i < args.length; i++) {
                joiner.add(args[i]);
            }
            sender.sendMessage(ChatColor.GREEN + "Sending payload " + joiner.toString() + " to topic " + args[0]);
            String topic = args[0];  // MQTT topic
            String message = joiner.toString(); // MQTT message
            Main.mqtt1.client.publish(topic, message); // publish message
        } catch (MqttException e) {
            getServer().getLogger().severe("Failed sending message to MQTT broker!");
        }

        return true;
    }
}
