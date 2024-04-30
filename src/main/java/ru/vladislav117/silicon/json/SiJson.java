package ru.vladislav117.silicon.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SiJson {
    static Gson gson = new Gson();

    public static JsonElement parse(String string) {
        return gson.fromJson(string, JsonElement.class);
    }
}
