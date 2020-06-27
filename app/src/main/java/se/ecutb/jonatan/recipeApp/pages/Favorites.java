package se.ecutb.jonatan.recipeApp.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import se.ecutb.jonatan.recipeApp.Recipe;
import se.ecutb.jonatan.recipeApp.adapters.FavoriteAdapter;
import se.ecutb.jonatan.recipeApp.assignment3.R;

public class Favorites extends Fragment {
    private final ArrayList<Recipe> recipes = new ArrayList<>();
    private FavoriteAdapter productsAdapter;
    private SharedPreferences sharedPreferences;

    public TextView recipeName, recipeTime, recipeType;

    public Favorites() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);
        productsAdapter = new FavoriteAdapter(recipes, sharedPreferences);

        RecyclerView listView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        listView.setLayoutManager(layoutManager);
        listView.setAdapter(productsAdapter);
        onRequestProducts();

        return view;
    }

    public void onRequestProducts() {
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, "https://www.sjjg.uk/eat/food-items?prefer=" +
            sharedPreferences.getString("Meal Type", "Breakfast") + "&ordering=" + sharedPreferences.getString("Time", "asc"),null,
            new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response)
                {
                    populateList(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }
        );
        requestQueue.add(getRequest);
    }

    private void populateList(JSONArray items) {
        recipes.clear();
        recipeName = Objects.requireNonNull(getActivity()).findViewById(R.id.tvTitle);
        recipeTime = getActivity().findViewById(R.id.tvTime);
        recipeType = getActivity().findViewById(R.id.tvType);
        try {
            for (int i =0; i<items.length();i++) {
                JSONObject jsonObject = items.getJSONObject(i);
                if(sharedPreferences.getBoolean("apiID"+jsonObject.getInt("id"), false))
                    recipes.add(new Recipe(jsonObject.getInt("id"), jsonObject.getString("name"),
                                    jsonObject.getString("meal"), jsonObject.getInt("time")));
            }
        } catch(JSONException ignored) {}
        productsAdapter.notifyDataSetChanged();
    }
}