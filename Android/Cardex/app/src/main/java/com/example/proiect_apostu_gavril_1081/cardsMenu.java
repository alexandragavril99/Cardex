package com.example.proiect_apostu_gavril_1081;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class cardsMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, mainFragment.onFragmentBtnSelected,
        addCardFragment.onFragmentBtnSelect {

    private static UsersWithCardsDB usersWithCardsDB;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager; //folosit pt a crea instanta unui fragment
    FragmentTransaction fragmentTransaction; //executa ce avem nevoie pt fragment (il adaugam, il inlocuim, il stergem)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_menu);
        usersWithCardsDB= Room.databaseBuilder(this, UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();

        //adaugare meniu in cadrul activitatii
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //incarcam default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new mainFragment());
        fragmentTransaction.commit();

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new mainFragment());
                fragmentTransaction.commit();
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START); // inchidem meniul cand e apasat un buton

        if(menuItem.getItemId() == R.id.nav_home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new mainFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.new_card) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new addCardFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.rate_app) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new rateAppFragment());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() == R.id.vouchers) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new voucherFragment());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() == R.id.statistics) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new statisticsFragment());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() == R.id.log_out) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
        }

        if(menuItem.getItemId() == R.id.profile) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new profileFragment());
            fragmentTransaction.commit();
        }


        return true;
    }



    @Override
    public void onFabButtonSelected() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, new addCardFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonSelect() {

        final EditText cardNumber = findViewById(R.id.idCardNumberET);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        Spinner spinnerStoreName = findViewById(R.id.spinnerStoreName);

        if(cardNumber.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(), "You have to write the card number!", Toast.LENGTH_SHORT).show();
        }
        else if(cardNumber.getText().toString().length() >14){
            Toast.makeText(getApplicationContext(), "The card number is too long!", Toast.LENGTH_SHORT).show();
        }
        else if(cardNumber.getText().toString().length() <8){
            Toast.makeText(getApplicationContext(), "The card number is too short!", Toast.LENGTH_SHORT).show();
        }
        else if(spinnerCategory.getSelectedItem().equals("Choose category")){
            Toast.makeText(getApplicationContext(), "You have to choose a category!", Toast.LENGTH_SHORT).show();
        }
        else if(spinnerStoreName.getSelectedItem().equals("Choose store")){
            Toast.makeText(getApplicationContext(), "You have to choose a store!", Toast.LENGTH_SHORT).show();
        }
        else {
            usersWithCardsDB= Room.databaseBuilder(this, UsersWithCardsDB.class, "UsersWithCardsDB").allowMainThreadQueries().build();
            SharedPreferences sharedPref = this.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
            final int idUser = sharedPref.getInt("idUser", 0);

            String selectedStore = spinnerStoreName.getSelectedItem().toString();
            String selectedCategory = spinnerCategory.getSelectedItem().toString();

            // adaugam cardul creat in baza de date si deschidem fragmentul principal
            final int[] idObject = new int[1];
            JsonParseID parser = new JsonParseID(selectedStore,selectedCategory) {
                @Override
                protected void onPostExecute(Integer integer) {
                    idObject[0] = integer;

                    Card newCard = new Card(selectedStore,cardNumber.getText().toString(),selectedCategory,idUser,idObject[0]);
                    usersWithCardsDB.getCardDAO().insertCard(newCard);

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    mainFragment mainFragment = new mainFragment();

                    fragmentTransaction.replace(R.id.container_fragment, mainFragment);
                    fragmentTransaction.commit();


                    Toast toast = Toast.makeText(getApplicationContext(), "Card added", Toast.LENGTH_SHORT);
                    toast.show();
                }
            };
            parser.execute("https://api.mocki.io/v1/81541948");
        }
    }
}