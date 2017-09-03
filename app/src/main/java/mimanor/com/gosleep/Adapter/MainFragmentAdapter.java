package mimanor.com.gosleep.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import mimanor.com.gosleep.GoSleep;
import mimanor.com.gosleep.fragment.Setting;
import mimanor.com.gosleep.fragment.Sleep;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private Setting setting = null;
    private Sleep sleep = null;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);

        setting = new Setting();
        sleep = new Sleep();
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case GoSleep.PAGE_ONE:
                fragment = sleep;
                break;
            case GoSleep.PAGE_TWO:
                fragment = setting;
                break;
        }
        return fragment;
    }
}
