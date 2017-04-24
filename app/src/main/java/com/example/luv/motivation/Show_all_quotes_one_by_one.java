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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


    public void liked( View v )
    {
        Toast.makeText(this, "liked "+v.getTag(), Toast.LENGTH_SHORT).show();
        handler.toggleLike(v.getTag().toString());

        setView();

    }
    public void share( View v )
    {
        Toast.makeText(this, "sharing "+v.getTag(), Toast.LENGTH_SHORT).show();
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


    void next(View v)
    {
        numbers.one_by_one++;
        setView();
    }
    void prev(View v)
    {
        numbers.one_by_one--;
        if( numbers.one_by_one<=0 )
        {
            numbers.one_by_one = 1 ;
        }
        setView();
    }
    void setView()
    {

        PP = new products("no","no");
        PP = handler.getProductById(numbers.one_by_one);

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

            int id = this.context.getResources().getIdentifier(this.context.getPackageName()+":drawable/" + back_name, null, null);
            Qback.setBackgroundResource(id);

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_quotes_one_by_one);

        Log.i("ONE", "onCreate: reached here");

        context = this;

        handler = new MyDBHandler(this,null,null,1);
        setView();

    }
}
