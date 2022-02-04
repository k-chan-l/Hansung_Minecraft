package lkc.httpconnection.formatters;

import com.google.gson.JsonObject;

public class jsonToStringFormatter {
    public static String StringFormatting(JsonObject jsonObject){
        return jsonObject.toString();
    }
}
