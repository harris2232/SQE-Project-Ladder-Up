package com.sqe.project.ladderup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class newStoryUpload extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    EditText Title, Desc;
    Button Upload, Cancel;
    Spinner Categories;
    String uploaderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story_upload);
        firebaseAuth = FirebaseAuth.getInstance();
        Title = findViewById(R.id.uploadStory_title);
        Desc = findViewById(R.id.uploadStory_desc);
        Upload = findViewById(R.id.uploadStory_uploadBtn);
        Cancel = findViewById(R.id.uploadStory_cancelBtn);
        Categories = findViewById(R.id.uploadStory_spinner);
        uploaderName = getIntent().getExtras().getString("username");

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storyID = addNewStory(Title.getText().toString(), Desc.getText().toString());
                Intent intent = new Intent(newStoryUpload.this, storyPictureUpload.class);
                Bundle bundle = new Bundle();
                bundle.putString("story id", storyID);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });


    }

    private String addNewStory(String title, String description){

        databaseReference = FirebaseDatabase.getInstance().getReference("Story");
        firebaseUser = firebaseAuth.getCurrentUser();
        String uploaderID = uploaderName;
        String storyID = databaseReference.push().getKey();
        String date = getCurrentDate();
        String imageID = storyID;
        String category = Categories.getSelectedItem().toString();
        Story story = new Story(title, description, imageID, date, uploaderID, category);
        databaseReference.child(storyID).setValue(story);
        return storyID;



    }

    private String getCurrentDate(){
        //code for getting date in MMM DD, YYYY format
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MMM dd, yyyy");
        return mdformat.format(calendar.getTime());


    }



}
