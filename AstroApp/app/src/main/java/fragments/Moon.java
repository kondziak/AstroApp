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

public class Moon extends Fragment {

    public static TextView moonrise,moonset,newMoon,fullMoon,lunarPhase,lunarDay;
    Handler handMe = new Handler();
    public static boolean selected = false;
    public Moon() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.moon_activity, container, false);
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

    @Override
    public void onPause() {
        super.onPause();
        handMe.removeCallbacksAndMessages(null);
    }

    public static AstroCalculator astroInit(){
        AstroConfig configuration = new AstroConfig();
        configuration.setCalculator();
        AstroCalculator calculator = configuration.getCalculator();
        return calculator;
    }

    public void init(View v){
        moonrise = v.findViewById(R.id.moonriseField);
        moonset = v.findViewById(R.id.moonsetField);
        newMoon = v.findViewById(R.id.newMoonField);
        fullMoon = v.findViewById(R.id.fullMoonField);
        lunarPhase = v.findViewById(R.id.lunarPhaseField);
        lunarDay = v.findViewById(R.id.lunarDayField);
    }

    public static void initCalculator(){
        AstroCalculator calculator = astroInit();
        String[] splitMessage = calculator.getMoonInfo().getMoonrise().toString().split(" ");
        moonrise.setText(splitMessage[0] + " " + splitMessage[1]);
        splitMessage = calculator.getMoonInfo().getMoonset().toString().split(" ");
        moonset.setText(splitMessage[0] + " " + splitMessage[1]);
        splitMessage = calculator.getMoonInfo().getNextNewMoon().toString().split(" ");
        newMoon.setText(splitMessage[0] + " " + splitMessage[1]);
        splitMessage = calculator.getMoonInfo().getNextFullMoon().toString().split(" ");
        fullMoon.setText(splitMessage[0] + " " + splitMessage[1]);
        lunarPhase.setText(String.valueOf((int)(calculator.getMoonInfo().getIllumination() * 100)) + "%");
        lunarDay.setText(String.valueOf((int)calculator.getMoonInfo().getAge())+ "th Day");
    }

    public boolean getFlag() { return getResources().getBoolean(R.bool.isBig);}

    public void handlePhone(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(MainApp.activeThread){
                        Thread.sleep(1000 * SettingsActivity.timeRefresh);
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

    public void setSelected(boolean flag){
        selected = flag;
    }

}