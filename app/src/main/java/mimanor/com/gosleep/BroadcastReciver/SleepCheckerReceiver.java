package mimanor.com.gosleep.BroadcastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mimanor.com.gosleep.SleepService.SleepChecker;


/**
 * Created by GD on 5/9/2017.
 */

public class SleepCheckerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,SleepChecker.class);
        context.startService(i);
    }
}
