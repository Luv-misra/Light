package com.example.luv.motivation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class show_all_fav extends AppCompatActivity {

    MyDBHandler handler;
    ListView list;
    ArrayList<String> author;
    ArrayList<products> allContent;
    FavCustomListAdapter adapter;

    String[] author1={};

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
        setContentView(R.layout.activity_show_all_fav);

        handler = numbers.handler;

        allContent = handler.getAllFavContents();

        String author2 = handler.getAllFavAuthors();
        author1 = author2.split("##");

        list = (ListView) findViewById(R.id.list);
        author = new ArrayList<>(Arrays.asList(author1));

        adapter=new FavCustomListAdapter(this,author,allContent);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

    }
}
