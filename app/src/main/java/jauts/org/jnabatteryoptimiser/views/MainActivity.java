package jauts.org.jnabatteryoptimiser.views;

import android.Manifest;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import jauts.org.jnabatteryoptimiser.R;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPullSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPushSensorsTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grantLocation();
        grantUsageStat();
        collectSensorData();
    }

    private void grantLocation() {
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    Log.d("LocationGranted", String.valueOf(granted));
                });

    }

    private void grantUsageStat() {
        Boolean isGranted = false;
        isGranted = new RxPermissions(this)
                .isGranted(Manifest.permission.PACKAGE_USAGE_STATS);

       // if (! isGranted) {
        //    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        //}
        Log.d("UsageStatGranted", String.valueOf(isGranted));

    }

    private void collectSensorData() {
        //new CollectSensorDataTask(this).execute();
        new SenseFromAllPushSensorsTask(this).execute();
        new SenseFromAllPullSensorsTask(this).execute();
    }

}
