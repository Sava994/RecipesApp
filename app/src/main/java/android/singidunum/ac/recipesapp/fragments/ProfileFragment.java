package android.singidunum.ac.recipesapp.fragments;


import android.os.Bundle;
import android.singidunum.ac.recipesapp.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {


    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        loadData();

        return view;
    }


    private void loadData() {
        final TextView fullNameProfile;
        final TextView emailProfile;
        String userId;
        FirebaseFirestore fStore;
        FirebaseAuth fAuth;


        fullNameProfile = (TextView) view.findViewById(R.id.fullNameProfile);
        emailProfile = (TextView) view.findViewById(R.id.emailProfile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("user").document(userId);
        //postavljamo ime i prezime kao i email u navigacioni meni
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null) {
                    Log.d("TAG","Error:"+e.getMessage());
                }
                else {
                    fullNameProfile.setText(documentSnapshot.getString("Full name"));
                    emailProfile.setText(documentSnapshot.getString("email"));
                }
            }
        });
    }





}
