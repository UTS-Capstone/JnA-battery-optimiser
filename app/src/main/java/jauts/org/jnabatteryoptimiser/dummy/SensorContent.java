package jauts.org.jnabatteryoptimiser.dummy;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.jaredrummler.android.processes.models.Statm;
import com.ubhave.sensormanager.sensors.SensorEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.Context.ACTIVITY_SERVICE;


public class SensorContent {
    public static final List<SensorItem> PULL_ITEMS = new ArrayList<>();
    public static final List<SensorItem> PUSH_ITEMS = new ArrayList<>();
    public static final List<SensorItem> ENVIRONMENT_ITEMS = new ArrayList<>();
    public static final List<SensorItem> APP_ITEMS = new ArrayList<>();
    /**
     * Amapofsample(dummy)items,byID.
     */
    public static final Map<String, SensorItem> ITEM_MAP = new HashMap<String, SensorItem>();
    private static final Logger logger = LoggerFactory.getLogger(SensorContent.class);

    public static List<SensorItem> getPullSensors() {
        for (SensorEnum s : SensorEnum.values()) {
            if (s.isPull()) {
                PULL_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return PULL_ITEMS;
    }

    public static List<SensorItem> getPushSensors() {
        for (SensorEnum s : SensorEnum.values()) {
            if (s.isPush()) {
                PUSH_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return PUSH_ITEMS;
    }

    public static List<SensorItem> getEnviroSensors() {
        for (SensorEnum s : SensorEnum.values()) {
            if (s.isEnvironment()) {
                ENVIRONMENT_ITEMS.add(new SensorItem(s.getName(), null, null));
            }
        }
        return ENVIRONMENT_ITEMS;
    }

    public static List<SensorItem> getRunningApps(Context context) throws PackageManager.NameNotFoundException, IOException {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0);

        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> usageStatsList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, calendar.getTimeInMillis(), System.currentTimeMillis());
            for (UsageStats us : usageStatsList) {
                APP_ITEMS.add(new SensorItem(us.getPackageName(), null,null));
            }
        }

        */
        // Get a list of running apps
        PackageManager pm = context.getPackageManager();
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

        for (AndroidAppProcess process : processes) {
            // Get some information about the process
            String processName = process.name;

            Stat stat = process.stat();
            int pid = stat.getPid();
            int parentProcessId = stat.ppid();
            long startTime = stat.stime();
            int policy = stat.policy();
            char state = stat.state();

            Statm statm = process.statm();
            long totalSizeOfProcess = statm.getSize();
            long residentSetSize = statm.getResidentSetSize();

            PackageInfo packageInfo = process.getPackageInfo(context, 0);
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            APP_ITEMS.add(new SensorItem(appName,null, null));
            logger.debug("App name: " + appName);
        }

        /*
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

        for (int i = 0; i < runningAppProcessInfo.size(); i++) {
            APP_ITEMS.add(new SensorItem(getApplicationLabel(context, runningAppProcessInfo.get(i).processName), null, null));
        } */
        return APP_ITEMS;

    }

    public static String getApplicationLabel(Context context, String packageName) {

        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String label = null;

        for (int i = 0; i < packages.size(); i++) {

            ApplicationInfo temp = packages.get(i);

            if (temp.packageName.equals(packageName))
                label = packageManager.getApplicationLabel(temp).toString();
        }
        return label;
    }

    boolean isNamedProcessRunning(Context context, String processName){
        if (processName == null)
            return false;

        ActivityManager manager =
                (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processes)
        {
            if (processName.equals(process.processName))
            {
                return true;
            }
        }
        return false;
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
            if (content == null || content.length() == 0) {
                content = "none";
            }
            this.content = content;
            this.details = "";
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
