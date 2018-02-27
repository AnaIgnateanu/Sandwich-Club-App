package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

    private TextView originTextView;
    private TextView originLabelTextView;
    private TextView ingredientsTextView;
    private TextView descriptionTextView;
    private TextView akaTextView;
    private TextView akaLabelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTextView = findViewById(R.id.origin_tv);
        originLabelTextView = findViewById(R.id.origin_label_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        akaTextView = findViewById(R.id.also_known_tv);
        akaLabelTextView = findViewById(R.id.aka_label_tv);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            originTextView.setVisibility(View.GONE);
            originLabelTextView.setVisibility(View.GONE);
        }
        else {
            originLabelTextView.setVisibility(View.VISIBLE);
            originTextView.setVisibility(View.VISIBLE);
            originTextView.setText(origin);
        }

        String description = sandwich.getDescription();
        if (description.isEmpty())
            descriptionTextView.setVisibility(View.GONE);
        else {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(description);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.size() == 0)
            ingredientsTextView.setVisibility(View.GONE);
        else {
            ingredientsTextView.setVisibility(View.VISIBLE);
            String ingredients = TextUtils.join(", ", ingredientsList);
            ingredientsTextView.setText(ingredients);
        }

        List<String> akaList = sandwich.getAlsoKnownAs();
        if (akaList.size() == 0) {
            akaLabelTextView.setVisibility(View.GONE);
            akaTextView.setVisibility((View.GONE));
        }
        else {
            akaLabelTextView.setVisibility(View.VISIBLE);
            akaTextView.setVisibility((View.VISIBLE));
            String aka = TextUtils.join(", ", akaList);
            akaTextView.setText(aka);
        }

    }
}
