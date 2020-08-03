package android.singidunum.ac.recipesapp.meals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.singidunum.ac.recipesapp.API.Api;
import android.singidunum.ac.recipesapp.API.MealsCategory;
import android.singidunum.ac.recipesapp.API.ReadDataHandler;
import android.singidunum.ac.recipesapp.MainActivity;
import android.singidunum.ac.recipesapp.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MealsPage extends AppCompatActivity {

    ListView listView;
    private String url = "";
    MealAdapter mealAdapter;
    String chosen;

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

        initMeals();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal meal =(Meal) mealAdapter.getItem(position);
                chosen = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + meal.getId();
                Intent intent = new Intent(MealsPage.this,MealDescriptionPage.class);
                intent.putExtra("position",listView.getItemAtPosition(position).toString());
                intent.putExtra("url", chosen);
                startActivity(intent);
            }
        });
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
