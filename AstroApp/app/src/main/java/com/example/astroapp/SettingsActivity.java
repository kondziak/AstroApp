package com.example.astroapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    public static Double longtitude = 19.4667;
    public static Double latitude = 51.7833;
    public static int timeRefresh = 5;
    private int errorCounter = 0;
    private EditText longtitudeText,latitudeText,timeText;
    private Button applyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        timeText = findViewById(R.id.timeName);
        longtitudeText = findViewById(R.id.longtitudeName);
        latitudeText = findViewById(R.id.latitudeName);
        applyButton = findViewById(R.id.applyButton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorCounter = 0;
                if(!timeText.getText().toString().isEmpty()){
                    try{
                        int num = Integer.parseInt(timeText.getText().toString());
                        if(num >= 0){
                            timeRefresh = num;
                        }
                        else{
                            timeText.requestFocus();
                            timeText.setError("Enter an unsigned integer number");
                        }
                    }catch(NumberFormatException exp){
                        timeText.requestFocus();
                        timeText.setError("Enter an integer number");
                        errorCounter++;
                    }
                }
                else{
                    timeText.requestFocus();
                    timeText.setError("FIELD CANNOT BE EMPTY");
                    errorCounter++;
                }

                if(!longtitudeText.getText().toString().isEmpty()){
                    try{
                        Double num = Double.parseDouble(longtitudeText.getText().toString());
                        if(num >= -180 && num <=180){
                            longtitude = num;
                        }
                        else{
                            longtitudeText.requestFocus();
                            longtitudeText.setError("longtitude should be in range [-180,180]");
                            errorCounter++;
                        }
                    } catch(NumberFormatException exp){
                        longtitudeText.requestFocus();
                        longtitudeText.setError("Invalid input");
                        errorCounter++;
                    }

                }
                else{
                    longtitudeText.requestFocus();
                    longtitudeText.setError("FIELD CANNOT BE EMPTY");
                    errorCounter++;
                }

                if(!latitudeText.getText().toString().isEmpty()){
                    try{
                        Double num = Double.parseDouble(latitudeText.getText().toString());
                        if(num >= -90 && num <= 90){
                            latitude = num;
                        }
                        else{
                            latitudeText.requestFocus();
                            latitudeText.setError("longtitude should be in range [-90,90]");
                            errorCounter++;
                        }
                    }catch(NumberFormatException exp){
                        latitudeText.requestFocus();
                        latitudeText.setError("Invalid input");
                        errorCounter++;
                    }
                }
                else{
                    latitudeText.requestFocus();
                    latitudeText.setError("FIELD CANNOT BE EMPTY");
                    errorCounter++;
                }
                if(errorCounter == 0){
                    Toast t = Toast.makeText(getApplicationContext(),"Changes were made",Toast.LENGTH_LONG);
                    t.show();
                }
            }

        });
    }
}
