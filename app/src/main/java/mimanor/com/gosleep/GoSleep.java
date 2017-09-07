package mimanor.com.gosleep;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mimanor.com.gosleep.Adapter.MainFragmentAdapter;
import mimanor.com.gosleep.BroadcastReciver.SleepAlarmReceiver;
import mimanor.com.gosleep.Controller.SleepController;
import mimanor.com.gosleep.Manager.ActivityManager;
import mimanor.com.gosleep.Manager.AlarmManagerSetter;
import mimanor.com.gosleep.SleepService.Sleep;
import mimanor.com.gosleep.SleepService.SleepChecker;

public class GoSleep extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener {

    private final String acName = "Main";
    private Activity ac;
    private Context mContext;
    private boolean self_activated;
    private boolean is_to_restart;
    private boolean is_self_on_top;

    //constant
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    //Views
    private RadioGroup rg_tab_bar;
    private RadioButton rb_sleep;
    private RadioButton rb_setting;
    private ViewPager pagers;

    //Adapter
    private MainFragmentAdapter mfAdapter;

    //value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_sleep);

        bindViews();
        init();
        requestPermission();
    }

    private void init(){
        is_self_on_top = true;
        ac = GoSleep.this;
        mContext = GoSleep.this;
        ActivityManager.addActivity(acName,ac);
        mfAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        pagers.setAdapter(mfAdapter);
        pagers.setCurrentItem(0);
        pagers.addOnPageChangeListener(this);
        rg_tab_bar.setOnCheckedChangeListener(this);
        rb_sleep.setChecked(true);

        Intent intent = getIntent();
        self_activated = !intent.getBooleanExtra("from_service",false);
        is_to_restart = !self_activated;
        if(!is_to_restart){
            SleepController sc = new SleepController(mContext);
            is_to_restart = sc.isRunning();
        }
    }

    private void bindViews(){
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_sleep = (RadioButton) findViewById(R.id.rb_sleep);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        pagers = (ViewPager) findViewById(R.id.pagers);
    }

    private void requestPermission(){

    }

    public Context getContext(){
        return mContext;
    }

    public boolean isSelf_activated(){
        return self_activated;
    }

    public void set_start_service(){
        is_to_restart = true;
    }

    public void off_start_service(){
        is_to_restart = false;
    }

    public boolean is_on_top(){
        return is_self_on_top;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(mContext, mContext.getString(R.string.warn), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        is_self_on_top = false;
        if (is_to_restart) {
            PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            if (powerManager.isScreenOn()) {
                AlarmManagerSetter.SetOffScreenAlarm(mContext);
                //Intent it = new Intent(mContext, Sleep.class);
                //mContext.startService(it);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        is_self_on_top = true;
        try {
            SleepController sp = new SleepController(mContext);
            sp.check_running();
            mfAdapter.updateView();
        }catch (NullPointerException e){
            System.out.println("Damn");
        }
    }

    //Radio changes
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_sleep:
                pagers.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_setting:
                pagers.setCurrentItem(PAGE_TWO);
                break;
        }
    }

    //Change page view
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (pagers.getCurrentItem()) {
                case PAGE_ONE:
                    rb_sleep.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_setting.setChecked(true);
                    break;
            }
        }
    }
}
