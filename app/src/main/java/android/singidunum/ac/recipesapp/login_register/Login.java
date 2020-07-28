package android.singidunum.ac.recipesapp.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.singidunum.ac.recipesapp.MainActivity;
import android.singidunum.ac.recipesapp.R;
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

public class Login extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button buttonLogin;
    TextView labelRegisterHere;
    FirebaseAuth fAuth;
    ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        fAuth = FirebaseAuth.getInstance();
        buttonLogin = findViewById(R.id.buttonLogin);
        labelRegisterHere = findViewById(R.id.labelRegisterHere);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
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

                progressBarLogin.setVisibility(View.VISIBLE);

                // proveravamo da li korisnik postoji
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            //saljemo korisnika na mainActivity posto je uspesno kreiran
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error! " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        labelRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}