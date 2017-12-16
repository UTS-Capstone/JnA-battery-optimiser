package jauts.org.jnabatteryoptimiser.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import jauts.org.jnabatteryoptimiser.AppsFragment;
import jauts.org.jnabatteryoptimiser.PullSensorsFragment;
import jauts.org.jnabatteryoptimiser.PushSensorsFragment;

/**
 * Created by Jeremy on 2/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    private String tabTitles[] = new String[]{"Pull Sensors", "Push Sensors", "Running Apps"};

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                PullSensorsFragment tab1 = new PullSensorsFragment();
                return tab1;
            case 1:
                PushSensorsFragment tab2 = new PushSensorsFragment();
                return tab2;
            case 2:
                AppsFragment tab3 = new AppsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
