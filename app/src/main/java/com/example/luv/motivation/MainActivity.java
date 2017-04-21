package com.example.luv.motivation;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    public TextView T;
    Button B;

    String quote="";
    String prev_quote="";
    String prev_author="";
    TextView finalView;
    MyDBHandler handler;
    String flow = "FLOW" ;
    CountDownTimer countdown;
    String author="";
    Boolean isError = false;
    EditText name;

    public OkHttpClient okHttpClient;
    public Request request;
    public String url = "http://www.mocky.io/v2/58f166262400004706f696f6" ;
    String s="";



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
            Log.i("flow", "quote : "+quote+"\n"+"by : "+author);
            if( isError==false && !prev_quote.equals(quote))
            {
                Log.i(flow, "got the new quote");
                putInDB temp = new putInDB();
                temp.quote1 = quote;
                temp.author1 = author;
                Log.i("flow", "temp.quote1 = "+temp.quote1);
                Log.i("flow", "temp.author1 = "+temp.author1);
                temp.execute();
            }
        }
    }


    public class putInDB extends AsyncTask<Void, Void, Void> {

        public String quote1;
        public String author1;

        @Override
        public Void doInBackground(Void... params) {
            if( isError == false )
            {
                products P = new products(quote1,author1);
                handler.addProduct(P);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(flow, "quote by "+author1+"added in db" );
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
        int count = 0;
        while( count<10 && isNetworkAvailable() )
        {
            new GetQuote().execute(null,null,null);
            count++;
        }
    }

    public void show_by_id(View v)
    {
        Integer i;
        i = Integer.parseInt(name.getText().toString()) ;
        String temp = handler.getQuoteById(i);
        T.setText(temp);
    }

    public void logDB(View v)
    {
        String temp = handler.getAllQuotes();
        T.setText(temp);
        String[] each = temp.split("##");
        for( int i=0 ; i<each.length ; i++ )
        {
            Log.i("all quotes : ", each[i] );
        }
    }

    public void clicked(View v)
    {
        Log.i(flow, "clicked the button");
        fillDB();
    }

    public void show_all_quotes( View v )
    {
        Intent i = new Intent(this , show_all_quotes.class);
        startActivity(i);
    }
    public void show_all_fav( View v )
    {
        Intent i = new Intent(this , show_all_fav.class);
        startActivity(i);
    }

    public void nextQuote(View v)
    {
        Log.i("widget main_activity : ", "nextQuote: ");
//        finalView.setText(handler.getQuoteById(numbers.quote_id));
//        numbers.quote_id++;
//        if( numbers.quote_id > handler.size() )
//        {
//            numbers.quote_id = 0;
//        }
    }

    public void startService()
    {
        Intent intent = new Intent(this , SendDataService.class);
        startService(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T= (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        handler = new MyDBHandler(MainActivity.this,null,null,1);
        numbers.handler = handler;
//        finalView = (TextView) findViewById(R.id.finalView);
        startService();
    }
}
