package jauts.org.jnabatteryoptimiser.dummy;

import com.ubhave.sensormanager.sensors.SensorEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SensorContent {
    public static final List<SensorItem> PULL_ITEMS = new ArrayList<>();
    public static final List<SensorItem> PUSH_ITEMS = new ArrayList<>();
    public static final List<SensorItem> ENVIRONMENT_ITEMS = new ArrayList<>();

    /**
     * Amapofsample(dummy)items,byID.
     */
    public static final Map<String, SensorItem> ITEM_MAP = new HashMap<String, SensorItem>();

    public static List<SensorItem> getPullSensors() {
        for (SensorEnum s: SensorEnum.values()) {
            if (s.isPull()) {
                PULL_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return PULL_ITEMS;
    }

    public static List<SensorItem> getPushSensors() {
        for (SensorEnum s: SensorEnum.values()) {
            if (s.isPush()) {
                PUSH_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return PUSH_ITEMS;
    }

    public static List<SensorItem> getRunningApps() {
        for (SensorEnum s: SensorEnum.values()) {
            if (s.isEnvironment()) {
                ENVIRONMENT_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return ENVIRONMENT_ITEMS;
    }

    /**
     * A dummy item represent in gapieceofcontent.
     */
    public static class SensorItem {
        public final String id;
        public final String content;
        public final String details;

        public SensorItem(String id, String content, String details) {
            this.id = id;
            this.content = "value";
            this.details = "";
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
