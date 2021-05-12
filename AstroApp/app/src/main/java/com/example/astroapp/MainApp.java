package com.example.astroapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import fragments.Adapter;
import fragments.Moon;
import fragments.Sun;

public class MainApp extends AppCompatActivity {

    int positionOnPager;
    int seconds = 1000;
    ViewPager2 viewPager2;
    TextView timeView, latitudeView, longtitudeView;
    Handler handler = new Handler();
    public static boolean activeThread;
    List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        fragments = initList();
        init();
        activeThread = true;
        if(getFlag() == false){
            Adapter adapt = new Adapter(this,fragments);
            viewPager2.setAdapter(adapt);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setFlags(position);
                }
            });
            clock();
        }
        else{
            clock();
            getSupportFragmentManager().beginTransaction().add(R.id.rel1,new Sun(),"Sun").commit();
            getSupportFragmentManager().beginTransaction().add(R.id.rel2,new Moon(),"Moon").commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean getFlag() { return getResources().getBoolean(R.bool.isBig);}

    public List<Fragment> initList(){
        List<Fragment> tempFragments = new ArrayList<>();
        tempFragments.add(new Sun());
        tempFragments.add(new Moon());
        return tempFragments;
    }

    public void init(){
        setContentView(R.layout.app_layout);
        viewPager2 = findViewById(R.id.pager);
        timeView = findViewById(R.id.timeView);
        latitudeView = findViewById(R.id.latitudeView);
        longtitudeView = findViewById(R.id.longtitudeView);
        String latitudeText = "Latitude:" + SettingsActivity.latitude;
        String longtitudeText = "Longtitude:" + SettingsActivity.longtitude;
        latitudeView.setText(latitudeText);
        longtitudeView.setText(longtitudeText);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = c.getTime();
        String s = simpleDateFormat.format(date);
        timeView.setText(s);
    }


    @Override
    public void onResume() {
        super.onResume();
        activeThread = true;
        clock();
    }

    @Override
    public void onPause() {
        super.onPause();
        activeThread = false;
        handler.removeCallbacksAndMessages(null);
    }

    public void clock(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(activeThread){
                    try {
                        Thread.sleep(seconds);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                                Date date = c.getTime();
                                String s = simpleDateFormat.format(date);
                                timeView.setText(s);
                            }
                        });
                    }
                    catch (Exception ex) {
                        timeView.setText(ex.getMessage());
                    }
                }
            }
        }).start();
    }

    public void setFlags(int position){
        for(int i = 0; i < fragments.size(); i++){
            if(i == position){
                if(fragments.get(i) instanceof  Sun){
                    Sun s = (Sun) fragments.get(i);
                    s.setSelected(true);
                }
                else{
                    Moon m = (Moon) fragments.get(i);
                    m.setSelected(true);
                }
            }
            else{
                if(fragments.get(i) instanceof  Sun){
                    Sun s = (Sun) fragments.get(i);
                    s.setSelected(false);
                }
                else{
                    Moon m = (Moon) fragments.get(i);
                    m.setSelected(false);
                }
            }
        }
    }

}