package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            //parsing jsonObject from String to fetch data from it.
            JSONObject object = new JSONObject(json);

            //parsing name object from the json object to fetch more details from it.
            JSONObject nameObj = object.getJSONObject("name");

            //fetching the main name string.
            String mainName = nameObj.getString("mainName");

            //fetching all other local names or alias names.
            JSONArray alsoKnownArr = nameObj.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for ( int i = 0 ; i < alsoKnownArr.length() ; i++ )
                alsoKnownAs.add(i,alsoKnownArr.getString(i));

            //fetching place of origin.
            String placeOfOrigin = object.getString("placeOfOrigin");

            //fetching description for the Sandwich
            String description = object.getString("description");

            //fetching url-string for image
            String image = object.getString("image");

            //fetching ingredients required for the recipe
            JSONArray ingredientsArr = object.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for ( int i = 0 ; i < ingredientsArr.length() ; i++ )
                ingredients.add(i,ingredientsArr.getString(i));

            //putting all the data in sandwich object.
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setImage(image);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setIngredients(ingredients);
            sandwich.setDescription(description);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //returning the sandwich object
        return sandwich;
    }
}
