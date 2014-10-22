package group12.ucsc.agentmate.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by NRV on 10/21/2014.
 */
public class NetSync extends Service{
    private final WebSocketConnection mConnection = new WebSocketConnection();
    private static final String TAG = "de.tavendo.test1";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TAG = "de.tavendo.test1";
        int res = super.onStartCommand(intent, flags, startId);
        Toast.makeText(getApplicationContext(), "Service SYNC Started", Toast.LENGTH_LONG).show();
        final Thread t = new Thread() {

            @Override
            public void run() {
                mestart();
            }
        };
        t.start();

        Toast.makeText(getApplicationContext(),"Line",Toast.LENGTH_LONG).show();


        return START_STICKY;
    }

    private void mestart() {
//ws://echo.websocket.org:80
        final String wsuri = "ws://connect.mysensors.mobi:8080";

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    mConnection.sendTextMessage("LOGIN #name Randul #skey 123456 @mysensors");
                    Toast.makeText(getApplicationContext(),"Open",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                    Toast.makeText(getApplicationContext(),"payload"+payload,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                    mestart();
                    Toast.makeText(getApplicationContext(),"^^^^^^^",Toast.LENGTH_LONG).show();
                }

            });
        } catch (WebSocketException e) {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

            Log.d(TAG, e.toString());
        }
    }
}
