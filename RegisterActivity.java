package com.sqe.project.ladderup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText newName, newEmail, newPassword, newConfirmPassword;
    Button btn_register;
    TextView loginLink;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        newName             = findViewById(R.id.register_name);
        newEmail            = findViewById(R.id.register_email);
        newPassword         = findViewById(R.id.register_password);
        newConfirmPassword  = findViewById(R.id.register_cnfpassword);
        btn_register        = findViewById(R.id.register_btnRegister);
        loginLink           = findViewById(R.id.register_LoginNow);
        progressBar = findViewById(R.id.register_progressBar);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputCredentials()){

                    registerUser();

                    return;
                }
                else{
                    return;
                }
            }
        });
    }

    private Boolean checkInputCredentials(){
        String Name             = newName.getText().toString();
        String Email            = newEmail.getText().toString().trim();
        String Password         = newPassword.getText().toString().trim();
        String confirmPassword  = newConfirmPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Email) &&
        !TextUtils.isEmpty(Password) && !TextUtils.isEmpty(confirmPassword)){

            if(Password.length() >= 6){
                if(Password.equals(confirmPassword)){
                    return true;
                }
                else{
                    toastMessage("Password does not match!");
                    return false;
                }
            }
            else{
                toastMessage("Password must be greater than 6 characters");
                return false;
            }
        }
        else{
            toastMessage("Please Fill-out all the fields!");
            return false;
        }

    }

    private void registerUser(){
        final String Name             = newName.getText().toString();
        final String Email            = newEmail.getText().toString().trim();
        final String Password         = newPassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            String id = databaseReference.push().getKey();
                            newUser newuser = new newUser(id, Name, Email, Password);
                            databaseReference.child(id).setValue(newuser);
                            toastMessage("Registration Successful! Please Login now.");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            toastMessage("Registration un-successful!");
                        }
                    }
                });
    }

    private void toastMessage(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
