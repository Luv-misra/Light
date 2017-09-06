package com.leo.conanme.motivation;

/**
 * Created by luv on 16/4/17.
 */


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
        import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class SendDataService extends Service {
        private final LocalBinder mBinder = new LocalBinder();
        protected Handler handler;




    String quote="$$$";
    String prev_quote="$$$";
    products P;
    Boolean S_status;
    MyDBHandler handlerDB;
    String author="";
    SharedPreferences sharedPreferences;
    Boolean isError = false;
    ArrayList<Integer> arrayList = new ArrayList<>();

    public class GetQuote extends AsyncTask<Void, Void, Void> {

        @Override
        public Void doInBackground(Void... params) {

            //http://api.forismatic.com/api/1.0/
            Map<String,String> m = new HashMap<>();

            JSONObject jsonObject = new JSONObject(m);

            Log.i("flow", "doInBackground: for GetQuote ");
            String url = "http://api.forismatic.com/api/1.0/";
            Response res = NetworkUtilities.postRequest(getApplicationContext(),jsonObject.toString(),url);

            if(res == null){
                Log.i("flow", "doInBackground: recieved null ");
            }else{
                Log.i("flow", "doInBackground: recieved data" );
                ResponseBody jsonData = res.body();
                try {
                    prev_quote = quote;

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("prevQuote",prev_quote);
                    editor.commit();

                    JSONObject Jobject = new JSONObject(jsonData.string());
                    quote  = Jobject.getString("quoteText");
                    author = Jobject.getString("quoteAuthor");

                    if( quote.equals(prev_quote) )
                    {
                        isError = true ;
                    }
                    if( quote == null )
                    {
                        isError = true;
                    }
                    else
                    {
                        isError = false;
                    }
                } catch (JSONException e) {
                    isError = true;
                    e.printStackTrace();
                } catch (IOException e) {
                    isError = true;
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if( isError==false && !prev_quote.equals(quote))
            {
                SendDataService.putInDB temp = new SendDataService.putInDB();
                temp.quote1 = quote;
                temp.author1 = author;
                Log.i("flow", "temp.quote1 = "+temp.quote1);
                Log.i("flow", "temp.author1 = "+temp.author1);
                temp.execute();
            }
        }
    }

    public int ran()
    {
        Random r = new Random();
        int a = r.nextInt(numbers.total_img);
        while( a==0 )
        {
            a = r.nextInt(numbers.total_img);
        }
        return a;
    }

    public class putInDB extends AsyncTask<Void, Void, Void> {

        public String quote1;
        public String author1;

        @Override
        public Void doInBackground(Void... params) {
            if( isError == false )
            {
                P.quote = quote1 ;
                P.author = author1;
                P.Bimg = ran();

                while( arrayList.contains(P.Bimg)  )
                {
                    P.Bimg = ran();
                }

                arrayList.add(P.Bimg);
                if( arrayList.size() > 10 )
                {
                    arrayList.remove(0);
                }

                try
                {
                    handlerDB.addProduct(P);
                }
                catch (Exception e)
                {
                    Log.i("FLOW", " ERROR ");
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("here : ", "##################################");
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void fillDB()
    {
        try
        {
            if( handlerDB.size() < 18000  )
            {

                if( isNetworkAvailable() )
                {
                    new SendDataService.GetQuote().execute(null,null,null);
                }
            }
        }
        catch (Exception e)
        {

        }


    }



    public class LocalBinder extends Binder {
                public SendDataService getService() {
                        return SendDataService .this;
                }
        }

        @Override
        public IBinder onBind(Intent intent) {
                return mBinder;
        }

        @Override
        public void onCreate() {
                super.onCreate();

        }

        @Override
        public void onDestroy() {
                super.onDestroy();
        }

        public void getQuote()
        {
            CountDownTimer c = new CountDownTimer(200000 , 1200) {
                @Override
                public void onTick(long millisUntilFinished) {


                        fillDB();

                }

                @Override
                public void onFinish()
                {
                    if( handlerDB.size() <  18000 )
                    {
                        getQuote();
                    }
                }
            }.start();

        }


        @Override
        public int onStartCommand(final Intent intent, int flags, int startId) {
                handler = new Handler();
                handler.post(new Runnable() {
                        @Override
                        public void run() {

                            P = new products("","");

                            Context context  = getApplicationContext();
                            handlerDB = new MyDBHandler(SendDataService.this , null , null , 1);

                            if( handlerDB.size() > 18000 )
                            {
                                stopService(intent);
                            }
                            else {
                                sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

                                if (!sharedPreferences.contains("prevQuote")) {
                                    sharedPreferences.edit().putString("prevQuote", prev_quote).apply();
                                } else {
                                    prev_quote = sharedPreferences.getString("prevQuote", "");
                                }

                                String temp2 = "0";
                                if (!sharedPreferences.contains("S_status")) {
                                    sharedPreferences.edit().putString("S_status", "0").apply();
                                } else {
                                    temp2 = sharedPreferences.getString("S_status", "");
                                }

                                S_status = true;
                                if( temp2.equals("0") )
                                {
                                    S_status = false;
                                }


                                arrayList = numbers.already_taken_Bimg;
//                                fillDB();
                                getQuote();
                            }
                            if( handlerDB.size() > 18000 )
                            {
                                stopService(intent);
                            }
                        }
                });
                return android.app.Service.START_STICKY;

        }

}

