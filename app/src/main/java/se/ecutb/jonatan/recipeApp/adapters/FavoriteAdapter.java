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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final SharedPreferences sharedPreferences;
    private final ArrayList<Recipe> recipes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView time;
        public final TextView type;
        public final CardView card;
        public final ImageButton fave;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            time = view.findViewById(R.id.tvTime);
            type = view.findViewById(R.id.tvType);
            card = view.findViewById(R.id.myCardView);
            fave = view.findViewById(R.id.button);
        }
    }

    public FavoriteAdapter(ArrayList<Recipe> recipes, SharedPreferences sharedPreferences) {
        this.recipes = recipes;
        this.sharedPreferences = sharedPreferences;
    }

    @Override

    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new FavoriteAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(recipes.get(position).getRecipeName());
        viewHolder.time.setText(recipes.get(position).getCookTime() +" minutes");
        viewHolder.type.setText(recipes.get(position).getRecipeType());
        viewHolder.fave.setImageResource(R.drawable.ic_star_black_24dp);

        viewHolder.fave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ImageButton fave = v.findViewById(R.id.button);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(sharedPreferences.getBoolean("apiID" + recipes.get(position).getApiID(), false)) {
                editor.putBoolean("apiID"+ recipes.get(position).getApiID(), false);
                recipes.remove(position);
                notifyItemRemoved(position);
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