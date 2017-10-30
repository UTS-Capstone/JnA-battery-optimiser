package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.pull.BluetoothData;
import com.ubhave.sensormanager.data.pull.LocationData;
import com.ubhave.sensormanager.data.pull.WifiData;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPullSensorsTask extends AsyncTask<Void, Void, Void>
{
	private final static String LOG_TAG = "SensePull";
	private ESSensorManager sensorManager;
	private Context context;

	public SenseFromAllPullSensorsTask(final Context context)
	{
		this.context = context;

		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			sensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		for (SensorEnum s : SensorEnum.values())
		{
			if (s.isPull()) {

				if (s.getType() == SensorUtils.SENSOR_TYPE_LOCATION) {
					Log.d(LOG_TAG, "Sensing from: " + s.getName());
					try {
						LocationData locdata = (LocationData) sensorManager.getDataFromSensor(SensorUtils.SENSOR_TYPE_LOCATION);
						JSONFormatter f = DataFormatter.getJSONFormatter(context, locdata.getSensorType());
						Log.d("LOCJSON", f.toString(locdata));
						Log.d(LOG_TAG, "Sensed from: " + SensorUtils.getSensorName(locdata.getSensorType()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

}
