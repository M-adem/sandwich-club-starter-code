package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView textViewAlsoKnownAsTv;
    private TextView textViewPlaceOfOriginTv;
    private TextView textViewDescriptionTv;
    private TextView textViewIngredientsTv;
    private LinearLayout groupeAlsoKnowsTV;
    private LinearLayout groupeIngredientsTV;
    private LinearLayout groupeOriginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        textViewAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        textViewPlaceOfOriginTv = findViewById(R.id.origin_tv);
        textViewDescriptionTv = findViewById(R.id.description_tv);
        textViewIngredientsTv = findViewById(R.id.ingredients_tv);
        groupeAlsoKnowsTV = findViewById(R.id.also_known_tv_groupe);
        groupeIngredientsTV = findViewById(R.id.ingredients_groupe);
        groupeOriginTV = findViewById(R.id.origin_tv_groupe);
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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        String description = sandwich.getDescription();

        textViewDescriptionTv.setText(description);


        textViewPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());


        if (textViewPlaceOfOriginTv.getText().length() == 0) {
            groupeOriginTV.setVisibility(View.GONE);
        }


        for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
            textViewAlsoKnownAsTv.append(alsoKnownAs + " ,");
        }

        if (textViewAlsoKnownAsTv.getText().length() != 0) {
            String tViewAKATV = textViewAlsoKnownAsTv.getText().toString();
            textViewAlsoKnownAsTv.setText(tViewAKATV.substring(0, textViewAlsoKnownAsTv.getText().toString().length() - 1));
            textViewAlsoKnownAsTv.append(".");
        } else {
            groupeAlsoKnowsTV.setVisibility(View.GONE);
        }


        for (String ingredient : sandwich.getIngredients()) {
            textViewIngredientsTv.append(ingredient + " ,");
        }

        if (textViewIngredientsTv.getText().length() != 0) {
            String tViewIngredientsTv = textViewIngredientsTv.getText().toString();
            textViewIngredientsTv.setText(tViewIngredientsTv.substring(0, tViewIngredientsTv.length() - 1));
            textViewIngredientsTv.append(".");
        } else {
            groupeIngredientsTV.setVisibility(View.GONE);

        }
    }

}
