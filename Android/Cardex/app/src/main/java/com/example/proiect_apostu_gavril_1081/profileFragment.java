package com.example.proiect_apostu_gavril_1081;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class profileFragment extends Fragment {

    private static UsersWithCardsDB usersWithCardsDB;
    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment, container, false);

        usersWithCardsDB= Room.databaseBuilder(getContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

        SharedPreferences sharedPref = getContext().getSharedPreferences("SharedPreferences", getContext().MODE_PRIVATE);
        int idUser = sharedPref.getInt("idUser", 0);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
        final String pickedImage = sharedPref.getString("pickedImage", encodeTobase64(bm));
        final User currentUser=usersWithCardsDB.getUserDAO().getUserById(idUser);

        ImageView profileImage = view.findViewById(R.id.storeLogoImage);
        profileImage.setImageBitmap(decodeBase64(pickedImage));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                TextView textViewName = view.findViewById(R.id.NameTitle);
                textViewName.setText("Name: "+ " " + currentUser.getName());
                TextView textViewSurname = view.findViewById(R.id.SurnameTitle);
                textViewSurname.setText("Surname: "+ " " + currentUser.getSurname());
                TextView textViewUsername = view.findViewById(R.id.UsernameTV);
                textViewUsername.setText("Username: ");
                TextView textViewCurrentUsername = view.findViewById(R.id.currentUsernameTV);
                textViewCurrentUsername.setText(currentUser.getUsername());
                TextView textViewPassword = view.findViewById(R.id.PasswordTV);
                textViewPassword.setText("Password: ");

                //codificam parola cu atatea stelute cate caractere are parola user-ului
                int passwordCharacters = currentUser.getPassword().length();
                StringBuilder starPassword = new StringBuilder(passwordCharacters);
                for(int i=0; i<passwordCharacters;i++){
                    starPassword.append("*");
                }
                TextView textViewCurrentPassword = view.findViewById(R.id.currentPasswordTV);
                textViewCurrentPassword.setText(starPassword);
                TextView textViewPhone = view.findViewById(R.id.PhoneTV);
                textViewPhone.setText("Phone: ");
                TextView textViewCurrentPhone = view.findViewById(R.id.currentPhoneTV);
                textViewCurrentPhone.setText(currentUser.getPhone());
                TextView textViewEmail = view.findViewById(R.id.EmailTV);
                textViewEmail.setText("Email: ");
                TextView textViewCurrentEmail = view.findViewById(R.id.currentEmailTV);
                textViewCurrentEmail.setText(currentUser.getEmail());
                TextView textViewBirthday = view.findViewById(R.id.BirthdayTV);
                textViewBirthday.setText("Birthday: ");
                TextView textViewCurrentBirthday = view.findViewById(R.id.currentBirthdayTV);
                textViewCurrentBirthday.setText(currentUser.getBirthday());

            }
        });

        Button deleteAccBtn = view.findViewById(R.id.buttonDeleteAccount);
        deleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("Delete Account?");
                builder.setMessage("Are you sure you want to delete this account?");
                builder.setCancelable(true);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Card> userCards = new ArrayList<>();
                        userCards=usersWithCardsDB.getCardDAO().getCardByUserId(currentUser.getIdUser());
                        for(int i=0;i<userCards.size(); i++){
                            usersWithCardsDB.getCardDAO().deleteCard(userCards.get(i));
                        }
                        List<Feedback> userFeedbacks = new ArrayList<>();
                        userFeedbacks=usersWithCardsDB.getFeedbackDAO().getFeedbackByUserId(currentUser.getIdUser());
                        for(int i=0;i<userFeedbacks.size(); i++){
                            usersWithCardsDB.getFeedbackDAO().deleteFeedback(userFeedbacks.get(i));
                        }
                        usersWithCardsDB.getUserDAO().deleteUser(currentUser);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button editProfileBtn = view.findViewById(R.id.buttonEditProfile);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), editProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // tratam evenimentul de click pe butonul de back
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new mainFragment());
                fragmentTransaction.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        return view;
}

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == editProfileActivity.RESULT_OK){
                Bundle bundle = data.getExtras();
                User updatedUser = bundle.getParcelable("updatedUser");
                TextView textViewName = getView().findViewById(R.id.NameTitle);
                textViewName.setText("Name: "+ " " + updatedUser.getName());
                TextView textViewSurname = getView().findViewById(R.id.SurnameTitle);
                textViewSurname.setText("Surname: "+ " " + updatedUser.getSurname());
                TextView textViewCurrentUsername = getView().findViewById(R.id.currentUsernameTV);
                textViewCurrentUsername.setText(updatedUser.getUsername());
                TextView textViewCurrentPassword = getView().findViewById(R.id.currentPasswordTV);
                textViewCurrentPassword.setText(updatedUser.getPassword());
                TextView textViewCurrentPhone = getView().findViewById(R.id.currentPhoneTV);
                textViewCurrentPhone.setText(updatedUser.getPhone());
                TextView textViewCurrentEmail = getView().findViewById(R.id.currentEmailTV);
                textViewCurrentEmail.setText(updatedUser.getEmail());
                TextView textViewCurrentBirthday = getView().findViewById(R.id.currentBirthdayTV);
                textViewCurrentBirthday.setText(updatedUser.getBirthday());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getContext().getSharedPreferences("SharedPreferences", getContext().MODE_PRIVATE);
        int idUser = sharedPref.getInt("idUser", 0);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
        final String pickedImage = sharedPref.getString("pickedImage", encodeTobase64(bm));
        ImageView profileImage = getView().findViewById(R.id.storeLogoImage);
        profileImage.setImageBitmap(decodeBase64(pickedImage));
    }

    // metoda pentru a prelua bitmapul din baza64
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
