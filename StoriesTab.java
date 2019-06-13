package com.sqe.project.ladderup;

import android.os.Bundle;
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

public class StoriesTab extends Fragment {

    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_stories, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Story");
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int viewCount = (int) dataSnapshot.getChildrenCount();//get the total number of view to be generated
                int i =viewCount-1; // generating index for each string data
                String[] name = new String[viewCount];
                String[] date = new String[viewCount];
                String[] title = new String[viewCount];
                String[] desc = new String[viewCount];
                String[] category = new String[viewCount];
                String[] imageID = new String[viewCount];

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    name[i] = ds.child("uploaderID").getValue().toString();
                    date[i] = ds.child("date").getValue().toString();
                    title[i] = ds.child("title").getValue().toString();
                    desc[i] = ds.child("description").getValue().toString();
                    category[i] = ds.child("category").getValue().toString();
                    imageID[i] = ds.child("imageID").getValue().toString();
                    i--;
                }

                StoryAdapter storyAdapter = new StoryAdapter(name, date, title, desc, category, imageID);
                recyclerView.setAdapter(storyAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                    Toast.makeText(getActivity(), "Error"+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }



}
