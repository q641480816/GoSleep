package mimanor.com.gosleep.Manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import mimanor.com.gosleep.BroadcastReceiver.SleepAlarmReceiver;
import mimanor.com.gosleep.BroadcastReceiver.SleepCheckerReceiver;

import static android.content.Context.ALARM_SERVICE;


public class AlarmManagerSetter {

    public static void SetOffScreenAlarm(Context cxt){
        AlarmManager am = (AlarmManager) cxt.getSystemService(ALARM_SERVICE);
        Intent it = new Intent(cxt, SleepAlarmReceiver.class);
        PendingIntent pit = PendingIntent.getBroadcast(cxt, 0, it, 0);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND,500);
        am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pit);
    }

    public static void SetOffScreenAlarm(Context cxt, int interval){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,interval);
        AlarmManager am = (AlarmManager) cxt.getSystemService(ALARM_SERVICE);
        Intent it = new Intent(cxt, SleepCheckerReceiver.class);
        PendingIntent pit = PendingIntent.getBroadcast(cxt, 0, it, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pit);
    }
}
