package jauts.org.jnabatteryoptimiser;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import jauts.org.jnabatteryoptimiser.dummy.DummyContent;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllEnvSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPullSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPushSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.WekaMlTask;

public class MainActivity extends AppCompatActivity implements PullSensorsFragment.OnListFragmentInteractionListener, PushSensorsFragment.OnListFragmentInteractionListener, AppsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Pull Sensors"));
        tabLayout.addTab(tabLayout.newTab().setText("Push Sensors"));
        tabLayout.addTab(tabLayout.newTab().setText("Running Apps"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // test weka lib
        // new WekaMlTask(this).execute();

        // test sensor manager
        startPull();
    }



    public void startPull() {
        new SenseFromAllPullSensorsTask(this)
        {
            @Override
            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
                //startEnvironment();
                startPush();
            }
        }.execute();
    }

    private void startEnvironment()
    {
        new SenseFromAllEnvSensorsTask(this)
        {
            @Override
            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
                startPush();
            }
        }.execute();
    }

    private void startPush()
    {
        new SenseFromAllPushSensorsTask(this).execute();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
