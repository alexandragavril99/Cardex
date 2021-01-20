package com.example.proiect_apostu_gavril_1081;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    private static UsersWithCardsDB usersWithCardsDB;


    private int reqCodeRegisterActivity = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersWithCardsDB= Room.databaseBuilder(getApplicationContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();


        final SharedPreferences sharedPref = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

        Button buttonRegister=findViewById(R.id.createAcc);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), registerActivity.class);
                startActivityForResult(intent,reqCodeRegisterActivity);
            }
        });
        TextView passwordTW = findViewById(R.id.passwordTV);
        passwordTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), forgotPassword.class);
                startActivityForResult(intent,reqCodeRegisterActivity);
            }
        });


        Button buttonLogin=findViewById(R.id.buttonlogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), cardsMenu.class);
                SharedPreferences.Editor editor = sharedPref.edit();
                int idUser = sharedPref.getInt("idUser", 0);
                String birthday = sharedPref.getString("birthday", null);
                User loggedUser;
                EditText etUsername = findViewById(R.id.editTextUsername);
                EditText etPassword = findViewById(R.id.editTextPassword);

                loggedUser=usersWithCardsDB.getUserDAO().getUserByUsernameAndPassword(etUsername.getText().toString(), etPassword.getText().toString());

                if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty fields!", Toast.LENGTH_SHORT).show();
                } else if(loggedUser!=null) {
                    startActivity(intent);
                    editor.putInt("idUser", loggedUser.getIdUser());
                    editor.putString("birthday", loggedUser.getBirthday());
                    editor.commit();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "Username and password do not match!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == reqCodeRegisterActivity) {
            if(resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                User user = bundle.getParcelable("user");
                EditText etUsername = findViewById(R.id.editTextUsername);
                etUsername.setText(user.getUsername());
                EditText etPassword = findViewById(R.id.editTextPassword);
                etPassword.setText(user.getPassword());
            }
        }
    }


}
