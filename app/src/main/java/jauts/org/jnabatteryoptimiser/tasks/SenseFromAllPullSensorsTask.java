package jauts.org.jnabatteryoptimiser.tasks;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.pull.ApplicationData;
import com.ubhave.sensormanager.data.pull.BluetoothData;
import com.ubhave.sensormanager.data.pull.CallContentListData;
import com.ubhave.sensormanager.data.pull.LocationData;
import com.ubhave.sensormanager.data.pull.WifiData;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorInterface;
import com.ubhave.sensormanager.sensors.SensorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ubhave.sensormanager.tasks.Subscription;
import com.ubhave.sensormanager.tasks.SubscriptionList;

import jauts.org.jnabatteryoptimiser.dummy.SensorContent;

public class SenseFromAllPullSensorsTask extends AsyncTask<Void, Void, Void> {
    private final static String LOG_TAG = "SensePull";
    private final Logger logger = LoggerFactory.getLogger(SenseFromAllPullSensorsTask.class);
    private final SubscriptionList subscriptionList;
    private ESSensorManager sensorManager;
    private Context context;

    public SenseFromAllPullSensorsTask(final Context context) {
        this.context = context;
        subscriptionList = new SubscriptionList();
        ArrayList<SensorInterface> sensors = SensorUtils.getAllSensors(context);

        try {
            sensorManager = ESSensorManager.getSensorManager(context);
            sensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
        } catch (ESException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        //pull location
        LocationData locdata = null;
        try {
            locdata = (LocationData) sensorManager.getDataFromSensor(SensorUtils.SENSOR_TYPE_LOCATION);
            JSONFormatter f = DataFormatter.getJSONFormatter(context, locdata.getSensorType());
            logger.debug(f.toString(locdata));
        } catch (ESException e) {
            e.printStackTrace();
        } catch (DataHandlerException e) {
            e.printStackTrace();
        }

        // application
        //ApplicationData appData = sensorManager.getDataFromSensor(SensorUtils.SENSOR_TYPE_)
        //getLatestRunApp();

        // call log
        CallContentListData callData = null;
        try {
            callData = (CallContentListData) sensorManager.getDataFromSensor(SensorUtils.SENSOR_TYPE_CALL_CONTENT_READER);
            JSONFormatter f = DataFormatter.getJSONFormatter(context, callData.getSensorType());
            logger.debug(f.toString(callData));
        } catch (ESException e) {
            e.printStackTrace();
        } catch (DataHandlerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getLatestRunApp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0);

        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> usageStatsList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, calendar.getTimeInMillis(), System.currentTimeMillis());
            for (UsageStats us : usageStatsList) {
                SensorContent.APP_ITEMS.add(new SensorContent.SensorItem(us.getPackageName(), null,null));
            }
        }
        return "";
    }

}
