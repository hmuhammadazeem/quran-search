package com.upheave.quransearch.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class VerseModel implements JsonDeserializer, Serializable {
    private String VerseText;
    private Integer SurahNumber;
    private String SurahName;
    private String SurahEnglishName;
    private int VerseNumber;

    public VerseModel(String verseText, Integer surahNumber, String surahName, String surahEnglishName, int verseNumber) {
        VerseText = verseText;
        SurahNumber = surahNumber;
        SurahName = surahName;
        SurahEnglishName = surahEnglishName;
        VerseNumber = verseNumber;
    }

    public VerseModel(){}

    public String getVerseText() {
        return VerseText;
    }

    public void setVerseText(String verseText) {
        VerseText = verseText;
    }

    public Integer getSurahNumber() {
        return SurahNumber;
    }

    public void setSurahNumber(Integer surahNumber) {
        SurahNumber = surahNumber;
    }

    public String getSurahName() {
        return SurahName;
    }

    public void setSurahName(String surahName) {
        SurahName = surahName;
    }

    public String getSurahEnglishName() {
        return SurahEnglishName;
    }

    public void setSurahEnglishName(String surahEnglishName) {
        SurahEnglishName = surahEnglishName;
    }

    public int getVerseNumber() {
        return VerseNumber;
    }

    public void setVerseNumber(int verseNumber) {
        VerseNumber = verseNumber;
    }

    @Override
    public VerseModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            JsonObject Surah = obj.get("surah").getAsJsonObject();
            VerseModel verseModel = null;

            try {
                verseModel = new VerseModel(
                        obj.get("text").getAsString(),
                        Surah.get("number").getAsInt(),
                        Surah.get("name").getAsString(),
                        Surah.get("englishName").getAsString(),
                        obj.get("numberInSurah").getAsInt()
                );
            }
            catch (Exception e) {
                // handle later
            }

        return verseModel;
    }
}
