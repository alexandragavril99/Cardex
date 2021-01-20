package com.example.proiect_apostu_gavril_1081;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

public class JsonParseID extends AsyncTask<String,Void, Integer> {

    String selectedStore;
    String selectedCategory;
    int objectID;
    public JsonParseID(String selectedStore, String selectedCategory) {
        this.selectedCategory = selectedCategory;
        this.selectedStore = selectedStore;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
            InputStream stream  = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String linie = "";
            StringBuilder builder = new StringBuilder(); //fol stringbuilder ca sa nu cream f multe obiecte de tip string

            while((linie = reader.readLine()) != null) {
                builder.append(linie);
            }
            String textFull = builder.toString();
            JSONObject object = new JSONObject(textFull);
            JSONObject firstObject = object.getJSONObject("ProiectDAM");
            JSONArray collection = firstObject.getJSONArray("Stores");

            for(int i=0; i<collection.length(); i++) {
                JSONObject index = collection.getJSONObject(i);
                String category = index.getString("Category");
                String store = index.getString("StoreName");

                if(category.equals(selectedCategory) && store.equals(selectedStore)) {
                    objectID = index.getInt("ID");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objectID;
    }
}
