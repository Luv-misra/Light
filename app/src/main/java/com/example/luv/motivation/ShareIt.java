package com.example.luv.motivation;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Created by luv on 22/4/17.
 */

public class ShareIt {

    public ShareIt(Context v , String ans ) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ans);
        sendIntent.setType("text/plain");
        v.startActivity(Intent.createChooser(sendIntent, "Share via" ));
    }
}
