package com.sqe.project.ladderup;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UserActivity extends AppCompatActivity {

    Toolbar tool_bar;
    TabLayout tab_layout;
    ViewPager view_pager;
    TabItem stories;
    TabItem your_feed;
    TabItem profile;
    PageAdapter page_adapter;

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.activity_user);

        tool_bar = findViewById(R.id.toolbar_useractivity);
        tool_bar.setTitle("Dashboard");
        setSupportActionBar(tool_bar);

        tab_layout = findViewById(R.id.tab_layout);
        stories = findViewById(R.id.tab_1);
        your_feed = findViewById(R.id.tab_2);
        profile = findViewById(R.id.tab_3);
        view_pager = findViewById(R.id.view_pager);

        page_adapter = new PageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 1){
                    tool_bar.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.yourfeed));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.yourfeed));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(
                                UserActivity.this,R.color.yourfeed));
                    }
                }

                else if(tab.getPosition() == 2){
                    tool_bar.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.profile));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.profile));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(
                                UserActivity.this,R.color.profile));
                    }
                }

                else{
                    tool_bar.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.stories));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(
                            UserActivity.this, R.color.stories));

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(
                                UserActivity.this,R.color.stories));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));


    }


}
