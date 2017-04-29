package com.example.luv.motivation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

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

import static android.R.attr.animation;

public class MainActivity extends AppCompatActivity {

    public TextView T;

    String quote="";
    ImageView IV_back;
    MyDBHandler handler;
    ImageView imageview;
    RelativeLayout RR_quote;
    boolean happy = true;
    boolean displaying = false;
    String author="";
    EditText name;


    public void show_by_id(View v)
    {
        if( displaying )
        {
            return;
        }
        RR_quote.setVisibility(View.VISIBLE);
        displaying = true;
    }

    public void show_quote_by_id_tick( View v )
    {
        Log.i("show_by_id", "show_by_id: ");
        Integer i;
        Log.i("show_by_id", "show_by_id: ");
        String ans = "-1";
        try {
            ans = name.getText().toString();
            i = Integer.parseInt(ans) ;
        }
        catch (Exception e)
        {
            ans = "-1";
            i = -1;
        }

        Log.i("show_by_id", "show_by_id: "+i.toString());
        String temp = "invalid or overflow";
        try
        {
            temp = handler.getQuoteById(i);

        }
        catch ( Exception e )
        {
            temp = "invalid or overflow";
        }
        if( temp == null || temp.equals("") )
        {
            temp = "invalid or overflow";
        }
        if( !temp.equals("invalid or overflow") )
        {
            if(happy)
            {
                new ParticleSystem(this, 100, R.drawable.par , 500 )
                        .setSpeedRange(0.1f, 0.5f)
                        .oneShot(v, 100);

            }
//            happy = false;
        }
        T.setText(temp);
    }

    public void cancel_quote(View v)
    {
        RR_quote.setVisibility(View.GONE);
        happy = true;
        displaying = false;
    }

    public void show_all_quotes( View v )
    {
        if( displaying )
        {
            return;
        }
        Intent i = new Intent(this , show_all_quotes.class);
        startService();
        startActivity(i);
        finish();
    }
    public void show_all_fav( View v )
    {
        if( displaying )
        {
            return;
        }
        Intent i = new Intent(this , show_all_fav.class);
        startService();
        startActivity(i);
        finish();
    }


    public void startService()
    {
        Intent intent = new Intent(this , SendDataService.class);
        startService(intent);
    }

    public void one_by_one(View v)
    {
        if( displaying )
        {
            return;
        }
        Intent intent = new Intent(this , Show_all_quotes_one_by_one.class);
        numbers.one_by_one = 1;
        Log.i("ONE", "one_by_one: going fo it ");
        startActivity(intent);
        finish();
    }

    public void rotate()
    {

        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat( IV_back ,
                "rotation", 0f, 360f);
        imageViewObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        imageViewObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        imageViewObjectAnimator.setInterpolator(new AccelerateInterpolator());
        imageViewObjectAnimator.setDuration(70000);
        imageViewObjectAnimator.start();
    }


    void startBACK()
    {

        Log.i("AAAA", "startBACK: 4 ");
        String back_name = "e" + Integer.toString(numbers.back_img);
        numbers.back_img++;
        if( numbers.back_img > numbers.total_back_img )
        {
            numbers.back_img = 1;
        }
        int id123 = MainActivity.this.getResources().getIdentifier(MainActivity.this.getPackageName()+":drawable/" + back_name, null, null);
        imageview.setImageResource(id123);

        Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        imageview.startAnimation(fadeIn);

        Log.i("AAAA", "startBACK: 1 ");
        fadeIn.setRepeatCount(Animation.INFINITE);
        fadeIn.getRepeatMode();
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                Log.i("AAAA", "startBACK: 2 ");

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeout);
                Log.i("AAAA", "startBACK: 3 ");


                CountDownTimer c2 = new CountDownTimer(6300,6300) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        imageview.startAnimation(fadeOut);

                    }
                }.start();


                CountDownTimer c = new CountDownTimer(9000,9000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        startBACK();

                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View Sview = getWindow().getDecorView();
        int FSCR = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Sview.setSystemUiVisibility(FSCR);
        imageview = (ImageView) findViewById(R.id.imageView);

        PrefManager prefManager = new PrefManager(this);

        IV_back = (ImageView)findViewById(R.id.backcircle);

        startService();

        RR_quote = (RelativeLayout) findViewById(R.id.RR_quote);

        if (prefManager.isFirstTimeLaunch())
        {
            Intent in = new Intent(this, WelcomeActivity.class);
            Log.i("WELCOME", "onCreate: going in for welcome ");
            startActivity(in);
            Log.i("WELCOME", "onCreate:out of it Welcome ");
        }

        T= (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        handler = new MyDBHandler(MainActivity.this,null,null,1);
        numbers.handler = handler;


        startBACK();

//        //BACKGROUND
//        // Generate color1 before starting the thread
//        int red1 = (int)(Math.random() * 128 + 127);
//        int green1 = (int)(Math.random() * 128 + 127);
//        int blue1 = (int)(Math.random() * 128 + 127);
//        color1 = 0xff << 24 | (red1 << 16) |
//                (green1 << 8) | blue1;
//
//        BB = false;
//
//
//        new Thread() {
//            public void run() {
//                while(true) {
//                    try {
//                        if(BB )
//                        {
//                            Thread.sleep(1500);
//                        }
//                        BB = true;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            int red2 = (int)(Math.random() * 128 + 127);
//                            int green2 = (int)(Math.random() * 128 + 127);
//                            int blue2 = (int)(Math.random() * 128 + 127);
//                            int color2 = 0xff << 24 | (red2 << 16) |
//                                    (green2 << 8) | blue2;
//
//                            View v = findViewById(R.id.RR);
//                            ObjectAnimator anim = ObjectAnimator.ofInt(v, "backgroundColor", color1, color2);
//
//
//                            anim.setEvaluator(new ArgbEvaluator());
//                            anim.setRepeatCount(ValueAnimator.INFINITE);
//                            anim.setRepeatMode(ValueAnimator.REVERSE);
//                            anim.setDuration(1500);
//                            anim.start();
//
//                            color1 = color2;
//
//                        }
//                    });
//                }
//            }
//        }.start();
//
//        //BACKGROUND


    }
}
