package com.example.proiect_apostu_gavril_1081;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class forgotPassword extends AppCompatActivity {

    private static UsersWithCardsDB usersWithCardsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        usersWithCardsDB= Room.databaseBuilder(getApplicationContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

    }

    public void sent_email(View view) {
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        User searchedUser=usersWithCardsDB.getUserDAO().getUserByEmail(editTextEmail.getText().toString());
        if(searchedUser!=null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast toast = Toast.makeText(this, "Email was sent", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,400);
            toast.show();

            Toast.makeText(this, usersWithCardsDB.getUserDAO().getPasswordByEmail(searchedUser.getEmail()), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast toast = Toast.makeText(this, "The account does not exist!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,400);
            toast.show();
        }

    }
}