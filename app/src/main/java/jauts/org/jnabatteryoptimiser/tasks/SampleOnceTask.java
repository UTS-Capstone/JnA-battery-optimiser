package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

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

    public SampleOnceTask(MainActivity activity) {
        mContext = activity;
    }

    @Override
    protected List<SensorContent.SensorItem> doInBackground(Void... voids) {

        List<SensorContent.SensorItem> pullSensors = new ArrayList<>();

        for (SensorEnum se: SensorEnum.values()) {
            if (se.isPull()) {
                pullSensors.add(new SensorContent.SensorItem(se.getName(), "666", null));
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
}
