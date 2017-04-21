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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class show_all_quotes extends AppCompatActivity {

    MyDBHandler handler;
    ListView list;
    ArrayList<String> author;
    ArrayList<String> quote;
    ArrayList<String> id;
    ArrayList<products> allContent;
    ArrayList<String> Bimg;
    ImageView IV;
    Bitmap mbitmap;
    CustomListAdapter adapter;
    View v;

    String[] author1={};
    String[] id1={};
    String[] Bimg1={};
    String[] quotes1={};


    public void logString( String[] input )
    {
        for( int i=0;i<input.length;i++ )
        {
            Log.i("DISPLAYING STRING : ", input[i]  );
        }
    }


    public void liked( View v )
    {
        Toast.makeText(this, "liked "+v.getTag(), Toast.LENGTH_SHORT).show();
        handler.toggleLike(v.getTag().toString());
        ImageView LIKE = (ImageView) v.findViewById(R.id.like);

        products P = handler.getProductById(Integer.parseInt(v.getTag().toString()));
        if( P.favourite == 1 )
        {
            LIKE.setImageResource(R.drawable.like);
        }
        else
        {
            LIKE.setImageResource(R.drawable.like_no);
        }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_quotes);

        handler = numbers.handler;

//        allContent = new ArrayList<>();
        allContent = handler.getAllContents();


        IV = (ImageView)findViewById(R.id.test);

        String author2 = handler.getAllAuthors();
        author1 = author2.split("##");

        String quote2 = handler.getAllQuotes();
        quotes1 = quote2.split("##");

        String Bimg2 = handler.getAllBimg();
        Bimg1 = Bimg2.split("##");

        Log.i("id jo aayi hai : ", Bimg2);

        logString(author1);
        logString(quotes1);

//        String id2 = handler.getAllId();
//        id1 = id2.split("##");

        list = (ListView) findViewById(R.id.list);
        author = new ArrayList<>(Arrays.asList(author1));
        quote = new ArrayList<>(Arrays.asList(quotes1));
        Bimg = new ArrayList<>(Arrays.asList(Bimg1));
//        id = new ArrayList<>(Arrays.asList(id1));

        adapter=new CustomListAdapter(this,author,quote,id,Bimg,allContent);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

    }
}
