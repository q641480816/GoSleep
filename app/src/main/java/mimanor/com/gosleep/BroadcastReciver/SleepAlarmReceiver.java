package mimanor.com.gosleep.BroadcastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mimanor.com.gosleep.SleepService.Sleep;

public class SleepAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,Sleep.class);
        context.startService(i);
    }
}
