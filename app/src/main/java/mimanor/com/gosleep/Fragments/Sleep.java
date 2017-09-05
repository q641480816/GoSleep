package mimanor.com.gosleep.Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mimanor.com.gosleep.Controller.SleepController;
import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.SharedManager;
import mimanor.com.gosleep.R;

public class Sleep extends Fragment {

    private Context mContext;
    private SleepController sController;
    private Calendar calendar, today;
    private AlertDialog confirm_start = null;
    private AlertDialog.Builder builder = null;
    private SharedManager sm;

    //Views
    private View view;
    private TextView time_content;
    private Button select_time, start;

    //values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gs_fg_sleep, container, false);

        bindView();
        init();
        setListener();
        return view;
    }

    private void bindView(){
        time_content = view.findViewById(R.id.time_content);
        select_time = view.findViewById(R.id.select_time);
        start = view.findViewById(R.id.start);
    }

    private void init(){
        mContext = ((GoSleep)ActivityManager.getActivity("Main")).getContext();
        sController = new SleepController(mContext);
        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
        sm = new SharedManager(mContext);

        checkStatus();
    }

    private void setListener(){
        select_time.setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View v) {
                today = Calendar.getInstance();
                new TimePickerDialog(mContext, new MyTimePickerDialogListener(), today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), true).show();
            }
        });

        start.setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View v) {
                String dim_duration = sm.read("dim");
                if(dim_duration.equals("")){
                    dim_duration = "5";
                }
                builder = new AlertDialog.Builder(mContext);
                confirm_start = builder
                        .setTitle(mContext.getString(R.string.sleep_start_confirm))
                        .setMessage(mContext.getString(R.string.sleep_start_content_top) + " " + dim_duration + " " + mContext.getString(R.string.sleep_start_content_end))
                        .setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(mContext.getString(R.string.sleep_confirm_start), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, mContext.getString(R.string.control_started), Toast.LENGTH_SHORT).show();
                                sleep_start();
                            }
                        }).create();
                confirm_start.show();
            }
        });
    }

    public void checkStatus(){
        if(sController.isRunning()){
            time_content.setText(mContext.getString(R.string.sleep_running));
            //activated_action();
        }else{
            time_content.setText(mContext.getString(R.string.sleep_no_time));
            select_time.setVisibility(View.VISIBLE);
        }
    }

    private void sleep_start(){
        start.setVisibility(View.INVISIBLE);
        select_time.setVisibility(View.INVISIBLE);
        time_content.setText(mContext.getString(R.string.sleep_running));
        sController.saveTime(calendar);
        sController.setRunning(true);

        //start service
        ((GoSleep)ActivityManager.getActivity("Main")).set_start_service();
    }

    private void activated_action(){
        WindowManager.LayoutParams params = ((GoSleep)ActivityManager.getActivity("Main")).getWindow().getAttributes();
        params.screenBrightness = ((float) 0.1);
        ((GoSleep)ActivityManager.getActivity("Main")).getWindow().setAttributes(params);
    }

    class MyTimePickerDialogListener implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.DAY_OF_MONTH,today.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND, 0);
            today.set(Calendar.SECOND,0);
            String time_c = "";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            if(calendar.compareTo(today) <= 0){
                calendar.add(Calendar.DAY_OF_MONTH,1);
                time_c = mContext.getString(R.string.sleep_with_time) + " " + mContext.getString(R.string.sleep_tmr) + " " + sdf.format(calendar.getTime());
            }else{
                time_c = mContext.getString(R.string.sleep_with_time) + " "  + mContext.getString(R.string.sleep_today) + " "  + sdf.format(calendar.getTime());
            }
            time_content.setText(time_c);
            start.setVisibility(View.VISIBLE);
        }
    }

}
