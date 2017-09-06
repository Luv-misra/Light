package com.leo.conanme.motivation;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends AppCompatActivity {

    public TextView T;

    String quote="";
    ImageView IV_back;
    MyDBHandler handler;
    ImageView imageview;
    RelativeLayout RR_quote;
    boolean happy = true;
    ListView LV;
    RelativeLayout RL;
    boolean displaying = false;
    String author="";
    ArrayAdapter<String> adapter;
    String data[] = {

            " do try the WIDGET for this app which is the main motive " ,
            " simply go to the list where you find the widgets of other apps and you will find the widget for this one too. " ,
            " \n Quotes are brought from the internet and only texts are brought to reduce data usage ",
            " you can see total numbers of quotes in your device currently on the bottom of your screen ",
            " hope you like this app ! :) "

    };
    TextView total;
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


    public void giveInfo(View v)
    {
        RL.setVisibility(View.VISIBLE);
    }
    public void no_info(View v)
    {
        RL.setVisibility(View.INVISIBLE);
    }


    public void setTotal()
    {
        CountDownTimer C = new CountDownTimer( 3000,1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {

                total.setText(Long.toString(handler.size()) + " quotes  currently ");;

            }

            @Override
            public void onFinish() {

                setTotal();

            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View Sview = getWindow().getDecorView();
        int FSCR = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Sview.setSystemUiVisibility(FSCR);
        imageview = (ImageView) findViewById(R.id.imageView);
        TextView IV = (TextView) findViewById(R.id.textView2);

        PrefManager prefManager = new PrefManager(this);


        startService();

        if (prefManager.isFirstTimeLaunch())
        {

            Intent in = new Intent(this, WelcomeActivity.class);
            startActivity(in);

        }

        IV_back = (ImageView)findViewById(R.id.backcircle);

        RR_quote = (RelativeLayout) findViewById(R.id.RR_quote);

        if(Integer.valueOf(Build.VERSION.SDK_INT) < 21)
        {
//            Toast.makeText(this, " lower SDK " + IV.getTextSize(), Toast.LENGTH_SHORT).show();
            IV.setTextSize(35);
            IV.setPadding(0,50,0,50);
            IV.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        T= (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        handler = new MyDBHandler(MainActivity.this,null,null,1);
        numbers.handler = handler;


        LV = (ListView) findViewById(R.id.LV);
        RL = (RelativeLayout) findViewById(R.id.RL);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        LV.setAdapter(adapter);

        total = (TextView) findViewById(R.id.total);

        setTotal();
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
