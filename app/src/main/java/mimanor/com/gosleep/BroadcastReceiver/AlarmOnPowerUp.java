package mimanor.com.gosleep.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Calendar;

import mimanor.com.gosleep.Controller.SleepController;
import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.AlarmManagerSetter;
import mimanor.com.gosleep.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmOnPowerUp extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        SleepController sp = new SleepController(mContext);
        if(sp.isRunning()) {
            Calendar stopTime = sp.getTime();
            if (stopTime != null) {
                Calendar today = Calendar.getInstance();
                if (today.before(stopTime)) {

                    if (ActivityManager.getActivityCount() == 0 || !ActivityManager.getMain().is_on_top()) {
                        AlarmManagerSetter.SetOffScreenAlarm(mContext, 3);
                        Intent it = new Intent();
                        it.setClass(mContext, GoSleep.class);
                        it.putExtra("from_service", true);
                        mContext.startActivity(it);
                    }

                } else {
                    sp.setRunning(false);
                    notify_finish();
                }
            } else {
                System.out.println();
                System.out.println("Screen off!");
            }
        }
    }

    private void notify_finish(){
        Intent it = new Intent(mContext, GoSleep.class);
        PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);
        NotificationManager mNManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Bitmap largeBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.notification);
        Notification.Builder mBuilder = new Notification.Builder(mContext);
        mBuilder.setContentTitle(mContext.getString(R.string.notification_title))
                .setContentText(mContext.getString(R.string.notification_content))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.sleep)
                .setLargeIcon(largeBitmap)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setContentIntent(pit);
        Notification notify1 = mBuilder.build();
        mNManager.notify(1, notify1);
    }
}
