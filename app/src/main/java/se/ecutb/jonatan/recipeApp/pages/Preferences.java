package se.ecutb.jonatan.recipeApp.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

import se.ecutb.jonatan.recipeApp.MainActivity;
import se.ecutb.jonatan.recipeApp.assignment3.R;

public class Preferences extends Fragment {
    private String mealType;
    private String Time;
    private SharedPreferences sharedPreferences;

    public Preferences() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if(bundle != null) {
            mealType = bundle.getString("Meal Type");
            Time = bundle.getString("Time");
        }

        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        RadioButton preferenceButton1 = view.findViewById(R.id.radioDinner);
        RadioButton preferenceButton2 = view.findViewById(R.id.radioLunch);
        RadioButton preferenceButton3 = view.findViewById(R.id.radioBreakfast);
        RadioButton preferenceButton4 = view.findViewById(R.id.radioHigh);
        RadioButton preferenceButton5 = view.findViewById(R.id.radioLow);

        preferenceButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Meal Type", "Dinner");
            editor.apply();
            }
        });

        preferenceButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Meal Type", "Lunch");
            editor.apply();
            }
        });

        preferenceButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Meal Type", "Breakfast");
            editor.apply();
            }
        });

        preferenceButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Time", "desc");
            editor.apply();
            }
        });

        preferenceButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Time", "asc");
            editor.apply();
            }
        });
        return view;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("Meal Type", mealType);
        bundle.putString("Time", Time);
    }

    public void onViewCreated(View view, Bundle bundle) {
        RadioGroup cookTimeChoice = Objects.requireNonNull(getView()).findViewById(R.id.radio);
        RadioGroup recipeTypeChoice = getView().findViewById(R.id.radio2);

        switch(((MainActivity) Objects.requireNonNull(getActivity())).cookTime) {
            case "desc":
                cookTimeChoice.check(R.id.radioHigh);
                break;
            case "asc":
                cookTimeChoice.check(R.id.radioLow);
                break;
        }

        switch(((MainActivity)getActivity()).recipeType) {
            case "Breakfast":
                recipeTypeChoice.check(R.id.radioBreakfast);
                break;
            case "Lunch":
                recipeTypeChoice.check(R.id.radioLunch);
                break;
            case "Dinner":
                recipeTypeChoice.check(R.id.radioDinner);
                break;
        }
    }
}