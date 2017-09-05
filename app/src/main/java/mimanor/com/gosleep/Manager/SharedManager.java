package mimanor.com.gosleep.Manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedManager {

    private Context mContext;
    private final String name = "go_sleep";

    public SharedManager(Context mContext) {
        this.mContext = mContext;
    }

    public void save(String v_name, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(v_name, value);
        editor.apply();
    }

    public String read(String v_name){
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(v_name, "");
    }
}
