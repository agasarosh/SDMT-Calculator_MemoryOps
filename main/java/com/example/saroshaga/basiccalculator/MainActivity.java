package com.example.saroshaga.basiccalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    TextView res;
    double temp;
    Button b1;
    String expression=" ";
    SQLiteDatabase db;
    private FileWriter fw;
    private FileOutputStream f1;
    private String mem_var = "0.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = (TextView) findViewById(R.id.Result);
        b1 = (Button) findViewById(R.id.change);
        db = openOrCreateDatabase("my_db", MODE_PRIVATE, null);

        try {
            f1 = openFileOutput("dump.txt", MODE_PRIVATE);
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Results.class);
                startActivity(intent);
            }
        });

    }


    public void take_in(View v)
    {
        Button b=(Button)findViewById(v.getId());

        expression=expression+b.getText().toString();
        res.setText(expression);
    }


    public void trigOps(View v)
    {
        expression=res.getText().toString();

        String op = " ";
        Button b=(Button)findViewById(v.getId());
        op=op+b.getText().toString();

        switch (v.getId()) {


            case R.id.buttonsin:
                res.setText(Double.toString(Math.sin(Double.parseDouble(expression) * (3.142 / 180))));
                break;
            case R.id.buttoncos:
                res.setText(Double.toString(Math.cos(Double.parseDouble(expression) * (3.142 / 180))));
                break;
            case R.id.buttontan:
                res.setText(Double.toString(Math.tan(Double.parseDouble(expression) * (3.142 / 180))));
                break;


        }

        String ans = res.getText().toString();
        if(f1!=null) {
            //CharSequence cs=ans.toString();
            try {
                f1.write((op+"("+expression+")"+" = "+ans+"\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        res.setText(ans);


        expression="";
    }

    public void simpleMath(View v) throws IOException
    {
        String expr=res.getText().toString();

        Cursor c=db.rawQuery("select "+expr+" as result",null);

        c.moveToFirst();
        String ans=c.getString(0);
        if(f1!=null) {
            //CharSequence cs=ans.toString();
            f1.write((expr+" = "+ans+"\n").getBytes());
        }
        res.setText(ans);
        expression="";

    }

    public void clear(View v)
    {
        res.setText("0");
        expression="";
    }

    public void mem_recall(View v)
    {
        expression=res.getText().toString();
        res.setText(res.getText()+mem_var);
    }

//    public void mem_minus(View v)
//    {
//        mem_var=mem_var-res.getText();
//        res.setText("("+res.getText()+")+");
//    }

    public void mem_clr(View v)
    {
        mem_var="0.0";
    }

    public void mem_save(View v)
    {
        mem_var=res.getText().toString();
    }
}

