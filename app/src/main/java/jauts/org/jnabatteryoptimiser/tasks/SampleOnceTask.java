package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pull.AccelerometerData;
import com.ubhave.sensormanager.data.pull.BluetoothData;
import com.ubhave.sensormanager.data.pull.CallContentListData;
import com.ubhave.sensormanager.data.pull.GyroscopeData;
import com.ubhave.sensormanager.data.pull.LocationData;
import com.ubhave.sensormanager.data.pull.MagneticFieldData;
import com.ubhave.sensormanager.data.pull.MicrophoneData;
import com.ubhave.sensormanager.data.pull.PhoneRadioData;
import com.ubhave.sensormanager.data.pull.SMSContentListData;
import com.ubhave.sensormanager.data.pull.SMSContentReaderEntry;
import com.ubhave.sensormanager.data.pull.StepCounterData;
import com.ubhave.sensormanager.data.pull.WifiData;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import jauts.org.jnabatteryoptimiser.dummy.SensorContent;
import jauts.org.jnabatteryoptimiser.views.activity.MainActivity;
import jauts.org.jnabatteryoptimiser.views.fragment.PullSensorsFragment;

/**
 * Created by liangze on 12/1/18.
 */

public class SampleOnceTask extends AsyncTask<Void, Void, List<SensorContent.SensorItem>> {

    private MainActivity mContext;
    private ESSensorManager sensorManager;

    private final Logger logger = LoggerFactory.getLogger(SenseFromAllPullSensorsTask.class);

    public SampleOnceTask(MainActivity activity) {
        mContext = activity;
        try {
            sensorManager = ESSensorManager.getSensorManager(mContext);
            sensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
        } catch (ESException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<SensorContent.SensorItem> doInBackground(Void... voids) {

        List<SensorContent.SensorItem> pullSensors = new ArrayList<>();
        String data = null;

        for (SensorEnum se: SensorEnum.values()) {
            if (se.isPull()) {
                data = getDataFrom(se);
                pullSensors.add(new SensorContent.SensorItem(se.getName(), data, null));
            }
        }

        return pullSensors;
    }

    @Override
    protected void onPostExecute(List<SensorContent.SensorItem> sensorItems) {
        super.onPostExecute(sensorItems);
        PullSensorsFragment frag = (PullSensorsFragment) mContext.getAttachedFragment(0);
        frag.updatePullSensorsList(sensorItems);
    }

    private String getDataFrom(SensorEnum s) {
        String res = null;
        try {
            if (s.getName().equals("Accelerometer")) {
                AccelerometerData data = (AccelerometerData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("Bluetooth")) {
                BluetoothData data = (BluetoothData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("Location")) {
                LocationData data = (LocationData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("Microphone")) {
                MicrophoneData data = (MicrophoneData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("WiFi")) {
                WifiData data = (WifiData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("SMSContentReader")) {
                SMSContentListData data = (SMSContentListData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("CallContentReader")) {
                CallContentListData data = (CallContentListData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("Gyroscope")) {
                GyroscopeData data = (GyroscopeData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("PhoneRadio")) {
                // leave it for now
            }
            if (s.getName().equals("MagneticField")) {
                MagneticFieldData data = (MagneticFieldData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    res = formatter.toString(data);
                }
            }
            if (s.getName().equals("StepCounter")) {
                StepCounterData data = (StepCounterData) sensorManager.getDataFromSensor(s.getType());
                if (data != null) {
                    JSONFormatter formatter = DataFormatter.getJSONFormatter(mContext, data.getSensorType());
                    if (formatter == null) {
                        // not supported
                        res = "steps: " + String.valueOf(data.getNumSteps()) + " timestamp: " + String.valueOf(data.getTimestamp());
                    } else {
                        res = formatter.toString(data);
                    }
                }
            }

        } catch (ESException e) {
            e.printStackTrace();
        } catch (DataHandlerException e) {
            e.printStackTrace();
        }
        return res;
    }

}
