package com.example.proiect_apostu_gavril_1081;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class rateAppFragment extends Fragment {

    private static UsersWithCardsDB usersWithCardsDB;
    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rate_app_fragment, container, false);

        usersWithCardsDB= Room.databaseBuilder(getContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Feedback");

        SharedPreferences sharedPref = getContext().getSharedPreferences("SharedPreferences", getContext().MODE_PRIVATE);
        final int idUser = sharedPref.getInt("idUser", 0);



        Button sendFeedbackBtn = view.findViewById(R.id.sendFeedbackBtn);
        sendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = view.findViewById(R.id.ratingBar);
                RadioGroup radioGroup = view.findViewById(R.id.radiobuttonGroup);
                RadioButton checkedRadioButton;
                int idFeedback=0;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                checkedRadioButton = view.findViewById(selectedId);
                // verificam daca s-a acordat feedback-ul
                if (ratingBar.getRating() != 0) {
                    if (checkedRadioButton != null) {
                        if (checkedRadioButton.getText().equals("Yes")) {
                            Feedback feedback = new Feedback(idUser, ratingBar.getRating(), true);
                            idFeedback = usersWithCardsDB.getFeedbackDAO().getIdFeedbackByAll(feedback.getFk_idUser(), feedback.getNoStars(), feedback.isRecommend());
                            String username = usersWithCardsDB.getUserDAO().getUserById(idUser).getUsername();

                            DatabaseReference myRef = database.getReference("Feedback");
                            DatabaseReference feedbackRef = myRef.child("Feedback-"+username);
                            if(idFeedback==0){
                                usersWithCardsDB.getFeedbackDAO().insertFeedback(feedback);
                                idFeedback = usersWithCardsDB.getFeedbackDAO().getIdFeedbackByAll(feedback.getFk_idUser(), feedback.getNoStars(), feedback.isRecommend());
                                feedback.setIdFeedback(idFeedback);
                                feedbackRef.setValue(feedback);
                                Toast.makeText(getContext(), "Feedback sent! Thank you!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "You already sent this feedback!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Feedback feedback = new Feedback(idUser, ratingBar.getRating(), false);
                            idFeedback = usersWithCardsDB.getFeedbackDAO().getIdFeedbackByAll(feedback.getFk_idUser(), feedback.getNoStars(), feedback.isRecommend());
                            String username = usersWithCardsDB.getUserDAO().getUserById(idUser).getUsername();

                            DatabaseReference myRef = database.getReference("Feedback");
                            DatabaseReference feedbackRef = myRef.child("Feedback-"+username);

                            if(idFeedback==0){
                                usersWithCardsDB.getFeedbackDAO().insertFeedback(feedback);
                                idFeedback = usersWithCardsDB.getFeedbackDAO().getIdFeedbackByAll(feedback.getFk_idUser(), feedback.getNoStars(), feedback.isRecommend());
                                feedback.setIdFeedback(idFeedback);
                                feedbackRef.setValue(feedback);
                                Toast.makeText(getContext(), "Feedback sent! Thank you!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "You already sent this feedback!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Toast.makeText(getContext(), "Please choose if you would recommend our app!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please select the number of stars!", Toast.LENGTH_SHORT).show();
                }
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
