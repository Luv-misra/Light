package com.example.luv.motivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    Switch S1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String temp = "0";
        if( numbers.unlock )
        {
            temp = "1";
        }

        sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        if( !sharedPreferences.contains("unlock") )
        {
            sharedPreferences.edit().putString("unlock",temp).apply();
        }
        else
        {
            temp = sharedPreferences.getString("unlock","");
            if( temp.equals("0") )
            {
                numbers.unlock = false;
            }
            else
            {
                numbers.unlock = true;
            }
        }

        S1 = (Switch) findViewById(R.id.switch1);

        if( numbers.unlock )
        {
            S1.setChecked(true);
        }
        else
        {
            S1.setChecked(false);
        }

        S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean state = S1.isChecked();
                String temp = "1";
                if( state )
                {
                    numbers.unlock = true;
                }
                else
                {
                    numbers.unlock = false;
                    temp = "0";
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("unlock",temp);
                editor.commit();

            }
        });

    }
}
