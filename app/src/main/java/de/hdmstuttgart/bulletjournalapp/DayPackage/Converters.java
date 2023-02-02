package de.hdmstuttgart.bulletjournalapp.DayPackage;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;

public class Converters {

    // For reading from the database
    @TypeConverter
    public static ArrayList<Bullet> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Bullet>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    // For writing to the database
    public static String fromArrayList(ArrayList<Bullet> list) {
        Type personType = new TypeToken<ArrayList<Bullet>>(){}.getType();
        Gson gson = new Gson();
        String json = gson.toJson(list, personType);
        return json;
    }
}