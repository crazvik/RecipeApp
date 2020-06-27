package se.ecutb.jonatan.recipeApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import se.ecutb.jonatan.recipeApp.assignment3.R;
import se.ecutb.jonatan.recipeApp.pages.Favorites;
import se.ecutb.jonatan.recipeApp.pages.Home;
import se.ecutb.jonatan.recipeApp.pages.Preferences;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    final Favorites favorites = new Favorites();
    final Preferences preferences = new Preferences();
    final Home home = new Home();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public String recipeType, cookTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BottomNavigationView)findViewById(R.id.botttomNav)).setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.frag_frame, home).commit();
        }

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        recipeType = sharedPreferences.getString("Meal Type", "Breakfast");
        cookTime = sharedPreferences.getString("Time", "asc");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.home:
                transaction.replace(R.id.frag_frame, home);
                break;
            case R.id.preferences:
                transaction.replace(R.id.frag_frame, preferences);
                break;
            case R.id.favorites:
                transaction.replace(R.id.frag_frame, favorites);
                break;
        }
        transaction.commit();
        return true;
    }
}