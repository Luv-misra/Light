package com.example.luv.motivation;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by luv on 28/4/17.
 */

public class save {

    RelativeLayout RR ;
    TextView quote1;
    TextView author1;
    Bitmap bitmap;
    ImageView img1;

    public save( Context context , String quote , String author , int imgId )
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.save, null);
        RR = (RelativeLayout) view.findViewById(R.id.main);
        quote1 = (TextView) view.findViewById(R.id.quote);
        author1 = (TextView) view.findViewById(R.id.author);
        img1 = (ImageView) view.findViewById(R.id.back);

        quote1.setText(quote);
        author1.setText(author);
        img1.setImageResource(imgId);

        RR.setDrawingCacheEnabled(true);
        RR.buildDrawingCache();
        bitmap = RR.getDrawingCache();
        RR.setDrawingCacheEnabled(false);
    }
    public Bitmap getBitmap()
    {
        return bitmap;
    }

}
