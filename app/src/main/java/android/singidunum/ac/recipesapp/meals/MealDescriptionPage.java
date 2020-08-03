package android.singidunum.ac.recipesapp.meals;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.singidunum.ac.recipesapp.API.Api;
import android.singidunum.ac.recipesapp.API.MealDescriptionCategory;
import android.singidunum.ac.recipesapp.API.MealsCategory;
import android.singidunum.ac.recipesapp.API.ReadDataHandler;
import android.singidunum.ac.recipesapp.R;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MealDescriptionPage extends AppCompatActivity {

    String url;
    TextView textMealName,textMealAbout;
    ImageView imageSelectedMeal;
    Button buttonMealVideo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_meal);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
        } else {
        }

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
                    LinkedList<MealDescriptionCategory> meals = MealDescriptionCategory.parseJSONArray(array);

                    String name,image,description,youtube;

                    for(final MealDescriptionCategory meal : meals) {
                        name = meal.getStrMeal();
                        image = meal.getStrMealThumb() + "/preview";
                        description = meal.getStrInstructions();
                        youtube = meal.getStrYoutube();

                        imageSelectedMeal = (ImageView) findViewById(R.id.imageSelectedMeal);
                        textMealName = (TextView) findViewById(R.id.textMealName);
                        textMealAbout = (TextView) findViewById(R.id.textMealAbout);
                        buttonMealVideo = (Button) findViewById(R.id.buttonMealVideo);

                        textMealAbout.setText(meal.getStrInstructions());
                        textMealName.setText(meal.getStrMeal());
                        buttonMealVideo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(meal.getStrYoutube()));
                                startActivity(appIntent);
                            }
                        });

                        Picasso.get().load(meal.getStrMealThumb()).error(R.mipmap.ic_launcher).into(imageSelectedMeal, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
