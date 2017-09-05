package mimanor.com.gosleep.Manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;


import mimanor.com.gosleep.BroadcastReciver.SleepAlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by GD on 11/4/2017.
 */

public class AlarmManagerSetter {

    public static void SetOffScreenAlarm(Context cxt){
        SharedManager sm = new SharedManager(cxt);
        //String dim_duration = sm.read("dim");
        String dim_duration = "";

        if(dim_duration.equals("")){
            dim_duration = "1";
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,Integer.parseInt(dim_duration));
        setAlarm(c,cxt);
    }

    private static void setAlarm(Calendar calendar, Context cxt){
        AlarmManager am = (AlarmManager) cxt.getSystemService(ALARM_SERVICE);
        Intent it = new Intent(cxt, SleepAlarmReceiver.class);
        PendingIntent pit = PendingIntent.getBroadcast(cxt, 0, it, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pit);
    }
}
