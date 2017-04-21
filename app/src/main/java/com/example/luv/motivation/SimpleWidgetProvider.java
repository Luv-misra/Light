package com.example.luv.motivation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by luv on 17/4/17.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {

    MyDBHandler handler;
    String value = "";
    RemoteViews RR;
    products P;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        handler = new MyDBHandler(context,null,null,1);

        P = handler.getProductById(numbers.now);
        numbers.now++;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            Log.i("ONONONON", "onUPDATE: ");
//            setText();

//            Intent intent = new Intent(context, SimpleWidgetProvider.class);
//
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
//            intent.putExtra("key", "1");
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
//
//
//            RR = remoteViews;
//            // Register an onClickListener for 2nd button............
//            Intent intent2 = new Intent(context, SimpleWidgetProvider.class);
//
//            intent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
//            intent2.putExtra("key", "2");
//
//            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,
//                    1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            remoteViews.setOnClickPendingIntent(R.id.copy, pendingIntent2);
//
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);



            remoteViews.setTextViewText(R.id.textView, P.quote + "\nBy : " +P.author );

            Intent intent = new Intent(context, SimpleWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","19");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);

//            Intent intent1 = new Intent(context, SimpleWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","21");
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,
                    1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.copy, pendingIntent1);


            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","33");
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,
                    2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.share, pendingIntent2);



            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle b=intent.getExtras();

        if( b!=null )
        {
            value=b.getString("key");
        }
//        int a = Integer.parseInt(value);

        Log.i("ONONONON", "onReceive: "+value);


//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                R.layout.simple_widget);

//        if( a==19 )
//        {
////            remoteViews.setTextViewText(R.id.textView, P.quote + "\nBy : " +P.author );
////            Log.i("ONONONON", "REFRESHING");
//        }
//        else if( a==21 )
//        {
////            Log.i("ONONONON", "COPYING");
//        }
//        else if( a==33 )
//        {
////            Log.i("ONONONON", "SHARING");
//        }

        super.onReceive(context, intent);

    }

}
