package mimanor.com.gosleep.SleepService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.util.Calendar;

import mimanor.com.gosleep.Controller.SleepController;
import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.R;

/**
 * Created by GD on 4/9/2017.
 */

public class Sleep extends Service {

    private Context mContext;
    private PowerManager pm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        mContext = this;
        pm = (PowerManager)getSystemService(Context.POWER_SERVICE);

        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        SleepController sp = new SleepController(mContext);
        Calendar stopTime = sp.getTime();
        if(stopTime != null) {
            Calendar today = Calendar.getInstance();
            if(today.before(stopTime)) {
                Intent it = new Intent();
                it.setClass(mContext, GoSleep.class);
                it.putExtra("from_service", true);
                startActivity(it);
            }else{
                sp.setRunning(false);
                notify_finish();
            }
        } else {
            System.out.println();
            System.out.println("Screen off!");
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void notify_finish(){
        Intent it = new Intent(mContext, GoSleep.class);
        PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);
        NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.notification);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle(mContext.getString(R.string.notification_title))
                .setContentText(mContext.getString(R.string.notification_content))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.sleep)
                .setLargeIcon(largeBitmap)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(false)
                .setContentIntent(pit);
        Notification notify1 = mBuilder.build();
        mNManager.notify(1, notify1);
    }
}
