package com.leo.conanme.motivation;

import java.util.Random;

/**
 * Created by luv on 15/4/17.
 */

public class products {

    public int _id;
    public String quote;
    public String author;
    public int favourite;
    public int repeat_times;
    public int del;
    public int Bimg;

    public products()
    {

    }
    public int ran()
    {
        Random r = new Random();
        int a = r.nextInt(15);
        while( a==0 )
        {
            a = r.nextInt(13);
        }
        return a;
    }
    public products( String Q , String A )
    {
        this.quote = Q;
        this.author = A;
        this.del = this.favourite = this.repeat_times = 0 ;
        Bimg = ran();
    }

}
