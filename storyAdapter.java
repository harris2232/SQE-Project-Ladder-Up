package com.sqe.project.ladderup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    String[] Name;
    String[] Date;
    String[] Title;
    String[] Desc;
    String[] Categ;
    String[] imageID;


    public StoryAdapter(
            String[] Name, String[] Date, String[] Title, String[] Desc, String[] Categ, String[] imageID){

        this.Name = Name;
        this.Date = Date;
        this.Title = Title;
        this.Desc = Desc;
        this.Categ = Categ;
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.single_story_layout, viewGroup, false);

        return (new StoryViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolder storyViewHolder, int i) {

        storyViewHolder.progressBar.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://ladderupsqe.appspot.com/StoryPictures")
                .child(this.imageID[i]+".jpg");
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            storyViewHolder.image.setImageBitmap(bitmap);
                            storyViewHolder.progressBar.setVisibility(View.GONE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    storyViewHolder.progressBar.setVisibility(View.GONE);
                    storyViewHolder.image.setImageResource(R.drawable.noimagefoundicon);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tempName = this.Name[i];
        storyViewHolder.name.setText(tempName);

        String tempDate = this.Date[i];
        storyViewHolder.date.setText(tempDate);

        String tempTitle = this.Title[i];
        storyViewHolder.title.setText(tempTitle);

        String tempDesc = this.Desc[i];
        storyViewHolder.desc.setText(tempDesc);

        String tempCateg = this.Categ[i];
        storyViewHolder.category.setText(tempCateg);
    }



    @Override
    public int getItemCount() {
        return this.Name.length;
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, title, desc, category;
        ImageView image;
        ProgressBar progressBar;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.singlestory_name);
            date = itemView.findViewById(R.id.singlestory_date);
            title = itemView.findViewById(R.id.singlestory_title);
            desc = itemView.findViewById(R.id.singlestory_desc);
            category = itemView.findViewById(R.id.singlestory_category);
            image = itemView.findViewById(R.id.singlestory_image);
            progressBar = itemView.findViewById(R.id.progressbar_storyLoad);
        }
    }
}
