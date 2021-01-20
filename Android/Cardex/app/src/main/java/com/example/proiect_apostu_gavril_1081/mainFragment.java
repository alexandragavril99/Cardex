package com.example.proiect_apostu_gavril_1081;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class mainFragment extends Fragment {
    private static UsersWithCardsDB usersWithCardsDB;
    private List<Card> cards;  //declaram lista de carduri

    private onFragmentBtnSelected listener; //instanta a interfetei create
    private String pickedCategory = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment, container, false);
        usersWithCardsDB= Room.databaseBuilder(getContext(), UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFabButtonSelected();
            }
        });


        Spinner categorySpinner = view.findViewById(R.id.spinnerCategory);
        String categories[] = {"All", "Beauty Stores", "Fashion", "Homeware", "Supermarket"};

        categorySpinner.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, categories));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinnerView, int position, long id) {
                // formatam stilul elementelor din Spinner
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                Typeface itemFont=Typeface.createFromAsset(getContext().getAssets(), "res/font/poppins_medium.ttf");
                ((TextView) parent.getChildAt(0)).setTypeface(itemFont);

                pickedCategory=parent.getItemAtPosition(position).toString();
                showCards(view, pickedCategory);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showCards(view, "All");
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCards(getView(), pickedCategory);
    }

    @Override
    public void onAttach(@NonNull Context context) { //metoda care verifica daca fragmentul s-a adaugat in cadrul activitatii
        super.onAttach(context);

        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else {
            throw new ClassCastException(context.toString()+ " must implement listener.");
        }
    }

    public interface onFragmentBtnSelected { // realizam o interfata ce va fi implementata in activitatea ce contine meniul
        public void onFabButtonSelected();
    }


    public void showCards(View view, final String category) {
        cards = new ArrayList<>();
        ArrayList<Card> emptycards = new ArrayList<>();
        final List<Card> categorizedCards = new ArrayList<>();
        SharedPreferences sharedPref = getContext().getSharedPreferences("SharedPreferences", getContext().MODE_PRIVATE);
        int idUser = sharedPref.getInt("idUser", 0);
        cards=usersWithCardsDB.getCardDAO().getCardByUserId(idUser);

        if(!cards.equals(emptycards)) {

            ListView listView =view.findViewById(R.id.listView);
            if(category.equals("All")) {
                CardAdapter adapter = new CardAdapter(cards, view.getContext());
                listView.setAdapter(adapter);
            } else {

                for(int i=0; i<cards.size(); i++){
                    if(category.equals(cards.get(i).getCategory())) {
                        categorizedCards.add(cards.get(i));
                    }
                }
                CardAdapter adapter = new CardAdapter(categorizedCards, view.getContext());
                listView.setAdapter(adapter);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Card card;
                    if(category.equals("All")){
                        card = cards.get(position);
                    }
                    else {
                        card = categorizedCards.get(position);
                    }
                    Intent it = new Intent(getContext(), cardNumberActivity.class);
                    it.putExtra("card", card);
                    startActivity(it);
                }
            });
        }
        else {
            TextView noCards = view.findViewById(R.id.noCardsTV);
            noCards.setVisibility(view.VISIBLE);
        }
    }
}
