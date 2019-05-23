package com.sqe.project.ladderup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView forgot_password, registerLink;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        login = findViewById(R.id.login_btnLogin);
        forgot_password = findViewById(R.id.login_forgotPass);
        registerLink = findViewById(R.id.login_registerNow);
        progressbar = findViewById(R.id.login_progressbar);
        mAuth = FirebaseAuth.getInstance();

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please contact support", Toast.LENGTH_LONG).show();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().trim().length() == 0){
                    toastMessage("Please write Email");
                    return;
                }

                if(password.getText().toString().trim().length() == 0){
                    toastMessage("Please write Password");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),
                        password.getText().toString().trim())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    toastMessage("Login Successful");

                                } else {

                                    toastMessage("An Error Occurred while Logging-In");
                                }

                            }
                        });
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    void toastMessage(String Message){
        Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_SHORT).show();
    }

}
