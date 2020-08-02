package android.singidunum.ac.recipesapp.fragments;

import android.os.Bundle;
import android.os.Message;
import android.singidunum.ac.recipesapp.API.Api;
import android.singidunum.ac.recipesapp.API.CategoryModel;
import android.singidunum.ac.recipesapp.API.ReadDataHandler;
import android.singidunum.ac.recipesapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class HomeFragment extends Fragment {
    private View v;
    private TextView text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        initCategorys();

        return v;
    }

    public void initCategorys() {
        Api.getJSON("https://www.themealdb.com/api/json/v1/1/list.php?c=list", new ReadDataHandler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                try {
                    String answer = getJson();
                    JSONObject jsonObject = new JSONObject(answer);
                    JSONArray array = jsonObject.getJSONArray("meals");
                    LinkedList<CategoryModel> categoryes = CategoryModel.parseJSONArray(array);

                    text = (TextView) v.findViewById(R.id.textApi);
                    text.setText("Kategorije:\n\n");

                    for(CategoryModel category : categoryes) {
                        String showCategory = category.getStrCategory() + "\n";
                        text.append(showCategory);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        text = (TextView) v.findViewById(R.id.textApi);
        text.setText("Ucitava se ...");
    }

}
