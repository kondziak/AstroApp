package astroConfig;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.astroapp.SettingsActivity;

import java.util.Calendar;
import java.util.Date;

public class AstroConfig {
    private AstroCalculator calculator;
    private AstroCalculator.Location location;
    private AstroDateTime time;

    public void setCalculator(){
        int timeZoneOffset = Calendar.getInstance().get(Calendar.ZONE_OFFSET)+1;
        boolean dayLight = Calendar.getInstance().getTimeZone().inDaylightTime(new Date());
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int seconds = Calendar.getInstance().get(Calendar.SECOND);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        time = new AstroDateTime(year,month,day,hour,minute,seconds,timeZoneOffset,dayLight);
        location = new AstroCalculator.Location(SettingsActivity.latitude,SettingsActivity.longtitude);
        calculator = new AstroCalculator(time,location);
    }

    public AstroCalculator getCalculator() {return calculator;}


}
