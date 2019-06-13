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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText newName, newEmail, newPassword, newConfirmPassword;
    Button btn_register;
    TextView loginLink;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseUser user;

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
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

    //Check if the inputs are according to the standard and right
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

    //add the user using the following function
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
                            user = mAuth.getCurrentUser();
                            newUser newuser = new newUser(Name, Email, Password);
                            databaseReference.child(user.getUid()).setValue(newuser);
                            toastMessage("Registration Successful! Please set profile picture now");
                            Bundle userID = new Bundle();
                            userID.putString("userID", user.getUid());
                            Intent gotoPictureUpload = new Intent(RegisterActivity.this, ProfilePictureUpload.class);
                            gotoPictureUpload.putExtras(userID);
                            startActivity(gotoPictureUpload);
                            finish();
                        }
                        else {
                            toastMessage("Registration un-successful!");
                        }
                    }
                });
    }

    //generate toast message function used often
    private void toastMessage(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
