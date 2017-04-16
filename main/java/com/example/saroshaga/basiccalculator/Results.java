package com.example.saroshaga.basiccalculator;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Results extends AppCompatActivity {


    private FileInputStream r;
    private TextView t;
    private byte[] buffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        int n;
        buffer=new byte[1024];
        StringBuffer fileContent=new StringBuffer();
        t = (TextView) findViewById(R.id.res1);
        try{

            r=openFileInput("dump.txt");
            while((n=r.read(buffer))!=-1)
            {
                fileContent.append(new String(buffer,0,n));
            }
            t.setText(fileContent.toString());

        }catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    public void back(View v)
    {
        finish();
    }
}

