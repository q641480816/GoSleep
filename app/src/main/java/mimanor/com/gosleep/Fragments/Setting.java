package mimanor.com.gosleep.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Slider;

import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.SharedManager;
import mimanor.com.gosleep.R;


public class Setting extends Fragment {

    private Context mContext;
    private SharedManager sharedManager;

    //views
    private View view;
    private TextView languages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gs_fg_setting, container, false);

        bindView();
        init();
        addListener();
        return view;
    }

    private void bindView(){
        languages = view.findViewById(R.id.chose_language);
    }

    private void init(){
        mContext = ((GoSleep) ActivityManager.getActivity("Main")).getContext();
        sharedManager = new SharedManager(mContext);
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
    }

}

