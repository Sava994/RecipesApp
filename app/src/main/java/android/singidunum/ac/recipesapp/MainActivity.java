package android.singidunum.ac.recipesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.singidunum.ac.recipesapp.fragments.HomeFragment;
import android.singidunum.ac.recipesapp.fragments.ProfileFragment;
import android.singidunum.ac.recipesapp.login_register.Login;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //dodajemo meni dugme u gornjem levom cosku
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //rotiranje meni opcije
        toggle.syncState();

        //ucitavanje podataka
        loadData();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit(); // kada pokrenemo aktivnost prvo ce prikazivati home kao standardno
        navigationView.setCheckedItem(R.id.nav_home);
    }

    //uzimamo podatke iz baze o korisniku i ucitavamo ih u navigacioni meni
    private void loadData() {
        final TextView fullName;
        final TextView email;
        View nvViwe;
        String userId;
        FirebaseFirestore fStore;
        FirebaseAuth fAuth;

        NavigationView nv = findViewById(R.id.nav_view);
        nvViwe = nv.getHeaderView(0);
        fullName = (TextView) nvViwe.findViewById(R.id.fullName);
        email = (TextView) nvViwe.findViewById(R.id.userEmail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("user").document(userId);

        //postavljamo ime i prezime kao i email u navigacioni meni
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e!=null) {
                    Log.d("TAG","Error:"+e.getMessage());
                }
                else {
                    fullName.setText(documentSnapshot.getString("Full name"));
                    email.setText(documentSnapshot.getString("email"));
                }
            }
        });
    }

    //kada pritisnemo zelimo da bude selektovano zato je true, i na osnovu selektovanog izvrsava se nesto
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit(); //pokrecemo home fragment i menjamo ga sa fragment_container koji je frameLayout i nalazi se u activity_main
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_logOut:
                logout();
                Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_SHORT).show(); // ispisujemo poruku kada se korisnik izloguje
                break;
        }

        drawer.closeDrawer(GravityCompat.START); // zatvaramo nav meni nakon selektovanja

        return true;
    }

    //pritiskom na dugme back ne izlazimo iz aplikacije cele vec samo iz navigacije(da je uz desnu stranu navigacije pozivali bismo .END
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          super.onBackPressed();
        }
    }


    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}