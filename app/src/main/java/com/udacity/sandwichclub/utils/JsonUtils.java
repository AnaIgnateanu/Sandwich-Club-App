package com.udacity.sandwichclub.utils;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String PLACE = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        if (json == null)
            return (null);

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject(NAME);
            mainName = name.getString(MAIN_NAME);

            JSONArray akaJSONArray = name.getJSONArray(AKA);
            alsoKnownAs = new ArrayList<>();
            int names = akaJSONArray.length();
            for (int i = 0; i < names; i++)
            {
                alsoKnownAs.add(akaJSONArray.getString(i));
            }

            placeOfOrigin = jsonObject.getString(PLACE);

            description = jsonObject.getString(DESCRIPTION);

            image = jsonObject.getString(IMAGE);

            JSONArray ingredientsJSONArray = jsonObject.getJSONArray(INGREDIENTS);
            ingredients = new ArrayList<>();
            int numberOfIngredients = ingredientsJSONArray.length();
            for (int i = 0; i < numberOfIngredients; i++)
            {
                ingredients.add(ingredientsJSONArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                    image, ingredients);
        } catch (JSONException e) {
            Log.e(JsonUtils.class.getName(), "Problem parsing JSON", e);
        }

        return (sandwich);
    }
}
