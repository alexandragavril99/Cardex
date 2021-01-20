package com.example.proiect_apostu_gavril_1081;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;



public class registerActivity extends AppCompatActivity {

    private static UsersWithCardsDB usersWithCardsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        usersWithCardsDB= Room.databaseBuilder(getApplicationContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();
    }

    public void register(View view) {
        if(validateInput(view)) {
            Intent intent = new Intent();
            EditText etUsername = findViewById(R.id.idUsername);
            EditText etPassword = findViewById(R.id.idPassword);
            EditText etName = findViewById(R.id.idName);
            EditText etSurname = findViewById(R.id.idSurname);
            EditText etPhone = findViewById(R.id.idPhone);
            EditText etEmail = findViewById(R.id.idEmail);
            DatePicker datePickerDP = findViewById(R.id.datepicker);
            int   day  = datePickerDP.getDayOfMonth();
            int   month= datePickerDP.getMonth() + 1;
            int   year = datePickerDP.getYear();
            String datePicker = day+"/"+month+"/"+year;

            User user = new User(etUsername.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etSurname.getText().toString(),
                    etPhone.getText().toString(), etEmail.getText().toString(), datePicker);

            //trimitem userul si parola in activitatea principala
            intent.putExtra("user", user);
            usersWithCardsDB.getUserDAO().insertUser(user);

            setResult(RESULT_OK, intent);
            finish();


            Toast toast = Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 240, 1200);
            toast.show();
        }
    }

    public boolean validateInput(View view) {
        EditText etUsername = findViewById(R.id.idUsername);
        EditText etPassword = findViewById(R.id.idPassword);
        EditText etName = findViewById(R.id.idName);
        EditText etSurname = findViewById(R.id.idSurname);
        EditText etPhone = findViewById(R.id.idPhone);
        EditText etEmail = findViewById(R.id.idEmail);
        DatePicker datePickerDP = findViewById(R.id.datepicker);
        int   year = datePickerDP.getYear();


        if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etName.getText().toString().isEmpty()
        || etSurname.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this,"Empty fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!etName.getText().toString().matches("^[a-zA-Z\\s]+") || etName.getText().length()<3
                || etName.getText().length()>20) {
            Toast.makeText(this,"Incorrect name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!etSurname.getText().toString().matches("^[a-zA-Z\\s]+") || etSurname.getText().length()<3
                || etSurname.getText().length()>20){
            Toast.makeText(this,"Incorrect surname!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!etPhone.getText().toString().matches("[0-9]+$") || etPhone.getText().length() != 10) {
            Toast.makeText(this, "Incorrect phone number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!etEmail.getText().toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Toast.makeText(this,"Incorrect email!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(year > 2015){
            Toast.makeText(this,"Invalid date!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

}
