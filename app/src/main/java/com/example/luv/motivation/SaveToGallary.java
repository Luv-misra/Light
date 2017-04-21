package com.example.luv.motivation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by luv on 30/3/17.
 */



// just need to include the following code in any activity to save that image
    // create a instance of the class and start using it
//SaveToGallary a = new SaveToGallary();
//        a.saveToInternalStorage(mbitmap,"image test"); bitmap is the image provided and image test is the name of the folder its gonna be stored
//        sendBroadcast(a.intent);

public class SaveToGallary extends Activity {


    static String nameoffolder  = " ";

    public File new_file;

    public Intent intent;

    public void saveToInternalStorage(Bitmap bitmap , String namefolder) {
        nameoffolder = namefolder;
        FileOutputStream fileOutputStream = null;
        Log.i("reached", "saveToInternalStorage: 0 ");
        File file = getDisc();
        Log.i("reached", "saveToInternalStorage: 3 ");
//        if( !file.exists() && !file.mkdirs() )
//        {
//            Toast.makeText(this, "cannot create", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Log.i("reached", "saveToInternalStorage: 4 ");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmsshhmmss");
        String date=simpleDateFormat.format(new Date());
        Log.i("reached", "saveToInternalStorage: 5 ");
        String name = "Img"+date+".jpg";
        String file_name = file.getAbsolutePath()+"/"+name;
        new_file = new File(file_name);
        Log.i("reached", "saveToInternalStorage: 6 ");
        try {

            fileOutputStream = new FileOutputStream(new_file);
            Log.i("reached", "saveToInternalStorage: 7 ");
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Log.i("reached", "saveToInternalStorage: 8 ");
            //Toast.makeText(this, "file saved successfully", Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            Log.i("reached", "saveToInternalStorage: 9 ");
            fileOutputStream.close();

        } catch (Exception e){
            //Toast.makeText(this, "not saved!!", Toast.LENGTH_SHORT).show();
        }
        Log.i("reached", "saveToInternalStorage: 10 ");

        refreshGallery(new_file);

    }

    public void refreshGallery(File file)
    {
        intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));

    }

    public File getDisc(){
        Log.i("reached", "saveToInternalStorage: 1 ");
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //image test will be the name of the folder
        Log.i("reached", "saveToInternalStorage: 2 ");
        return new File(file , nameoffolder);
    }



}
