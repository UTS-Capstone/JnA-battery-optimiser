package jauts.org.jnabatteryoptimiser;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllEnvSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPullSensorsTask;
import jauts.org.jnabatteryoptimiser.tasks.SenseFromAllPushSensorsTask;

public class LoggingService extends Service {
    public LoggingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Logging service started", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // non-blocking call
        collectSensorData();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void collectSensorData() {
        new SenseFromAllPushSensorsTask(this).execute();
        new SenseFromAllPullSensorsTask(this).execute();
    }

}
