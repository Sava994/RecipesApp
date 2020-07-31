package android.singidunum.ac.recipesapp.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.singidunum.ac.recipesapp.MainActivity;
import android.singidunum.ac.recipesapp.R;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText inputFullName, inputEmail, inputPassword;
    Button buttonRegister;
    TextView labelLoginHere;
    FirebaseAuth fAuth;
    ProgressBar progressBarRegister;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = findViewById(R.id.inputFullName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        buttonRegister = findViewById(R.id.buttonLogin);
        labelLoginHere = findViewById(R.id.labelRegisterHere);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBarRegister = findViewById(R.id.progressBarRegister);

        //ako vec postoji korisnik nema potrebe da mu pokazujemo register
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        //kada pritisnemo dugme registruj se
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String fullName = inputFullName.getText().toString();

                //proveravamo da li je email praza kao i password  i ispisujemo poruku ako jeste
                if(TextUtils.isEmpty(email)) {
                    inputEmail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password) || password.length() < 6) {
                    inputPassword.setError("Password is required and must have more than 6 characters.");
                    return;
                }

                //postavljamo progresBar na vidljivo zato sto je podesen da bude nevidljiv
                progressBarRegister.setVisibility(View.VISIBLE);

                //registrujemo korisnika u firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //proveravamo da li je zadatak(registracija) uspesno obavljena
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created.", Toast.LENGTH_SHORT).show();
                            //uzimamo user id i po njemu cemo da se orijentisemo u bazi kao PK
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("user").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full name",fullName);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            //saljemo korisnika na mainActivity posto je uspesno kreiran
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(Register.this, "Error! " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarRegister.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        labelLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}