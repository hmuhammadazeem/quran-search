package com.upheave.quransearch.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VerseSearchResponse implements JsonDeserializer, Serializable {
    private ArrayList<VerseModel> responseList;

    private static final String MATCHES = "matches";

    public VerseSearchResponse(ArrayList<VerseModel> responseList) {
        this.responseList = responseList;
    }

    public ArrayList<VerseModel> getResponseList() {
        return responseList;
    }

    public void setResponseList(ArrayList<VerseModel> responseList) {
        this.responseList = responseList;
    }

    @Override
    public VerseSearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<VerseModel> response = null;
        Gson gson = new Gson();

        JsonObject jsonObject = json.getAsJsonObject().get("data").getAsJsonObject();

        if (jsonObject.has(MATCHES)) {
            JsonElement elem = jsonObject.get(MATCHES);
            if (elem != null && !elem.isJsonNull()) {
                if (jsonObject.get(MATCHES).isJsonArray()) {
                    Gson gsonDeserializer = new GsonBuilder()
                            .registerTypeAdapter(VerseModel.class, new VerseModel())
                            .create();
                    response = gsonDeserializer.fromJson(jsonObject.get(MATCHES), new TypeToken<List<VerseModel>>(){}.getType());
                }
            }
        }

        if (response == null) {
            response = new ArrayList<>();
        }

        return new VerseSearchResponse(response);
    }
}
