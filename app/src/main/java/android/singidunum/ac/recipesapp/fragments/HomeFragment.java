package android.singidunum.ac.recipesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.singidunum.ac.recipesapp.API.Api;
import android.singidunum.ac.recipesapp.API.CategoryModel;
import android.singidunum.ac.recipesapp.API.MealsCategory;
import android.singidunum.ac.recipesapp.API.ReadDataHandler;
import android.singidunum.ac.recipesapp.R;
import android.singidunum.ac.recipesapp.login_register.Login;
import android.singidunum.ac.recipesapp.meals.MealsPage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;


public class HomeFragment extends Fragment {
    private View v;

    private Button buttonSearch ;
    private Spinner spinnerCategory;
    private String chosen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        initCategorys();

        buttonSearch = (Button) v.findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ako je selektovano dugme
                if(v.getId() == R.id.buttonSearch) {
                    chosen = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" + (String) spinnerCategory.getSelectedItem();
                    Context context = getContext();
                    Intent intent = new Intent(getActivity(), MealsPage.class);
                    intent.putExtra("url", chosen);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    //ucitavamo podatke saljemo ih na parsiranje i obradu i onda ih upisujemo u listu i stavljamo u spiner
    public void initCategorys() {
        Api.getJSON("https://www.themealdb.com/api/json/v1/1/list.php?c=list", new ReadDataHandler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                try {
                    String answer = getJson();
                    JSONObject jsonObject = new JSONObject(answer);
                    JSONArray array = jsonObject.getJSONArray("meals");
                    LinkedList<CategoryModel> categoryes = CategoryModel.parseJSONArray(array);


                    spinnerCategory = (Spinner) v.findViewById(R.id.spinnerCategory);
                    ArrayList<String> showCategory = new ArrayList<>();

                    for(CategoryModel category : categoryes) {
                        showCategory.add(category.getStrCategory());
                    }

                    Context context = getContext();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, showCategory);
                    spinnerCategory.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
