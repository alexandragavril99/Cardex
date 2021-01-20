package com.example.proiect_apostu_gavril_1081;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class voucherFragment extends Fragment {

    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voucher_fragment, container, false);

        String currentMonth = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);

        SharedPreferences sharedPref = getContext().getSharedPreferences("SharedPreferences", getContext().MODE_PRIVATE);
        int idUser = sharedPref.getInt("idUser", 0);
        String birthday = sharedPref.getString("birthday", null);
        String[] birthdayArray = birthday.split("/",3);

        // verificam daca luna curenta este aceeasi cu luna de nastere a userului pentru a-i acorda voucherul cadou
        if(birthdayArray[1].equals(currentMonth)){
            CardView cardView = view.findViewById(R.id.card_view_voucher);
            cardView.setVisibility(view.VISIBLE);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), voucherNumberActivity.class);
                    startActivity(it);
                }
            });
        }
        else {
            TextView textView = view.findViewById(R.id.idNoVouchersTV);
            textView.setVisibility(view.VISIBLE);

        }

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
}
