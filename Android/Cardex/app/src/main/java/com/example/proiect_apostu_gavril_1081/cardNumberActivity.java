package com.example.proiect_apostu_gavril_1081;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class cardNumberActivity extends AppCompatActivity {

    private static UsersWithCardsDB usersWithCardsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_number);

        usersWithCardsDB= Room.databaseBuilder(this, UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();
        SharedPreferences sharedPref = this.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        final int idUser = sharedPref.getInt("idUser", 0);
        Bundle bundle = getIntent().getExtras();

        final Card card = bundle.getParcelable("card");
        EditText editTextCardNumber = findViewById(R.id.idVoucherNumber);
        editTextCardNumber.setText(card.getCardNumber());
        TextView textViewStoreName =findViewById(R.id.idVoucherT);
        textViewStoreName.setText(card.getStoreName());


        final Button deleteCardBtn = findViewById(R.id.buttonDeleteCard);
        deleteCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderdelete = new AlertDialog.Builder(cardNumberActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                builderdelete.setTitle("Delete Card?");
                builderdelete.setMessage("Are you sure you want to delete this card?");
                builderdelete.setCancelable(true);

                builderdelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builderdelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Card currentCard = usersWithCardsDB.getCardDAO().getCardByIdCard(card.getIdCard(), idUser);
                        usersWithCardsDB.getCardDAO().deleteCard(currentCard);
                        finish();
                    }
                });

                AlertDialog alertDialog = builderdelete.create();
                alertDialog.show();
            }

        });

        Button updateCardBtn = findViewById(R.id.buttonUpdateCard);
        updateCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextCardNumber = findViewById(R.id.idVoucherNumber);
                Card updatedCard = usersWithCardsDB.getCardDAO().getCardByIdCard(card.getIdCard(), idUser);
                updatedCard.setCardNumber(editTextCardNumber.getText().toString());
                usersWithCardsDB.getCardDAO().updateCard(updatedCard);

                Toast.makeText(cardNumberActivity.this, "Card Number was successfully updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}