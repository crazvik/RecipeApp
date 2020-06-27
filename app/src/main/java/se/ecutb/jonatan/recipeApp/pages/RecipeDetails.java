 package se.ecutb.jonatan.recipeApp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import se.ecutb.jonatan.recipeApp.assignment3.R;

 public class RecipeDetails extends AppCompatActivity {
     private int cookTime;
     private String recipeName, recipeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int apiID = getIntent().getIntExtra("apiID", 0);
        recipeName = getIntent().getStringExtra("Name");
        recipeType = getIntent().getStringExtra("Type");
        cookTime = getIntent().getIntExtra("Time", 0);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, " https://www.sjjg.uk/eat/recipe-details/" + apiID, null,
            new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    TextView foodDescription = findViewById(R.id.txtDescription);
                    TextView foodIngredients = findViewById(R.id.txtIngredients);
                    TextView foodName = findViewById(R.id.txtRecipeName);
                    TextView foodSteps = findViewById(R.id.txtSteps);
                    TextView foodType = findViewById(R.id.txtType);
                    TextView foodTime = findViewById(R.id.txtTime);
                    try {
                        foodDescription.setText(response.getString("description"));
                        StringBuilder steps = new StringBuilder();
                        for (int i = 0; i < response.getJSONArray("steps").length(); i++) {
                            steps.append(response.getJSONArray("steps").getString(i)).append("\n");
                        }
                        foodSteps.setText(steps.toString());

                        StringBuilder ingredients = new StringBuilder();
                        for (int i = 0; i < response.getJSONArray("ingredients").length(); i++) {
                            ingredients.append(response.getJSONArray("ingredients").getString(i)).append("\n");
                        }
                        foodIngredients.setText(ingredients.toString());

                        foodName.setText(recipeName);
                        foodType.setText(recipeType);
                        foodTime.setText(cookTime +" minutes");
                    } catch (JSONException ignored) {}
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
        );
        requestQueue.add(getRequest);
    }
 }