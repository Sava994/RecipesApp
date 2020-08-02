package android.singidunum.ac.recipesapp.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MealsCategory {
    String strMeal;
    String strMealThumb;
    String idMeal;

    public MealsCategory() {}

    public MealsCategory(String strMeal, String strMealThumb, String idMeal) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public static MealsCategory parseJSONObject(JSONObject object) {
        MealsCategory meals = new MealsCategory();
        try {
            if(object.has("strMeal")) {
                meals.setStrMeal(object.getString("strMeal"));
            }
            if(object.has("strMealThumb")) {
                meals.setStrMealThumb(object.getString("strMealThumb"));
            }
            if(object.has("idMeal")) {
                meals.setIdMeal(object.getString("idMeal"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return meals;
    }

    public static LinkedList<MealsCategory> parseJSONArray(JSONArray array) {
        LinkedList<MealsCategory> list = new LinkedList<>();

        try {
            for(int i=0; i<array.length(); i++) {
                MealsCategory meal = parseJSONObject(array.getJSONObject(i));
                list.add(meal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
