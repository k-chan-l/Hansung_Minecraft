package lkc.httpconnection.formatters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

public class stringToJsonFormatter {

    public static JsonObject JsonParsing(String jsonString){
        return JsonValidChecker(jsonString);
    }

    private static JsonObject JsonValidChecker(String jsonString){
        JsonElement element = JsonNullChecker(jsonString);
        if(element == null) return null;
        JsonObject jsonObject = JsonObjectChecker(element);
        if(jsonObject == null) return null;
        if(!HasEssentialItems(jsonObject)) return null;
        return jsonObject;
    }

    private static JsonElement JsonNullChecker(String jsonString){
        if (jsonString == null || jsonString == ""){
            Bukkit.getLogger().severe("jsonString is null");
            return null;
        }
        JsonElement element = JsonParser.parseString(jsonString);
        return element;
    }

    private static JsonObject JsonObjectChecker(JsonElement element){
        if(!element.isJsonObject()) {
            Bukkit.getLogger().severe("element is not json object");
            return null;
        }
        JsonObject jsonObject = element.getAsJsonObject();
        return jsonObject;
    }

    private static JsonArray JsonArrayChecker(JsonElement element){
        if(!element.isJsonArray()) {
            Bukkit.getLogger().severe("element is not json array");
            return null;
        }
        JsonArray jsonArray = element.getAsJsonArray();
        return jsonArray;
    }



    private static boolean HasItem(JsonObject jsonObject, String memberName){
        if(!jsonObject.has(memberName)){
            Bukkit.getLogger().severe("Object " + memberName +" is null");
            return false;
        }
        return true;
    }

    private static boolean HasEssentialItems(JsonObject jsonObject){
        if(!HasItem(jsonObject, "Items")) return false;

        JsonArray Items = JsonArrayChecker(jsonObject.get("Items"));
        if(Items == null) {
            Bukkit.getLogger().severe("Items is null");
            return false;
        }

        JsonObject Item = JsonObjectChecker(Items.get(0));
        if(Item == null){
            Bukkit.getLogger().severe("Item is null");
            return false;
        }
        if(!HasItem(Item, "PK")) return false;
        if(!HasItem(Item, "SK")) return false;
        if(!HasItem(Item, "date")) return false;
        if(!HasItem(Item, "playerID")) return false;
        if(!HasItem(Item, "playerInfo")) return false;

        JsonObject playerInfo = JsonObjectChecker(Item.get("playerInfo"));
        if(playerInfo == null){
            Bukkit.getLogger().severe("playerInfo is null");
            return false;
        }
        if(!HasItem(playerInfo, "level")) return false;
        if(!HasItem(playerInfo, "age")) return false;
        if(!HasItem(playerInfo, "numberOfVisit")) return false;

        return true;
    }

}
