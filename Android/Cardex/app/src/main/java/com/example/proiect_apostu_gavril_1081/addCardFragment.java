package com.example.proiect_apostu_gavril_1081;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class addCardFragment extends Fragment {

    private onFragmentBtnSelect listener;
    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_card_fragment, container, false);
        final Spinner spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        final Spinner spinnerStoreName = (Spinner) view.findViewById(R.id.spinnerStoreName);

        // preluam categoriile cardului
        JsonParseCategory parser = new JsonParseCategory() {
            @Override
            protected void onPostExecute(List<String> lista) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,lista);
                spinnerCategory.setAdapter(adapter);
            }
        };
        parser.execute("https://api.mocki.io/v1/81541948");

        // preluam numele magazinelor
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                String selectedText = parent.getItemAtPosition(position).toString();
                JsonParseStore parser = new JsonParseStore(selectedText) {
                    @Override
                    protected void onPostExecute(List<String> list) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,list);
                        spinnerStoreName.setAdapter(adapter);

                    }
                };
                parser.execute("https://api.mocki.io/v1/81541948");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        Button btn = view.findViewById(R.id.idAddNewCard);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener.onButtonSelect();
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) { //metoda care verifica daca fragmentul s-a adaugat in cadrul activitatii
        super.onAttach(context);

        if (context instanceof addCardFragment.onFragmentBtnSelect) {
            listener = (addCardFragment.onFragmentBtnSelect) context;
        } else {
            throw new ClassCastException(context.toString()+ " must implement listener.");
        }
    }

    public interface onFragmentBtnSelect{
        public void onButtonSelect();
    }

}
