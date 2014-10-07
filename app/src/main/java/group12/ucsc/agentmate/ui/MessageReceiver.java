package group12.ucsc.agentmate.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by NRV on 10/7/2014.
 */

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;



/**
 * Created by NRV on 10/7/2014.
 */


public class MessageReceiver extends BroadcastReceiver {
    static String  msg="Gps Position Not set";
    static long time=0;
    static double speed=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        msg=""+intent.getExtras().getString("new_location").toString();
        time=intent.getExtras().getLong("updated_time");
        speed=intent.getExtras().getDouble("speed");
       // Toast.makeText(context, "Intent Detected." + intent.getExtras().getString("new_location").toString() + msg, Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Intent Detected." + time+"  "+speed+" " + msg, Toast.LENGTH_LONG).show();

    }

    public static String getData(){
        return msg+"#"+time+"#"+speed;
    }

}
