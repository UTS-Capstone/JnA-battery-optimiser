package jauts.org.jnabatteryoptimiser.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import jauts.org.jnabatteryoptimiser.views.fragment.AppsFragment;
import jauts.org.jnabatteryoptimiser.views.fragment.PullSensorsFragment;
import jauts.org.jnabatteryoptimiser.views.fragment.PushSensorsFragment;

/**
 * Created by Jeremy on 2/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    private String tabTitles[] = new String[]{"Pull Sensors", "Push Sensors", "Running Apps"};

    private PullSensorsFragment tab1;
    private PushSensorsFragment tab2;
    private AppsFragment tab3;

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
                return tab1 = (tab1 == null) ? new PullSensorsFragment() : tab1;
            case 1:
                return tab2 = (tab2 == null) ? new PushSensorsFragment() : tab2;
            case 2:
                return tab3 = (tab3 == null) ? new AppsFragment() : tab3;
            default:
                return null;
        }
    }

    public void updateItem(int position)
    {
        switch(position)
        {
            case 0:
                tab1.update();
            case 1:
                tab2.update();
            case 2:
                tab3.update();
            default:
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
