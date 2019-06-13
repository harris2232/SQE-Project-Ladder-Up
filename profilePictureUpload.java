package com.sqe.project.ladderup;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfilePictureUpload extends AppCompatActivity {

    Button upload, choose;
    ImageView image;
    StorageReference storageReference;
    Uri imageuri;
    ProgressBar progressBar;
    String userID;

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.activity_profilepictureupload);

        upload = findViewById(R.id.upload_button);
        choose = findViewById(R.id.choose_button);
        upload.setVisibility(View.GONE);
        image = findViewById(R.id.image_imageview);
        progressBar = findViewById(R.id.upload_progressbar);
        userID = getIntent().getExtras().getString("userID");
        storageReference = FirebaseStorage.getInstance().getReference("ProfilePictures");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileUploader();


            }
        });

    }

    private String getExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void fileUploader() {

        StorageReference ref = storageReference.child(userID+"."+getExtension(imageuri));
        progressBar.setVisibility(View.VISIBLE);
        ref.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfilePictureUpload.this,
                                "Profile Picture has been set. Please Login now!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ProfilePictureUpload.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(ProfilePictureUpload.this,
                                "error: "+ exception, Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageuri = data.getData();
            image.setImageURI(imageuri);
            upload.setVisibility(View.VISIBLE);
        }
    }

}
