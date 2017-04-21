package com.example.luv.motivation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by luv on 15/4/17.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2 ;
    public static final String DATABASE_NAME = "products.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUOTE = "quote";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_FAVOURITE = "favourite";
    public static final String COLUMN_REPEAT_TIMES = "repeated" ;
    public static final String COLUMN_DEL = "del";
    public static final String COLUMN_BIMG = "bimg";
    products P;


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_QUOTE + " TEXT ," +
                COLUMN_AUTHOR + " TEXT ," +
                COLUMN_FAVOURITE + " INTEGER ," +
                COLUMN_REPEAT_TIMES + " INTEGER ," +
                COLUMN_DEL + " INTEGER ," +
                COLUMN_BIMG + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);

    }


    //Add a new row to the database
    public void addProduct( products P )
    {
        ContentValues values = new ContentValues();
        values.put( COLUMN_QUOTE,P.quote );
        values.put( COLUMN_AUTHOR,P.author );
        values.put( COLUMN_FAVOURITE,P.favourite );
        values.put( COLUMN_REPEAT_TIMES,P.repeat_times );
        values.put( COLUMN_DEL,P.del );
        values.put( COLUMN_BIMG,P.Bimg );
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null ,values);
        Log.i("DB : ", "addProduct: Product isadded.......congrats...to you");
        db.close();
    }


    //Delete a product from the database
    public void deleteProducts_by_id( Integer i )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + i.toString() + "\";" );
    }

    public void deleteProducts_by_author( String s )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_AUTHOR + "=\"" + s + "\";" );
    }

    //return all ids
    public String getAllId()
    {
        String allId = "";
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen())
        {
            Log.i("hello : 1 : 1 : 1 ", " db is open ");
        }
        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor c = db.rawQuery(query,null);
        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_ID))!=null )
            {
                allId += c.getString(c.getColumnIndex(COLUMN_ID));
                allId += "##";
            }
            c.moveToNext();
        }
        return allId;
    }


    //Return all quotes in DB as a string
    public String getAllQuotes()
    {
        Log.i(" hello ", "getAllQuotes: inside it ");
        String allQuotes = "";
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen())
        {
            Log.i("hello : : : : : : ", " db is open ");
        }
        String query = "SELECT * FROM " + TABLE_PRODUCTS;

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);

        //Move to the first row in your results
        c.moveToFirst();

        Log.i("hello : ", "logDB:after it ");
        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_QUOTE))!=null )
            {
                Log.i("hello", c.getString(c.getColumnIndex(COLUMN_QUOTE)));
                allQuotes += c.getString(c.getColumnIndex(COLUMN_QUOTE));
                allQuotes += "##";
            }
            c.moveToNext();
        }

        db.close();
        return allQuotes;
    }

    public int size()
    {
        Integer res = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor c = db.rawQuery(query,null);
        if( c!=null )
        {
            c.moveToLast();
            res = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
        }
        return res;
    }

    //return quote by id
    public String getQuoteById( Integer i )
    {
        String res = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + i.toString() + "\";";
        Cursor c = db.rawQuery(query,null);
        if( c!=null && c.moveToFirst() )
        {
            c.moveToFirst();
            res = c.getString(c.getColumnIndex(COLUMN_QUOTE));
        }
        Log.i("widget", "Returning "+res);
        return res;
    }


    //Return all quotes in DB as a string
    public String getAllAuthors()
    {
        String allAuthors = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1 ";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_AUTHOR))!=null )
            {
                allAuthors += c.getString(c.getColumnIndex(COLUMN_AUTHOR));
                allAuthors += "##";
            }
            c.moveToNext();
        }

        db.close();
        return allAuthors;
    }

    //Return all quotes in DB as a string
    public String getAllFavAuthors()
    {
        String allAuthors = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FAVOURITE + "=\"" + "1" + "\";";
        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_AUTHOR))!=null )
            {
                allAuthors += c.getString(c.getColumnIndex(COLUMN_AUTHOR));
                allAuthors += "##";
            }
            c.moveToNext();
        }

        db.close();
        return allAuthors;
    }

    //return product by id
    public products getProductById( Integer i )
    {
        products P = new products();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + i.toString() + "\";";
        Cursor c = db.rawQuery(query,null);
        if( c!=null && c.moveToFirst() )
        {
            c.moveToFirst();
            P._id           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
            P.quote         = c.getString(c.getColumnIndex(COLUMN_QUOTE));
            P.author        = c.getString(c.getColumnIndex(COLUMN_AUTHOR));
            P.favourite     = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_FAVOURITE)));
            P.repeat_times  = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_REPEAT_TIMES)));
            P.del           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_DEL)));
            P.Bimg          = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_BIMG)));
        }
//        Log.i("widget", "Returning "+res);
        return P;
    }


    //toggle LIKE
    public void toggleLike( String  s )
    {
        Log.i("hello : ", "toggleLike: ");
        SQLiteDatabase db = getWritableDatabase();
        String temp = "1";
        products P = getProductById(Integer.parseInt(s));
        if( P.favourite == 1 )
        {
            temp = "0";
        }
        String query = " UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_FAVOURITE + "=" + temp + " WHERE " + COLUMN_ID + "=\"" + s + "\";";
        db.execSQL(query);
        db.close();
    }

    //Return all quotes in DB as a string
    public ArrayList<products> getAllContents()
    {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1 ";

        ArrayList<products> res = new ArrayList<>();
        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_AUTHOR))!=null )
            {

                P = new products();
                P._id           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
                P.quote         = c.getString(c.getColumnIndex(COLUMN_QUOTE));
                P.author        = c.getString(c.getColumnIndex(COLUMN_AUTHOR));
                P.favourite     = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_FAVOURITE)));
                P.repeat_times  = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_REPEAT_TIMES)));
                P.del           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_DEL)));
                P.Bimg          = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_BIMG)));
                res.add(P);
            }
            c.moveToNext();
        }

        db.close();
        return res;
    }


    //Return all quotes in DB as a string
    public ArrayList<products> getAllFavContents()
    {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FAVOURITE + "=\"" + "1" + "\";";

        ArrayList<products> res = new ArrayList<>();
        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_AUTHOR))!=null )
            {

                P = new products();
                P._id           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
                P.quote         = c.getString(c.getColumnIndex(COLUMN_QUOTE));
                P.author        = c.getString(c.getColumnIndex(COLUMN_AUTHOR));
                P.favourite     = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_FAVOURITE)));
                P.repeat_times  = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_REPEAT_TIMES)));
                P.del           = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_DEL)));
                P.Bimg          = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_BIMG)));
                res.add(P);
            }
            c.moveToNext();
        }

        db.close();
        return res;
    }


    //get all Back img id
    public String getAllBimg()
    {
        String allBimg = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while( !c.isAfterLast() )
        {
            if( c.getString(c.getColumnIndex(COLUMN_AUTHOR))!=null )
            {
                allBimg += c.getString(c.getColumnIndex(COLUMN_BIMG));
                allBimg += "##";
            }
            c.moveToNext();
        }

        db.close();
        return allBimg;
    }


}


















