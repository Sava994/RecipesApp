package android.singidunum.ac.recipesapp.meals;

import android.content.Context;
import android.singidunum.ac.recipesapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public MealAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(Meal object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        MealHolder mealHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_meals, parent, false);
            mealHolder = new MealHolder();
            mealHolder.tx_name = (TextView) row.findViewById(R.id.mealName);
            mealHolder.image = (ImageView) row.findViewById(R.id.imageMeals);

            row.setTag(mealHolder);
        } else {
            mealHolder = (MealHolder) row.getTag();
        }
        Meal meal =(Meal) this.getItem(position);
        mealHolder.tx_name.setText(meal.getName());

        //uccitavamo sliku
        Picasso.get().load(meal.getImage()).error(R.mipmap.ic_launcher).into(mealHolder.image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        
        
        return row;
    }

    static class MealHolder {
        TextView tx_name;
        ImageView image;
    }
}
