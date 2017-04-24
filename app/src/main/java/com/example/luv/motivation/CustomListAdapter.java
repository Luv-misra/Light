package com.example.luv.motivation;

/**
 * Created by luv on 26/3/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;



public class CustomListAdapter extends ArrayAdapter<String> {


    Context context;
    ArrayList<String> author1;
    ArrayList<products> allContents;
    MyDBHandler handler;


    public CustomListAdapter(@NonNull Context context, @NonNull ArrayList<String> author, @NonNull ArrayList<products> allContent1 ) {
        super(context, 0, author);
        Log.i("reach", "CustomListAdapter: ");
        this.context = context;
        author1 = author;
        allContents = allContent1;
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {

        String back_name = "b" + allContents.get(position).Bimg;

        Log.i("reach", "CustomListAdapter:1 ");
        View rowView = View.inflate(context, R.layout.single_quote_style, null);
        Log.i("reach", "CustomListAdapter: 1.5");
        TextView quoteA = (TextView) rowView.findViewById(R.id.quote);
        TextView authorA = (TextView) rowView.findViewById(R.id.author);
        RelativeLayout Qback = (RelativeLayout) rowView.findViewById(R.id.RR);

        ImageView LIKE  = (ImageView) rowView.findViewById(R.id.like);
        ImageView SHARE = (ImageView) rowView.findViewById(R.id.share);
        ImageView COPY  = (ImageView) rowView.findViewById(R.id.copy);


        Log.i("reach", "CustomListAdapter: 2");


        if( allContents.get(position).quote!=null )
        {
            quoteA.setText(allContents.get(position).quote);

            int id = this.context.getResources().getIdentifier(this.context.getPackageName()+":drawable/" + back_name, null, null);
            Qback.setBackgroundResource(id);

            Log.i("PPPPPPPPPPPP", back_name);
            if( allContents.get(position).favourite == 1 )
            {
                LIKE.setImageResource(R.drawable.like);
            }

            LIKE.setTag(allContents.get(position)._id);
            SHARE.setTag(allContents.get(position)._id);
            COPY.setTag(allContents.get(position)._id);

        }

        if( author1.get(position)!=null )
        {authorA.setText(allContents.get(position).author);}

        Log.i("reach", "CustomListAdapter: 3");
        return rowView;
    }
}
