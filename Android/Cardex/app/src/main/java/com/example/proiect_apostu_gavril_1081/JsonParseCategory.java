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
import java.util.HashSet;
import java.util.List;

public class JsonParseCategory extends AsyncTask<String,Void, List<String>> {

    List<String> lista = new ArrayList<>();
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
            JSONArray colectie = firstObject.getJSONArray("Stores");
            for(int i=0;  i<colectie.length(); i++) {
                JSONObject index = colectie.getJSONObject(i);
                String category = index.getString("Category");
                lista.add(category);
            }

            List<String> uniqueList = new ArrayList<>(new HashSet<>(lista));
            Collections.sort(uniqueList);
            uniqueList.add(0,"Choose category");
            lista = uniqueList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
