package com.example.proiect_apostu_gavril_1081;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class voucherNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_number);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        int idUser = sharedPref.getInt("idUser", 0);
        String birthday = sharedPref.getString("birthday", null);
        String[] birthdayArray = birthday.split("/",3);

        TextView textViewVoucherNr = findViewById(R.id.idVoucherNumber);
        textViewVoucherNr.setText(birthdayArray[0]+ "00" + birthdayArray[1] + "11" + birthdayArray[2] + "22");
    }
}