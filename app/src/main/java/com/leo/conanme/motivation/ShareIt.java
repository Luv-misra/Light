package com.leo.conanme.motivation;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by luv on 22/4/17.
 */

public class ShareIt {

    public ShareIt(Context v , String ans ) {

        Log.i("ONONONON", "ShareIt: got outside for sharing");
        Intent sendIntent = new Intent();
        Log.i("ONONONON", "ShareIt: intent created ");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ans);
        sendIntent.setType("text/plain");
        Log.i("ONONONON", "ShareIt: done se just pehle");
        v.startActivity(Intent.createChooser(sendIntent, "Share via" ));
        Log.i("ONONONON", "ShareIt: done ");
    }
}
