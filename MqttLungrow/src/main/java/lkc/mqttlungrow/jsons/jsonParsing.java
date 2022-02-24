package lkc.mqttlungrow.jsons;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lkc.mqttlungrow.MqttLungrow;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.logging.Logger;


public class jsonParsing {
    MqttLungrow mqttLungrow;
    String message;
    String inhale;
    String exhale;
    Logger logger;

    public jsonParsing(String message, Logger logger, MqttLungrow mqttLungrow){
        this.mqttLungrow = mqttLungrow;
        this.message = message;
        this.logger = logger;
    }



    private boolean keyValueParsing(){
        // gson을 이용하여 json string을 객체로 바꾼다.
        if(message == null) {
            logger.severe("mqtt message is null");
            return false;
        }
        JsonElement element = JsonParser.parseString(message);

        if(!element.isJsonObject()) {
            logger.severe("element is not json object");
            return false;
        }
        JsonObject jsonObject = element.getAsJsonObject();

        if(!jsonObject.has("inhale")) {
            logger.severe("inhale value is null");
            return false;
        }
        inhale = jsonObject.get("inhale").getAsString();

        if(!jsonObject.has("exhale")) {
            logger.severe("exhale value is null");
            return false;
        }
        exhale = jsonObject.get("exhale").getAsString();
        return true;
    }

    public void updatemqttValue(Player player){
        if(!keyValueParsing())
            return;
        int inhaleValue = Integer.parseInt(inhale);
        int exhaleValue = Integer.parseInt(exhale);

        mqttLungrow.updateValue(inhaleValue, exhaleValue, player);
    }

}
