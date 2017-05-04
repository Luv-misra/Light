package com.example.luv.motivation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;


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
    public void prevQuote( Context context )
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

        numbers.now--;
        if( numbers.now <= 0 )
        {
            handler = new MyDBHandler(context,null,null,1);
            numbers.now = (int)handler.size();
        }

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

//    public void share( Context context )
//    {
//
////        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
////        //retrieve a ref to the manager so we can pass a view update
////
////        Intent i = new Intent();
////        i.setClassName(context.getPackageName(), context.getPackageName().getClass().toString());
////        PendingIntent myPI = PendingIntent.getService(context, 0, i, 0);
//        //intent to start service
//
//
//        handler = new MyDBHandler(context,null,null,1);
//        P = handler.getProductById(numbers.now);
//        String res = P.quote ;
//        if( P.author.equals("") )
//        {
//
//        }
//        else
//        {
//            res += "\n\n"+"by : "+P.author;
//        }
//        Log.i("ONONONON", "share: going to share ");
//        ShareIt SI = new ShareIt(context,res);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        handler = new MyDBHandler(context,null,null,1);

        Log.i("ONONONON", "SIZE" );


        if( numbers.phone_on )
        {
            numbers.phone_on = false;
            nextQuote(context);
        }

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
        if( numbers.now > handler.size() )
        {
            numbers.now=1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Qnow",Integer.toString(numbers.now));
            editor.commit();
        }
        P = handler.getProductById(numbers.now);
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            Log.i("ONONONON", "onUPDATE: ");

            remoteViews.setTextViewText(R.id.textView, P.quote);
            remoteViews.setTextViewText(R.id.author, P.author);

            if( numbers.no_func )
            {
                remoteViews.setViewVisibility(R.id.func, View.GONE);
                remoteViews.setViewVisibility(R.id.left,View.GONE);
                remoteViews.setViewVisibility(R.id.right,View.GONE);
                remoteViews.setViewVisibility(R.id.tick_func,View.INVISIBLE);

            }
            else
            {
                remoteViews.setViewVisibility(R.id.func, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.left,View.VISIBLE);
                remoteViews.setViewVisibility(R.id.right,View.VISIBLE);
                remoteViews.setViewVisibility(R.id.tick_func,View.VISIBLE);
            }


            if( P.quote != null )
            {
                if( P.quote.length() < 35 )
                {
                    remoteViews.setFloat(R.id.textView, "setTextSize", 37);
                }
                else if( P.quote.length() < 40 )
                {
                    remoteViews.setFloat(R.id.textView, "setTextSize", 25);
                }
                else if( P.quote.length() < 45 )
                {
                    remoteViews.setFloat(R.id.textView, "setTextSize", 22);
                }
                else
                {
                    remoteViews.setFloat(R.id.textView, "setTextSize", 20);
                }

            }

            if( P.favourite == 1 )
            {
                remoteViews.setImageViewResource(R.id.actionButton,R.drawable.like);
            }
            else
            {
                remoteViews.setImageViewResource(R.id.actionButton,R.drawable.like_no);
            }

            if( numbers.blackBack )
            {
                remoteViews.setViewVisibility(R.id.back2, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.tick_black, View.VISIBLE);
            }
            else
            {
                remoteViews.setViewVisibility(R.id.back2, View.INVISIBLE);
                remoteViews.setViewVisibility(R.id.tick_black, View.INVISIBLE);
            }

            if( numbers.AWW )
            {
                remoteViews.setViewVisibility(R.id.SETTINGS, View.VISIBLE);
            }
            else
            {
                remoteViews.setViewVisibility(R.id.SETTINGS, View.INVISIBLE);
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
                remoteViews.setViewVisibility(R.id.tick_back, View.VISIBLE);
            }
            else
            {
                remoteViews.setViewVisibility(R.id.back, View.INVISIBLE);
                remoteViews.setViewVisibility(R.id.tick_back, View.INVISIBLE);
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
            remoteViews.setOnClickPendingIntent(R.id.no_back, pendingIntent3);


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

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","19");
            PendingIntent pendingIntent6 = PendingIntent.getBroadcast(context,
                    6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.right, pendingIntent6);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","11");
            PendingIntent pendingIntent7 = PendingIntent.getBroadcast(context,
                    7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.left, pendingIntent7);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","11");
            PendingIntent pendingIntent8 = PendingIntent.getBroadcast(context,
                    8, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.left_sc, pendingIntent8);


            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","19");
            PendingIntent pendingIntent9 = PendingIntent.getBroadcast(context,
                    9, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.right_sc, pendingIntent9);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","10");
            PendingIntent pendingIntent10 = PendingIntent.getBroadcast(context,
                    10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.black_back, pendingIntent10);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","100");
            PendingIntent pendingIntent11 = PendingIntent.getBroadcast(context,
                    11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.showBack, pendingIntent11);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","402");
            PendingIntent pendingIntent12 = PendingIntent.getBroadcast(context,
                    12, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.no_func, pendingIntent12);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","001");
            PendingIntent pendingIntent13 = PendingIntent.getBroadcast(context,
                    13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.AAA, pendingIntent13);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","82");
            PendingIntent pendingIntent14 = PendingIntent.getBroadcast(context,
                    14, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bbs, pendingIntent14);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra("key","210");
            PendingIntent pendingIntent15 = PendingIntent.getBroadcast(context,
                    15, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.more, pendingIntent15);


            //new activity
            Intent configIntent = new Intent(context, MainActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.openApp, configPendingIntent);
            //new activity

            //new activity (SETTINGS)
            Intent configIntent4 = new Intent(context, settings.class);
            PendingIntent configPendingIntent4 = PendingIntent.getActivity(context, 0, configIntent4, 0);
            remoteViews.setOnClickPendingIntent(R.id.more, configPendingIntent4);
            //new activity (SETTINGS)

            //share
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, ans);
            sendIntent.setType("text/plain");
            PendingIntent configPendingIntent1 = PendingIntent.getActivity(context, 0,sendIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.share, configPendingIntent1);
            //share

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
            if( value.equals("001") )
            {

            }
            else if(value.equals("19"))
            {
                Log.i("ONONONON", " 19191919 ");
                try
                {
                    nextQuote(context);
                }
                catch (Exception e)
                {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }

            }
            else if(value.equals("11"))
            {
                Log.i("ONONONON", " 11111111 ");
                try
                {
                    prevQuote(context);
                }
                catch (Exception e)
                {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }

            }
            else if(value.equals("33"))
            {
                Log.i("ONONONON", " 33333333 ");
            }
            else if(value.equals("21"))
            {
                Log.i("ONONONON", " 21212121 ");
                try
                {
                    copy(context);
                }
                catch (Exception e)
                {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }

            }
            else if(value.equals("100"))
            {
                numbers.AWW = !numbers.AWW;
            }
            else if( value.equals("82") )
            {
                numbers.AWW = !numbers.AWW;
            }
            else if(value.equals("89"))
            {
                Log.i("ONONONON", " 89898989 ");
                numbers.setBack = !numbers.setBack;
            }
            else if(value.equals("10"))
            {
                Log.i("ONONONON", " 10101010 ");
                numbers.blackBack = !numbers.blackBack;
            }
            else if(value.equals("74"))
            {

            }
            else if(value.equals("52"))
            {
                Log.i("ONONONON", " 52525252 ");
                try
                {
                    fav(context);
                }
                catch (Exception e)
                {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }
            }
            else if( value.equals("402") )
            {
                numbers.no_func = !numbers.no_func ;
            }
        }

        super.onReceive(context, intent);

    }

}
