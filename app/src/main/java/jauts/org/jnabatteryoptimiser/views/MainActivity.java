package jauts.org.jnabatteryoptimiser.views;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import jauts.org.jnabatteryoptimiser.AppsFragment;
import jauts.org.jnabatteryoptimiser.adapters.PagerAdapter;
import jauts.org.jnabatteryoptimiser.PullSensorsFragment;
import jauts.org.jnabatteryoptimiser.PushSensorsFragment;
import jauts.org.jnabatteryoptimiser.R;
import jauts.org.jnabatteryoptimiser.dummy.DummyContent;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPullSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPushSensorsTask;

public class MainActivity extends AppCompatActivity implements PullSensorsFragment.OnListFragmentInteractionListener, PushSensorsFragment.OnListFragmentInteractionListener, AppsFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Pull Sensors"));
        tabLayout.addTab(tabLayout.newTab().setText("Push Sensors"));
        tabLayout.addTab(tabLayout.newTab().setText("Running Apps"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
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

        grantLocation();
        collectSensorData();
    }

    private void grantLocation() {
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    Log.d("LocationGranted", String.valueOf(granted));
                });

    }

    private void collectSensorData() {
        //new CollectSensorDataTask(this).execute();
        new SenseFromAllPushSensorsTask(this).execute();
        new SenseFromAllPullSensorsTask(this).execute();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }
}
