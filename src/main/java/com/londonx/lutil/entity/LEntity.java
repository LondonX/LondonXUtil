package com.londonx.lutil.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by london on 15/5/29.
 * LEntity
 */
public class LEntity implements Serializable {
    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}
