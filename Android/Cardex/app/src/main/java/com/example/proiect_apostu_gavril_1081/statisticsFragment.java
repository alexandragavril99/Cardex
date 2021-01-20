package com.example.proiect_apostu_gavril_1081;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class statisticsFragment extends Fragment {

    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);


        Button showPieChartBtn = view.findViewById(R.id.showPieChartBtn);
        showPieChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), statisticsActivity.class);
                startActivity(it);
            }
        });

        Button rateAppForChart = view.findViewById(R.id.rateAppForCharts);
        rateAppForChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new rateAppFragment());
                fragmentTransaction.commit();
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

    public void onResume() {
        super.onResume();
    }

}
