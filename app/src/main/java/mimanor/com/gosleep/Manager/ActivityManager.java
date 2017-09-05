package mimanor.com.gosleep.Manager;

import android.app.Activity;

import java.util.HashMap;

/**
 * Created by GD on 3/9/2017.
 */

public class ActivityManager {

    private static HashMap<String,Activity> activities = new HashMap<>();

    public static void addActivity(String acName, Activity activity){
        activities.put(acName,activity);
    }

    public static void removeActivity(String name){
        activities.remove(name);
    }

    public static Activity getActivity(String name){
        return activities.get(name);
    }
}
