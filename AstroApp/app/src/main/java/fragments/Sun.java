package fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.astroapp.MainApp;
import com.example.astroapp.R;
import com.example.astroapp.SettingsActivity;


import astroConfig.AstroConfig;

public class Sun extends Fragment {

    TextView firstAnswer,secondAnswer,thirdAnswer,fourthAnswer;
    Handler handMe = new Handler();
    public static boolean selected = false;

    public Sun() {
    }


    @Override
    public void onPause() {
        super.onPause();
        handMe.removeCallbacksAndMessages(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.sun_activity, container, false);
        init(v);
        initCalculator();
        if(getFlag() == false){
            handlePhone();
        }
        else{
            handleTablet();
        }

        return v;
    }

    public static AstroCalculator astroInit(){
        AstroConfig configuration = new AstroConfig();
        configuration.setCalculator();
        AstroCalculator calculator = configuration.getCalculator();
        return calculator;
    }

    public void init(View v){
        firstAnswer = v.findViewById(R.id.firstAnswer);
        secondAnswer = v.findViewById(R.id.secondAnswer);
        thirdAnswer = v.findViewById(R.id.thirdAnswer);
        fourthAnswer = v.findViewById(R.id.fourthAnswer);
    }

    public void initCalculator(){
        AstroCalculator calculator = astroInit();
        Double format = calculator.getSunInfo().getAzimuthRise();
        String[] splitMessage = calculator.getSunInfo().getSunrise().toString().split(" ");
        firstAnswer.setText(splitMessage[0] + " " + splitMessage[1] + "    Azimuth: " + String.format("%.3f",format));
        splitMessage = calculator.getSunInfo().getSunset().toString().split(" ");
        format = calculator.getSunInfo().getAzimuthSet();
        secondAnswer.setText(splitMessage[0] + " " + splitMessage[1] + "    Azimuth: " + String.format("%.3f",format));
        splitMessage = calculator.getSunInfo().getTwilightMorning().toString().split(" ");
        thirdAnswer.setText(splitMessage[0] + " " + splitMessage[1]);
        splitMessage = calculator.getSunInfo().getTwilightEvening().toString().split(" ");
        fourthAnswer.setText(splitMessage[0] + " " + splitMessage[1]);
    }

    public void handlePhone(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(MainApp.activeThread){
                        Thread.sleep(1000 * 2);
                        if(MainApp.activeThread == false) break;
                        if(selected == false) continue;
                        handMe.post(new Runnable() {
                            @Override
                            public void run() {
                                initCalculator();
                                if(getActivity() != null){
                                    Toast t = Toast.makeText(getActivity(),"Refresh",Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });
                    }
                }catch(Exception exp){
                    System.out.println(exp.getLocalizedMessage());
                }
            }
        }).start();
    }

    public void handleTablet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(MainApp.activeThread){
                        Thread.sleep(1000 * SettingsActivity.timeRefresh);
                        if(MainApp.activeThread == false) break;
                        handMe.post(new Runnable() {
                            @Override
                            public void run() {
                                initCalculator();
                            }
                        });
                    }
                }catch(Exception exp){
                    System.out.println(exp.getLocalizedMessage());
                }
            }
        }).start();
    }

    public boolean getFlag() { return getResources().getBoolean(R.bool.isBig);}

    public void setSelected(boolean flag){
        selected = flag;
    }

}