package com.leo.conanme.motivation;

/**
 * Created by luv on 2/5/17.
 */

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
        import android.content.Intent;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {

        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

           if( numbers.unlock )
           {
               numbers.phone_on = true;

               AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(arg0.getApplicationContext());
               int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(arg0, SimpleWidgetProvider.class));
               if (appWidgetIds.length > 0) {
                   new SimpleWidgetProvider().onUpdate(arg0, appWidgetManager, appWidgetIds);
               }
           }


        }
        if( intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ){


        }
        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

        }
    }

}


