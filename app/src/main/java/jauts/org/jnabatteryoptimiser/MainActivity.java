package jauts.org.jnabatteryoptimiser;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllEnvSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPullSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPushSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.WekaMlTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void toggleWifi(boolean request) {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            boolean wifiEnabled = wifiManager.isWifiEnabled();
            if (request != wifiEnabled) {
                wifiManager.setWifiEnabled(request);
            }
        } catch (NullPointerException ex) {
            Log.e(TAG, "wifiManager has not been initialised!");
            System.exit(1);
        }
    }
}
