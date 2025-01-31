package com.leo.conanme.motivation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class show_all_fav extends AppCompatActivity {

    MyDBHandler handler;
    ListView list;
    ArrayList<String> author;
    ArrayList<products> allContent;
    FavCustomListAdapter adapter;

    String[] author1={};

    public void liked( View v )
    {
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

        View Sview = getWindow().getDecorView();
        int FSCR = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Sview.setSystemUiVisibility(FSCR);



        handler = new MyDBHandler(this,null,null,1);

        allContent = handler.getAllFavContents();

        author = handler.getAllFavAuthorsOPT();
        list = (ListView) findViewById(R.id.list);

        adapter=new FavCustomListAdapter(this,author,allContent);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }
}
