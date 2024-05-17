package com.luckin.coffee.json;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonStringParser {

    private JsonStringParser() {
    }

    public static JsonObject getJsonObject(String jsonString) {

        JsonObject jsonObject = null;
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            jsonObject = reader.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
