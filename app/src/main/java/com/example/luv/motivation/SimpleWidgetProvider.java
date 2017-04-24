package com.example.luv.motivation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import me.grantland.widget.AutofitTextView;

/**
 * Created by luv on 17/4/17.
 */


public class SimpleWidgetProvider extends AppWidgetProvider {

    MyDBHandler handler;
    String value;
    String ans = "";
    SharedPreferences sharedPreferences;
    public products P;


    public void nextQuote( Context context )
    {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if( !sharedPreferences.contains("Qnow") )
        {
            Log.i("SP", " 1 ");
            sharedPreferences.edit().putString("Qnow",Integer.toString(numbers.now)).apply();
        }
        else
        {
            Log.i("SP", " 2 ");
            numbers.now = Integer.parseInt(sharedPreferences.getString("Qnow",""));
        }

        numbers.now++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.i("SP", " 3 ");
        editor.putString("Qnow",Integer.toString(numbers.now));
        Log.i("SP", " 4 ");
        editor.commit();
        Log.i("SP", " 5 ");
    }

    public void fav( Context context )
    {
        handler = new MyDBHandler(context,null,null,1);
        handler.toggleLike(Integer.toString(numbers.now));
        Log.i("ONONONON", "fav: ");
    }

    public void openApp( Context context )
    {
        openAppNow OAN = new openAppNow(context);
    }
    public  void copy(Context context)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String ans = "";
        handler = new MyDBHandler(context,null,null,1);
        P = handler.getProductById(numbers.now);
        String res = P.quote ;

        if( P.author.equals("") )
        { }
        else
        {
            res += "\n"+"by : "+P.author;
        }
        ClipData clip = ClipData.newPlainText("",res);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public void share( Context context )
    {
        handler = new MyDBHandler(context,null,null,1);
        P = handler.getProductById(numbers.now);
        String res = P.quote ;
        if( P.author.equals("") )
        {

        }
        else
        {
            res += "\n\n"+"by : "+P.author;
        }
        ShareIt SI = new ShareIt(context,res);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        Log.i("ONONONON", " 1 ");
        handler = new MyDBHandler(context,null,null,1);

        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if( !sharedPreferences.contains("Qnow") )
        {
            Log.i("SP", " 1 ");
            sharedPreferences.edit().putString("Qnow",Integer.toString(numbers.now)).apply();
        }
        else
        {
            Log.i("SP", " 2 ");
            numbers.now = Integer.parseInt(sharedPreferences.getString("Qnow",""));
        }

        P = new products("no","no");
        Log.i("ONONONON", " 2 ");
        P = handler.getProductById(numbers.now);
        if( P.quote == null )
        {
            numbers.now=0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Qnow",Integer.toString(numbers.now));
            editor.commit();
            P = handler.getProductById(numbers.now);
        }
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            Log.i("ONONONON", "onUPDATE: ");

            remoteViews.setTextViewText(R.id.textView, P.quote);
            remoteViews.setTextViewText(R.id.author, P.author);


            if( P.favourite == 1 )
            {
                remoteViews.setImageViewResource(R.id.actionButton,R.drawable.like);
            }
            else
            {
                remoteViews.setImageViewResource(R.id.actionButton,R.drawable.like_no);
            }


            if( numbers.setBack )
            {
                if (P.quote != null)
                {
                    remoteViews.setViewVisibility(R.id.back, View.VISIBLE);
                    String back_name = "b" + Integer.toString(P.Bimg);
                    int id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + back_name, null, null);
                    remoteViews.setInt(R.id.back, "setBackgroundResource", id);

                }
            }
            else
            {
                remoteViews.setViewVisibility(R.id.back, View.INVISIBLE);
            }

            Intent intent = new Intent(context, SimpleWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","19");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.textView, pendingIntent);

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

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","89");
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context,
                    3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.showBack, pendingIntent3);


            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","74");
            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context,
                    4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.openApp, pendingIntent4);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","52");
            PendingIntent pendingIntent5 = PendingIntent.getBroadcast(context,
                    5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent5);


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


        if(value!=null )
        {
            Log.i("ONONONON", "here in it");
            if(value.equals("19"))
            {
                Log.i("ONONONON", " 19191919 ");
                nextQuote(context);
            }
            else if(value.equals("33"))
            {
                Log.i("ONONONON", " 33333333 ");
                share(context);
            }
            else if(value.equals("21"))
            {
                Log.i("ONONONON", " 21212121 ");
                copy(context);
            }
            else if(value.equals("89"))
            {
                Log.i("ONONONON", " 89898989 ");
                numbers.setBack = !numbers.setBack;
            }
            else if(value.equals("74"))
            {
                Log.i("ONONONON", " 74747474 ");
                openApp(context);
            }
            else if(value.equals("52"))
            {
                Log.i("ONONONON", " 52525252 ");
                fav(context);
            }
        }

        super.onReceive(context, intent);

    }

}
