package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        try {
            String jsonMain;

            String sandwichPlaceOfOrigin;
            String sandwichDescription;
            String sandwichImage;


            JSONObject sandwichJson = new JSONObject(json);


            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            jsonMain = sandwichName.getString("mainName");
            JSONArray alsoKnownAsArray = sandwichName.getJSONArray("alsoKnownAs");

            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }


            sandwichPlaceOfOrigin = sandwichJson.getString("placeOfOrigin");


            sandwichDescription = sandwichJson.getString("description");


            sandwichImage = sandwichJson.getString("image");


            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }

            sandwich = new Sandwich(jsonMain, alsoKnownAsList, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage,
                    ingredientsList);


        } catch (JSONException e) {
            Log.e(JsonUtils.class.getSimpleName(), e.getMessage());
        }


        return sandwich;
    }
}
