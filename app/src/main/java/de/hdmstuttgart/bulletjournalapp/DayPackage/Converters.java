package de.hdmstuttgart.bulletjournalapp.DayPackage;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Bullet>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Bullet> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}