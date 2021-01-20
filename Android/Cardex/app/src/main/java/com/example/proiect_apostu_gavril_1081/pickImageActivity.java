package com.example.proiect_apostu_gavril_1081;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class pickImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int) (height*0.4));

        final SharedPreferences sharedPref = this.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        final int idUser = sharedPref.getInt("idUser", 0);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
        final String prickedImage = sharedPref.getString("pickedImage", encodeTobase64(bm));

        final ImageView profilePic1 = findViewById(R.id.profilePic1);
        profilePic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic1.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });

        final ImageView profilePic2 = findViewById(R.id.profilePic2);
        profilePic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic2.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });
        final ImageView profilePic3 = findViewById(R.id.profilePic3);
        profilePic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic3.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });
        final ImageView profilePic4 = findViewById(R.id.profilePic4);
        profilePic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic4.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });
        final ImageView profilePic5 = findViewById(R.id.profilePic5);
        profilePic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic5.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });
        final ImageView profilePic6 = findViewById(R.id.profilePic6);
        profilePic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                BitmapDrawable drawable = (BitmapDrawable) profilePic6.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                editor.putString("pickedImage", encodeTobase64(bitmap));
                editor.commit();
                finish();
            }
        });
    }

    // metoda pentru a scrie bitmap in base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
}