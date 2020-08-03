package android.singidunum.ac.recipesapp.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MealDescriptionCategory {
    String strMeal;
    String strMealThumb;
    String strInstructions;
    String strYoutube;

    public MealDescriptionCategory() {}

    public MealDescriptionCategory(String strMeal, String strMealThumb, String strInstructions, String strYoutube) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public static MealDescriptionCategory parseJSONObject(JSONObject object) {
        MealDescriptionCategory meals = new MealDescriptionCategory();
        try {
            if(object.has("strMeal")) {
                meals.setStrMeal(object.getString("strMeal"));
            }
            if(object.has("strMealThumb")) {
                meals.setStrMealThumb(object.getString("strMealThumb"));
            }
            if(object.has("strInstructions")) {
                meals.setStrInstructions(object.getString("strInstructions"));
            }
            if(object.has("strYoutube")) {
                meals.setStrYoutube(object.getString("strYoutube"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return meals;
    }

    public static LinkedList<MealDescriptionCategory> parseJSONArray(JSONArray array) {
        LinkedList<MealDescriptionCategory> list = new LinkedList<>();

        try {
            for(int i=0; i<array.length(); i++) {
                MealDescriptionCategory meal = parseJSONObject(array.getJSONObject(i));
                list.add(meal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
