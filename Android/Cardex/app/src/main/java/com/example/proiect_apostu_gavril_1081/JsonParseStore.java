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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JsonParseStore extends AsyncTask<String,Void, List<String>> {
    String selectedText;
    List <String> list = new ArrayList<>();
    public JsonParseStore(String selectedText) {
        this.selectedText = selectedText;
    }

    @Override
    protected List<String> doInBackground(String... strings) {
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
                if(category.equals(selectedText)) {
                    String storeName = index.getString("StoreName");
                    list.add(storeName);
                }
            }
            Collections.sort(list);
            list.add(0,"Choose store");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
