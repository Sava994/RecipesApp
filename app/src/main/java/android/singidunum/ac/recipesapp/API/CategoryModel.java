package android.singidunum.ac.recipesapp.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class CategoryModel {
    String strCategory;

    public CategoryModel() {}

    public CategoryModel(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public static CategoryModel parseJSONObject(JSONObject object) {
        CategoryModel category = new CategoryModel();
        try {
            if(object.has("strCategory")) {
                category.setStrCategory(object.getString("strCategory"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    public static LinkedList<CategoryModel> parseJSONArray(JSONArray array) {
        LinkedList<CategoryModel> list = new LinkedList<>();

        try {
            for(int i=0; i<array.length(); i++) {
                CategoryModel category = parseJSONObject(array.getJSONObject(i));
                list.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}

