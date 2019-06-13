package com.sqe.project.ladderup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YourFeedTab extends Fragment {

    int count =0;
    String mName;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_yourfeed, container, false);

        mName = getArguments().getString("Name");
        recyclerView = view.findViewById(R.id.yourfeed_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        loadStories();



        return view;
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void loadStories(){
        DatabaseReference getCount = FirebaseDatabase.getInstance().getReference("Story");
        getCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (mName.equals(ds.child("uploaderID").getValue().toString())) {

                        count++;

                    }
                }
                if(count >0){
                    populateStories();
                }
                else{
                    Snackbar.make(getView(), R.string.noYourStoryMessage, Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.WHITE).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastMessage("Something went wrong!");
            }
        });

    }

    private void populateStories(){
        DatabaseReference getStories = FirebaseDatabase.getInstance().getReference("Story");
        getStories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int index = count - 1;

                String[] Name = new String[count];
                String[] Date = new String[count];
                String[] Title = new String[count];
                String[] Category = new String[count];
                String[] Description = new String[count];
                String[] imageID = new String[count];

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (mName.equals(ds.child("uploaderID").getValue().toString())) {
                        Date[index] = ds.child("date").getValue().toString();
                        Title[index] = ds.child("title").getValue().toString();
                        Name[index] = ds.child("uploaderID").getValue().toString();
                        Category[index] = ds.child("category").getValue().toString();
                        Description[index] = ds.child("description").getValue().toString();
                        imageID[index] = ds.child("imageID").getValue().toString();
                        index--;
                    }
                }
                StoryAdapter storyAdapter = new StoryAdapter(Name, Date, Title, Description, Category, imageID );
                recyclerView.setAdapter(storyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastMessage("Something went wrong!");
            }
        });
    }
}
