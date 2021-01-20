package com.example.proiect_apostu_gavril_1081;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class editProfileActivity extends AppCompatActivity {

    private static UsersWithCardsDB usersWithCardsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        usersWithCardsDB= Room.databaseBuilder(this, UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

        final SharedPreferences sharedPref = this.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        final int idUser = sharedPref.getInt("idUser", 0);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
        final String pickedImage = sharedPref.getString("pickedImage", encodeTobase64(bm));
        final User currentUser=usersWithCardsDB.getUserDAO().getUserById(idUser);

        final ImageView profileImage = findViewById(R.id.storeLogoImage);
        profileImage.setImageBitmap(decodeBase64(pickedImage));

        final EditText etName = findViewById(R.id.idEditedName);
        etName.setText(currentUser.getName());
        final EditText etSurname = findViewById(R.id.idEditedSurname);
        etSurname.setText(currentUser.getSurname());
        final EditText etUsername = findViewById(R.id.idEditedUsername);
        etUsername.setText(currentUser.getUsername());
        final EditText etPassword = findViewById(R.id.idEditedPassword);
        etPassword.setText(currentUser.getPassword());
        final EditText etPhone = findViewById(R.id.idEditedPhone);
        etPhone.setText(currentUser.getPhone());
        final EditText etEmail = findViewById(R.id.idEditedEmail);
        etEmail.setText(currentUser.getEmail());
        final EditText etBirthday = findViewById(R.id.idEditedBirthday);
        etBirthday.setText(currentUser.getBirthday());

        Button updateAccBtn = findViewById(R.id.UpdateAccBtn);
        updateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput(v)) {
                    Intent intent = new Intent();

                    currentUser.setName(etName.getText().toString());
                    currentUser.setSurname(etSurname.getText().toString());
                    currentUser.setUsername(etUsername.getText().toString());
                    currentUser.setPassword(etPassword.getText().toString());
                    currentUser.setPhone(etPhone.getText().toString());
                    currentUser.setEmail(etEmail.getText().toString());
                    currentUser.setBirthday(etBirthday.getText().toString());

                    usersWithCardsDB.getUserDAO().updateUser(currentUser);

                    // modificam in shared preferences data de nastere a user-ului
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("birthday", currentUser.getBirthday()).apply();

                    Toast.makeText(editProfileActivity.this, "Account was successfully updated!", Toast.LENGTH_SHORT).show();

                    // trimitem in profile parola codificata
                    int passwordCharacters = currentUser.getPassword().length();
                    StringBuilder starPassword = new StringBuilder(passwordCharacters);
                    for(int i=0; i<passwordCharacters;i++){
                        starPassword.append("*");
                    }
                    currentUser.setPassword(starPassword.toString());

                    intent.putExtra("updatedUser", currentUser);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        Button cancelBtn = findViewById(R.id.CancelUpdateBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), pickImageActivity.class);
                startActivity(it);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
        final String pickedImage = sharedPref.getString("pickedImage", encodeTobase64(bm));
        ImageView profileImage = findViewById(R.id.storeLogoImage);
        profileImage.setImageBitmap(decodeBase64(pickedImage));
    }

    public boolean validateInput(View view) {
        EditText etName = findViewById(R.id.idEditedName);
        EditText etSurname = findViewById(R.id.idEditedSurname);
        EditText etUsername = findViewById(R.id.idEditedUsername);
        EditText etPassword = findViewById(R.id.idEditedPassword);
        EditText etPhone = findViewById(R.id.idEditedPhone);
        EditText etEmail = findViewById(R.id.idEditedEmail);
        EditText etBirthday = findViewById(R.id.idEditedBirthday);

        if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etName.getText().toString().isEmpty()
                || etSurname.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() ||
                etBirthday.getText().toString().isEmpty()) {
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
        else if(!etBirthday.getText().toString().matches("^[0-9\\.\\-\\/]+$") || etBirthday.getText().length() >10) {
            Toast.makeText(this,"Incorrect birthday!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    // metoda pentru a prelua bitmapul din base64
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
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