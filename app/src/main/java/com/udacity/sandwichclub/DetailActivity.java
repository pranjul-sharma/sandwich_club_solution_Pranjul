package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView imageDetailIv;
    private TextView originTv, ingredientsTv, alsoKnownAsTv, descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageDetailIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageDetailIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        originTv.setText(sandwich.getPlaceOfOrigin().equals("") ? getString(R.string.placeholder) : sandwich.getPlaceOfOrigin());
        StringBuilder stringBuilder = new StringBuilder();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        List<String> ingredients = sandwich.getIngredients();

        for (int i=0;i < alsoKnownAs.size() ; i++ )
            stringBuilder.append(i+1+". ").append(alsoKnownAs.get(i)).append("\n");

        alsoKnownAsTv.setText(stringBuilder.toString().equals("") ? getString(R.string.placeholder) : stringBuilder.toString());
        stringBuilder.delete(0,stringBuilder.length());

        for (int i=0;i < ingredients.size() ; i++ )
            stringBuilder.append(i+1+". ").append(ingredients.get(i)).append("\n");

        ingredientsTv.setText(stringBuilder.toString().equals("") ? getString(R.string.placeholder) : stringBuilder.toString());

        descriptionTv.setText(sandwich.getDescription().equals("") ? getString(R.string.placeholder) : sandwich.getDescription());

    }
}
