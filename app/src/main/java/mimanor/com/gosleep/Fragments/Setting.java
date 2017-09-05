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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Slider;

import java.util.Calendar;

import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.SharedManager;
import mimanor.com.gosleep.R;


public class Setting extends Fragment {

    private Context mContext;
    private SharedManager sharedManager;

    //views
    private View view;
    private Slider dim_slider;
    private TextView dime_content, languages;

    //value
    private int dim_duration;

    //action
    private final String dim = "dim";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gs_fg_setting, container, false);

        bindView();
        init();
        addListener();
        return view;
    }

    private void bindView(){
        dime_content = view.findViewById(R.id.dim_content);
        dim_slider = view.findViewById(R.id.dim_slider);
        languages = view.findViewById(R.id.chose_language);
    }

    private void init(){
        mContext = ((GoSleep) ActivityManager.getActivity("Main")).getContext();
        sharedManager = new SharedManager(mContext);

        //set dim value
        String dim_temp = sharedManager.read(dim);
        if (!dim_temp.equals("")){
            dim_duration = Integer.parseInt(dim_temp);
        }else{
            dim_duration = 5;
            sharedManager.save(dim,"5");
        }
        dime_content.setText(mContext.getString(R.string.setting_config_dim_time) + "(" + dim_duration + " s)");
        dim_slider.setValue((float)dim_duration,false);
    }

    private void addListener(){
        languages.setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                AlertDialog alert = builder
                        .setTitle("Naive!")
                        .setMessage("Why would I build multi-languages function for this simple application _(:3 」∠)_ ")
                        .setPositiveButton("Interesting!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alert.show();
            }
        });

        dim_slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                dim_duration = newValue;
                dime_content.setText(mContext.getString(R.string.setting_config_dim_time) + "(" + dim_duration + " s)");
                sharedManager.save(dim,newValue + "");
            }
        });
    }

}

