package com.example.luv.motivation;

/**
 * Created by luv on 2/5/17.
 */

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
        import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {

        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

            Toast.makeText(arg0, "on", Toast.LENGTH_SHORT).show();
            numbers.now++;

            MyDBHandler handler = new MyDBHandler(arg0,null,null,1);
            if( numbers.now > handler.size() )
            {
                numbers.now = 1 ;
            }

            Context context = arg0;
            SimpleWidgetProvider S = new SimpleWidgetProvider();
            intent.putExtra("key","19");
            S.onReceive(context,intent);
        }
        if( intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ){

            Toast.makeText(arg0, "off", Toast.LENGTH_SHORT).show();

        }
        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

        }
    }

}


