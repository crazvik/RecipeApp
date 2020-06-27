package se.ecutb.jonatan.recipeApp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import se.ecutb.jonatan.recipeApp.Recipe;
import se.ecutb.jonatan.recipeApp.pages.RecipeDetails;
import se.ecutb.jonatan.recipeApp.assignment3.R;

public class  MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final SharedPreferences preferences;
    private final ArrayList<Recipe> recipes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView recipeName;
        public final TextView cookTime;
        public final TextView recipeType;
        public final CardView card;
        public final ImageButton imageButton;

        public ViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.tvTitle);
            cookTime = view.findViewById(R.id.tvTime);
            recipeType = view.findViewById(R.id.tvType);
            card = view.findViewById(R.id.myCardView);
            imageButton = view.findViewById(R.id.button);
        }
    }

    public MyAdapter(ArrayList<Recipe> products, SharedPreferences preferences) {
        this.recipes = products;
        this.preferences = preferences;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.recipeName.setText(recipes.get(position).getRecipeName());
        viewHolder.cookTime.setText(recipes.get(position).getCookTime() +" minutes");
        viewHolder.recipeType.setText(recipes.get(position).getRecipeType());
        if(preferences.getBoolean("apiID"+ recipes.get(position).getApiID(), false)) {
            viewHolder.imageButton.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            viewHolder.imageButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ImageButton fave = v.findViewById(R.id.button);
            SharedPreferences.Editor editor = preferences.edit();
            if(preferences.getBoolean("apiID"+ recipes.get(position).getApiID(), false)) {
                fave.setImageResource(R.drawable.ic_star_border_black_24dp);
                editor.putBoolean("apiID"+ recipes.get(position).getApiID(), false);
            } else {
                fave.setImageResource(R.drawable.ic_star_black_24dp);
                editor.putBoolean("apiID"+ recipes.get(position).getApiID(), true);
            }
            editor.apply();
            }
        });

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent( v.getContext(), RecipeDetails.class);
            intent.putExtra("apiID", recipes.get(position).getApiID());
            intent.putExtra("Name", recipes.get(position).getRecipeName());
            intent.putExtra("Type", recipes.get(position).getRecipeType());
            intent.putExtra("Time", recipes.get(position).getCookTime());
            v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}