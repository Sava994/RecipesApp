package android.singidunum.ac.recipesapp.meals;

import android.os.Bundle;
import android.os.Message;
import android.singidunum.ac.recipesapp.API.Api;
import android.singidunum.ac.recipesapp.API.MealsCategory;
import android.singidunum.ac.recipesapp.API.ReadDataHandler;
import android.singidunum.ac.recipesapp.R;
import android.widget.ListView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.LinkedList;

public class MealsPage extends AppCompatActivity {

    ListView listView;
    private String url = "";
    MealAdapter mealAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        mealAdapter = new MealAdapter(this, R.layout.row_meals);
        listView = findViewById(R.id.listView);
        listView.setAdapter(mealAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
        } else {
        }

        listView = findViewById(R.id.listView);

        initMeals();
    }


    private void initMeals() {
        Api.getJSON(url, new ReadDataHandler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                try {
                    String answer = getJson();
                    JSONObject jsonObject = new JSONObject(answer);
                    JSONArray array = jsonObject.getJSONArray("meals");
                    LinkedList<MealsCategory> meals = MealsCategory.parseJSONArray(array);

                    String name,image,id;

                    for(MealsCategory meal : meals) {
                        name = meal.getStrMeal();
                        image = meal.getStrMealThumb() + "/preview";
                        id = meal.getIdMeal();
                        Meal meal1 = new Meal(name,image,id);
                        mealAdapter.add(meal1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
