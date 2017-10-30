package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.push.BatteryData;
import com.ubhave.sensormanager.data.push.ConnectionStateData;
import com.ubhave.sensormanager.data.push.ScreenData;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPushSensorsTask extends SubscribeTask implements SensorDataListener
{
    private final static String LOG_TAG = "SensePush";
    private Context context;

    public SenseFromAllPushSensorsTask(Context context)
    {
        super(context);
        this.context = context;
    }

    @Override
    protected void subscribe() throws ESException
    {
        SensorDataListener listener = new SensorDataListener() {
            @Override
            public void onDataSensed(SensorData sensorData) {
                ScreenData pdata = (ScreenData) sensorData;
                JSONFormatter f = DataFormatter.getJSONFormatter(context, pdata.getSensorType());
                try {
                    Log.d("PhoneScreenJSON", f.toString(pdata));
                } catch (DataHandlerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCrossingLowBatteryThreshold(boolean b) {

            }
        };

        sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_SCREEN, listener);

        sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_CONNECTION_STATE,
                new SensorDataListener() {
                    @Override
                    public void onDataSensed(SensorData sensorData) {
                        ConnectionStateData conndata = (ConnectionStateData) sensorData;
                        JSONFormatter f = DataFormatter.getJSONFormatter(context, conndata.getSensorType());
                        try {
                            Log.d("ConnStateJSON", f.toString(conndata));
                        } catch (DataHandlerException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCrossingLowBatteryThreshold(boolean b) {

                    }
                }
        );
        sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_BATTERY,
                new SensorDataListener() {
                    @Override
                    public void onDataSensed(SensorData sensorData) {
                        BatteryData batterydata = (BatteryData) sensorData;
                        JSONFormatter f = DataFormatter.getJSONFormatter(context, batterydata.getSensorType());
                        try {
                            Log.d("BatteryJSON", f.toString(batterydata));
                        } catch (DataHandlerException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCrossingLowBatteryThreshold(boolean b) {

                    }
                }
        );

        /*
        for (SensorEnum s : SensorEnum.values())
        {
            if (s.isPush())
            {
                Log.d(LOG_TAG, "Subscribe to: " + SensorUtils.getSensorName(s.getType()));
                int subscriptionId = sensorManager.subscribeToSensorData(s.getType(), SenseFromAllPushSensorsTask.this);
                subscriptions.put(s.getType(), subscriptionId);

                if (s.getType() == SensorUtils.SENSOR_TYPE_SCREEN) {
                    SensorDataListener listener = new SensorDataListener() {
                        @Override
                        public void onDataSensed(SensorData sensorData) {
                            ScreenData pdata = (ScreenData) sensorData;
                            JSONFormatter f = DataFormatter.getJSONFormatter(context, pdata.getSensorType());
                            try {
                                Log.d("PhoneScreenJSON", f.toString(pdata));
                            } catch (DataHandlerException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCrossingLowBatteryThreshold(boolean b) {

                        }
                    };

                    sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_SCREEN, listener);
                }

                if (s.getType() == SensorUtils.SENSOR_TYPE_CONNECTION_STATE) {
                    sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_CONNECTION_STATE,
                            new SensorDataListener() {
                                @Override
                                public void onDataSensed(SensorData sensorData) {
                                    ConnectionStateData conndata = (ConnectionStateData) sensorData;
                                    JSONFormatter f = DataFormatter.getJSONFormatter(context, conndata.getSensorType());
                                    try {
                                        Log.d("ConnStateJSON", f.toString(conndata));
                                    } catch (DataHandlerException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCrossingLowBatteryThreshold(boolean b) {

                                }
                            }
                    );
                }

                if (s.getType() == SensorUtils.SENSOR_TYPE_BATTERY) {
                    sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_BATTERY,
                            new SensorDataListener() {
                                @Override
                                public void onDataSensed(SensorData sensorData) {
                                    BatteryData batterydata = (BatteryData) sensorData;
                                    JSONFormatter f = DataFormatter.getJSONFormatter(context, batterydata.getSensorType());
                                    try {
                                        Log.d("BatteryJSON", f.toString(batterydata));
                                    } catch (DataHandlerException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCrossingLowBatteryThreshold(boolean b) {

                                }
                            }
                    );
                }

            }
        }
        */
    }

}