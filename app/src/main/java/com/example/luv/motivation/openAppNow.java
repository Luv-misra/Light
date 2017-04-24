package com.example.luv.motivation;

import android.content.Context;
import android.content.Intent;

/**
 * Created by luv on 22/4/17.
 */

public class openAppNow {

    public openAppNow(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }
}
