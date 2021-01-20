package com.example.proiect_apostu_gavril_1081;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class statisticsActivity extends AppCompatActivity {

    List<Feedback> feedbackList;
    ConstraintLayout chartLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        chartLayout = findViewById(R.id.cardViewLayout);
        readFromFirebase();


    }

    public void readFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Feedback");
        myRef.keepSynced(true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackList  = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Feedback feedback = ds.getValue(Feedback.class);
                    Log.v("orice", ds.getValue(Feedback.class).toString());
                    feedbackList.add(feedback);
                }
                //Toast.makeText(getApplicationContext(), feedbackList.toString(), Toast.LENGTH_SHORT).show();
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                chartLayout.addView(new PieChart(getApplicationContext(),feedbackList,width));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}