package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.pull.WifiData;
import com.ubhave.sensormanager.sensors.SensorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liangze on 30/10/17.
 *
 * Collects sensor data
 */

public class CollectSensorDataTask extends AsyncTask<Void,Void,Void> {

    private final Logger logger = LoggerFactory.getLogger(CollectSensorDataTask.class);

    private static final String TAG_WIFI_STATUS = "WifiStatus";

    private Context mContext;
    private ESSensorManager mSensorManager;

    public CollectSensorDataTask(Context context) {
        this.mContext = context;
        try {
            mSensorManager = ESSensorManager.getSensorManager(context);
            mSensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
        }
        catch (ESException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // get time of the day

        // get location
        // get WiFi status
        //String wifiStatusJson = getWifiStatus();

        // get mobile network status
        // get screen status

        // get battery capacity

        // combine all data into one Json

        // pass json to arff converter

        return null;
    }

    private String getWifiStatus() {
        WifiData data = null;
        try {
            data = (WifiData) mSensorManager.getDataFromSensor(SensorUtils.SENSOR_TYPE_WIFI);
        } catch (ESException e) {
            e.printStackTrace();
        }

        JSONFormatter f = DataFormatter.getJSONFormatter(mContext, data.getSensorType());

        String str = null;
        try {
            str = f.toString(data);
        } catch (DataHandlerException e) {
            e.printStackTrace();
        }
        //Log.d(TAG_WIFI_STATUS, str);
        logger.debug(str);
        return str;
    }
}
