package com.example.th_app_02;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {
    private static final String PREF_NAME = "FoodGoFavPref";
    private static final String KEY_FAVS = "favorite_ids";

    private final SharedPreferences prefs;

    public FavoriteManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFavorite(int foodId) {
        Set<String> set = prefs.getStringSet(KEY_FAVS, new HashSet<>());
        return set.contains(String.valueOf(foodId));
    }

    public boolean toggleFavorite(int foodId) {
        Set<String> set = new HashSet<>(prefs.getStringSet(KEY_FAVS, new HashSet<>()));
        String idStr = String.valueOf(foodId);
        boolean isFav;
        if (set.contains(idStr)) {
            set.remove(idStr);
            isFav = false;
        } else {
            set.add(idStr);
            isFav = true;
        }
        prefs.edit().putStringSet(KEY_FAVS, set).apply();
        return isFav;
    }

    public Set<String> getFavoriteIds() {
        return prefs.getStringSet(KEY_FAVS, new HashSet<>());
    }
}
