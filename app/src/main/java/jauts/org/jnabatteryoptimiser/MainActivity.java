package jauts.org.jnabatteryoptimiser;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
