package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.content.SharedPreferences;

import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 유리 on 2017-09-13.
 */

public class SharedPreferencesManager {
    private final String RECORD_SEARCHING = "recordOfSearching";

    public void saveSharedPreferencesLogList(Context context, ArrayList<SearchRecordItem> searchRecordItems) {
        SharedPreferences mPrefs = context.getSharedPreferences(RECORD_SEARCHING, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(searchRecordItems);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }


    public ArrayList<SearchRecordItem> loadSharedPreferencesLogList(Context context) {
        ArrayList<SearchRecordItem> recordItems;
        SharedPreferences mPrefs = context.getSharedPreferences(RECORD_SEARCHING, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            recordItems = new ArrayList<SearchRecordItem>();
        } else {
            Type type = new TypeToken<List<SearchRecordItem>>() {
            }.getType();
            recordItems = gson.fromJson(json, type);
        }
        return recordItems;
    }
}
