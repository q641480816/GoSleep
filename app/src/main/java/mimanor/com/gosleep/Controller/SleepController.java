package mimanor.com.gosleep.Controller;

import android.content.Context;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mimanor.com.gosleep.Manager.SharedManager;

public class SleepController {

    private Context mContext;
    private SharedManager sm;

    //action
    private final String status = "status";
    private final String stop_time = "stop_time";
    private final String running = "running";

    //
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SleepController(Context mContext) {
        this.mContext = mContext;
        sm = new SharedManager(mContext);
    }

    public void saveTime(Calendar time){
        sm.save(stop_time,sdf.format(time.getTime()));
    }

    public Calendar getTime(){
        String time = sm.read(stop_time);
        Calendar c = null;
        try {
            Date date = sdf.parse(time);
            c = Calendar.getInstance();
            c.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return c;
        }
    }

    public boolean isRunning(){
        return (!sm.read(running).equals("") && sm.read(running).equals("1"));
    }

    public void setRunning(boolean run){
        if(run){
            sm.save(running,"1");
        }else{
            sm.save(running,"0");
        }
    }
}
