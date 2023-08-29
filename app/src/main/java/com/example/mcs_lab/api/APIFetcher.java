package com.example.mcs_lab.api;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.model.Medicine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.example.mcs_lab.database.Database;

public class APIFetcher {

    private Context context;
    private Database db;
    private SharedPreferences prefs;
    public APIFetcher(Context context){
        this.context = context;
        db = Database.getInstance(context);
        prefs = context.getSharedPreferences("APIFetcherPrefs", Context.MODE_PRIVATE);
    }

    public void fetchAndStoreData(){
        boolean isExecuted = prefs.getBoolean("dataFetched", false);
        System.out.println("Not Fetched");
        String url = "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJsonResponse(response);
//                        ArrayList<Medicine> medicines = parseJsonResponse(response);
//                        storeMedicinesInDatabase(medicines);

                        // Set flag indicating data has been fetched
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("dataFetched", true);
                        editor.apply();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void parseJsonResponse(JSONArray jsonArray){
        ArrayList<Medicine> medicines = new ArrayList<>();
        System.out.println("Parse JSON");
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String manufacturer = jsonObject.getString("manufacturer");
                int price = jsonObject.getInt("price");
                String imageUrl = jsonObject.getString("image");
                String desc = jsonObject.getString("description");

//                Medicine medicine = new Medicine(name, manufacturer, price, imageUrl, desc);
//                medicines.add(medicine);
                System.out.println(name);
                db.addMedicine(name, manufacturer, price, imageUrl, desc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void storeMedicinesInDatabase(ArrayList<Medicine> medicines) {
        for (Medicine medicine : medicines) {
//            db.addMedicine(medicine);
        }
    }
}
