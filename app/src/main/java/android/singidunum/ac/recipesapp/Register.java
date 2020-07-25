package android.singidunum.ac.recipesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText inputFullName, inputEmail, inputPassword;
    Button buttonRegister;
    TextView labelLoginHere;
    FirebaseAuth fAuth;
    ProgressBar progressBarRegister;


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
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

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
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}