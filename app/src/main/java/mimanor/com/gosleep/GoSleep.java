package mimanor.com.gosleep;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mimanor.com.gosleep.Adapter.MainFragmentAdapter;

public class GoSleep extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener {

    Context mContext;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_sleep);

        bindViews();
        init();
    }

    private void init(){
        mContext = GoSleep.this;
        mfAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        pagers.setAdapter(mfAdapter);
        pagers.setCurrentItem(0);
        pagers.addOnPageChangeListener(this);
        rb_sleep.setChecked(true);
    }

    private void bindViews(){
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_sleep = (RadioButton) findViewById(R.id.rb_sleep);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        pagers = (ViewPager) findViewById(R.id.pagers);
        rg_tab_bar.setOnCheckedChangeListener(this);
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
