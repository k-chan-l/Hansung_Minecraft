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
        return HasEssentialItems(jsonObject);
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

    private static JsonObject HasEssentialItems(JsonObject jsonObject){
        if(!HasItem(jsonObject, "Items")) return null;

        JsonArray Items = JsonArrayChecker(jsonObject.get("Items"));
        if(Items == null || Items.size() == 0) {
            Bukkit.getLogger().severe("Items is null");
            return null;
        }

        JsonObject Item = JsonObjectChecker(Items.get(Items.size()-1));
        if(Item == null){
            Bukkit.getLogger().severe("Item is null");
            return null;
        }
        if(!HasItem(Item, "PK")) return null;
        if(!HasItem(Item, "SK")) return null;
        if(!HasItem(Item, "date")) return null;
        if(!HasItem(Item, "playerID")) return null;
        if(!HasItem(Item, "playerInfo")) return null;

        JsonObject playerInfo = JsonObjectChecker(Item.get("playerInfo"));
        if(playerInfo == null){
            Bukkit.getLogger().severe("playerInfo is null");
            return null;
        }
        if(!HasItem(playerInfo, "level")) return null;
        if(!HasItem(playerInfo, "age")) return null;
        if(!HasItem(playerInfo, "numberOfVisit")) return null;

        return Item;
    }

}
