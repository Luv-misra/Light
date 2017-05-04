package com.example.luv.motivation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Show_all_quotes_one_by_one extends AppCompatActivity {

    MyDBHandler handler;
    Context context;
    products PP;
    View v;
    ImageView liking;
    ImageView next;
    ImageView prev;
    TextView next_sc;
    TextView prev_sc;


    public void liking()
    {
        liking.setVisibility(View.VISIBLE);
        animateHeart(liking);
    }

    public void liked( View v )
    {
        handler.toggleLike(v.getTag().toString());
        if( PP.favourite == 0 )
        {
            liking();
        }
        setView();

    }
    public void share( View v )
    {
        Intent sendIntent = new Intent();
        String ans = "";
        products P = handler.getProductById(Integer.parseInt(v.getTag().toString()));

        ans = P.quote ;
        if( P.author.equals("") )
        {

        }
        else
        {
            ans += "\n\n"+"by : "+P.author;
        }
        ans += "\n\nsent via MOTIVATION";
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ans);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent," share via "));
    }
    public void copy( View v )
    {
        Toast.makeText(this, "copying it "+v.getTag(), Toast.LENGTH_SHORT).show();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String ans = "";
        products P = handler.getProductById(Integer.parseInt(v.getTag().toString()));

        ans = P.quote ;
        if( P.author.equals("") )
        {

        }
        else
        {
            ans += "\n\n"+"by : "+P.author;
        }
        ClipData clip = ClipData.newPlainText("",ans);
        clipboard.setPrimaryClip(clip);

    }


    public void animateHeart(final ImageView view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(400);
        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private Animation prepareAnimation(Animation animation){
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }


    void setView()
    {

        if( numbers.H )
        {
            next.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
        }
        else
        {
            next.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
        }

        PP = new products("no","no");
        PP = handler.getProductById(numbers.one_by_one);

        if( PP==null )
        {
            numbers.one_by_one = 1 ;
            PP = handler.getProductById(numbers.one_by_one);
        }
        //to check for null one

        String back_name = "b" + PP.Bimg;

        Log.i("ONE", "Custom:1 ");
        Log.i("ONE", "Custom: 1.5");
        TextView quoteA = (TextView) findViewById(R.id.quote);
        TextView authorA = (TextView) findViewById(R.id.author);
        RelativeLayout Qback = (RelativeLayout) findViewById(R.id.RR);

        ImageView LIKE  = (ImageView) findViewById(R.id.like);
        ImageView SHARE = (ImageView) findViewById(R.id.share);
        ImageView COPY  = (ImageView) findViewById(R.id.copy);


        Log.i("ONE", "CustomListAdapter: 2");


        if( PP.quote!=null )
        {
            quoteA.setText(PP.quote);

//            int id = this.context.getResources().getIdentifier(this.context.getPackageName()+":drawable/" + back_name, null, null);
//            Qback.setBackgroundResource(id);

            Log.i("ONE 1 ", back_name);
            if( PP.favourite == 1 )
            {
                LIKE.setImageResource(R.drawable.like);
            }
            else
            {
                LIKE.setImageResource(R.drawable.like_no);
            }

            LIKE.setTag(PP._id);
            SHARE.setTag(PP._id);
            COPY.setTag(PP._id);

        }

        if( PP!=null )
        {authorA.setText(PP.author);}

        Log.i("ONE", "CustomListAdapter: 3");



    }


    public void toggle_H( View v )
    {
        numbers.H = !numbers.H ;
        setView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_quotes_one_by_one);
        View Sview = getWindow().getDecorView();
        int FSCR = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Sview.setSystemUiVisibility(FSCR);

        handler = new MyDBHandler(this,null,null,1);
        next = (ImageView) findViewById(R.id.next);
        prev = (ImageView) findViewById(R.id.prev);
        next_sc = (TextView) findViewById(R.id.right_sc);
        prev_sc = (TextView) findViewById(R.id.left_sc);
        liking = (ImageView) findViewById(R.id.liking);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers.one_by_one++;
                if( numbers.one_by_one > handler.size() )
                {
                    numbers.one_by_one = 1 ;
                }
                setView();
            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numbers.one_by_one--;
                if( numbers.one_by_one<=0 )
                {
                    numbers.one_by_one = (int)handler.size() ;
                    if( numbers.one_by_one <= 0 )
                    {
                        numbers.one_by_one = 1 ;
                    }
                }
                setView();
            }
        });

        next_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers.one_by_one++;
                if( numbers.one_by_one > handler.size() )
                {
                    numbers.one_by_one = 1 ;
                }
                setView();
            }
        });


        prev_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numbers.one_by_one--;
                if( numbers.one_by_one<=0 )
                {
                    numbers.one_by_one = (int)handler.size()-1 ;
                    if( numbers.one_by_one <= 0 )
                    {
                       numbers.one_by_one = 1 ;
                    }
                }
                setView();

            }
        });

        Log.i("ONE", "onCreate: reached here");

        context = this;

        setView();

    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }
}
