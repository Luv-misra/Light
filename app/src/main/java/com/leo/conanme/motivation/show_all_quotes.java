package com.leo.conanme.motivation;

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
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class show_all_quotes extends AppCompatActivity {

    MyDBHandler handler;
    ListView list;
    ArrayList<String> author;
    ArrayList<products> allContent;
    ImageView IV;
    Bitmap mbitmap;
    CustomListAdapter adapter;
    Context context;
    View v;
    TextView let;
    RelativeLayout RR;

    String[] author1={};
    boolean go = false;



    public void logString( String[] input )
    {
        for( int i=0;i<input.length;i++ )
        {
//            Log.i("DISPLAYING STRING : ", input[i]  );
        }
    }


    public Bitmap drawTextToBitmap(Context mContext,  int resourceId,  String mText) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(255,255,255));
            // text size in pixels
            paint.setTextSize((int) (20 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width())/6;
            int y = (bitmap.getHeight() + bounds.height())/5;

            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;
        } catch (Exception e) {
            return null;
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

        String back_name = "b"+Integer.toString(P.Bimg);
//        String back_name = "b" + Bimg.get(position);
        int id = this.getResources().getIdentifier(this.getPackageName()+":drawable/" + back_name, null, null);
        mbitmap = drawTextToBitmap(this, id , "testing" );
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
//        mbitmap = mbitmap.copy(Bitmap.Config.ARGB_8888, true);
//        IV.setImageBitmap(mbitmap);
    }


    public class LongOperation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Log.i("working","hue -1");
            return;
        }

        @Override
        protected Void doInBackground(Void... params) {

//            Log.i("working","hue 0");
            allContent = handler.getAllContents();
//            Log.i("working","hue 0.5");
            author = handler.getAllAuthorsEFF();
//            Log.i("working","hue 0.9");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            Log.i("working","hue 1");
            let.setText("");
            RR.setBackgroundResource(R.drawable.single2);
            adapter=new CustomListAdapter(context,author,allContent);
//            Log.i("working","hue 2");
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_quotes);

        View Sview = getWindow().getDecorView();
        int FSCR = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Sview.setSystemUiVisibility(FSCR);


        handler = new MyDBHandler(this,null,null,1);

        let = (TextView) findViewById(R.id.let);
        context = this;

//        Log.i("working", " 1 ");
        RR = (RelativeLayout) findViewById(R.id.RR);
//        Log.i("working", " 2 ");
        list = (ListView) findViewById(R.id.list);

//        Log.i("working", " 3 ");
        new LongOperation().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }
    @Override
    public void onBackPressed() {
//        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }
}
