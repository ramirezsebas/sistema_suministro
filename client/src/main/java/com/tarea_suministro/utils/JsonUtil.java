package com.tarea_suministro.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json.trim(), classOfT);
    }

    public static String printJson(Object object) {
        return GSON.newBuilder().setPrettyPrinting().create().toJson(object);
    }
}

