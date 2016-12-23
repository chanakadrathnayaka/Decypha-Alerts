package com.dfn.alerts.dataaccess.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hasarindat
 * Date: 11/22/12
 * Time: 11:33 AM
 */
public class GsonNaturalDeserializer implements JsonDeserializer<Object> {

    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (json.isJsonNull()){
            return null;
        }
        else if (json.isJsonPrimitive()) {
            return handlePrimitive(json.getAsJsonPrimitive());
        }
        else if (json.isJsonArray()) {
            return handleArray(json.getAsJsonArray(), context);
        }
        else {
            return handleObject(json.getAsJsonObject(), context);
        }
    }

    private Object handlePrimitive(JsonPrimitive json) {
        if (json.isBoolean()) {
            return json.getAsBoolean();
        } else if (json.isString()) {
            return json.getAsString();
        } else {
            return json.getAsDouble();
        }
    }

    private Object handleArray(JsonArray json, JsonDeserializationContext context) {
        Object[] array = new Object[json.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = context.deserialize(json.get(i), Object.class);
        return array;
    }

    private Object handleObject(JsonObject json, JsonDeserializationContext context) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet())
            map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));
        return map;
    }
}
