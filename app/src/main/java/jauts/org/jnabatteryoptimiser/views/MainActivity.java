package jauts.org.jnabatteryoptimiser.views;

import android.Manifest;
import android.annotation.TargetApi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import android.util.Log;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    public void sampleOnceClick(View view)
    {
        //TODO add sample functionality
        toastMsg("Sample taken");
    }

    public void exportCSVClick(View view)
    {
        //TODO add export to CSV file functionality
        toastMsg("Data exported");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loggingServiceSwitchClick(View view)
    {
        //TODO add start and stop functionality & (lower priority) add logging intervals
        TextView loggingServiceSwitchText = (TextView) findViewById(R.id.loggingServiceSwitch);
        if(loggingServiceSwitchText.getText() == "Start Logging Service") {
            loggingServiceSwitchText.setText("Stop Logging Service");
            loggingServiceSwitchText.setBackgroundTintList(ColorStateList.valueOf(0xffff0038));
        }
        else
        {
            loggingServiceSwitchText.setText("Start Logging Service");
            loggingServiceSwitchText.setBackgroundTintList(ColorStateList.valueOf(0xff17BDFF));
        }
    }


    public void toastMsg(String msg) {


    public void sampleOnceClick(View view)
    {
        toastMsg("Sample taken");
    }

    public void exportCSVClick(View view)
    {
        toastMsg("Data exported");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loggingServiceSwitchClick(View view)
    {
        TextView loggingServiceSwitchText = (TextView) findViewById(R.id.loggingServiceSwitch);
        if(loggingServiceSwitchText.getText() == "Start Logging Service") {
            loggingServiceSwitchText.setText("Stop Logging Service");
            loggingServiceSwitchText.setBackgroundTintList(ColorStateList.valueOf(0xffff0038));
        }
        else
        {
            loggingServiceSwitchText.setText("Start Logging Service");
            loggingServiceSwitchText.setBackgroundTintList(ColorStateList.valueOf(0xff17BDFF));
        }
    }


    public void toastMsg(String msg) {


        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setBackgroundColor(Color.TRANSPARENT);

        toastMessage.setShadowLayer(0, 0, 0, Color.TRANSPARENT);

        toast.show();
    }
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }
}
