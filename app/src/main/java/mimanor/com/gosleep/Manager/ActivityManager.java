package mimanor.com.gosleep.Manager;

import android.app.Activity;

import java.util.HashMap;

import mimanor.com.gosleep.GoSleep;

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

    public static int getActivityCount(){
        return activities.size();
    }

    public static GoSleep getMain(){
        return (GoSleep)activities.get("Main");
    }
}
