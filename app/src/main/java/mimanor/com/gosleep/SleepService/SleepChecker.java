package mimanor.com.gosleep.SleepService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

import mimanor.com.gosleep.Controller.SleepController;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.AlarmManagerSetter;

public class SleepChecker extends Service {

    private Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        mContext = this;

        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        SleepController sp = new SleepController(mContext);
        if(sp.isRunning()) {
            System.out.println("started");
            Calendar stopTime = sp.getTime();
            if (stopTime != null) {
                Calendar today = Calendar.getInstance();
                if (today.before(stopTime)) {

                    if (ActivityManager.getActivityCount() == 0 || !ActivityManager.getMain().is_on_top()) {
                        AlarmManagerSetter.SetOffScreenAlarm(mContext);
                    }

                } else {
                    sp.setRunning(false);
                }
            } else {
                System.out.println();
                System.out.println("Screen off!");
            }
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

}

