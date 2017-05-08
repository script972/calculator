package com.example.script972.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Math.tanh;

public class Tregeo extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tregeo);
        editText=(EditText)findViewById(R.id.geoResult);
        Intent intent=getIntent();
        editText.setText(intent.getStringExtra("value"));

    }

    //process click key
    //обработка нажатия клавиш
    public void geoClick(View v){
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.sin:{
                intent.putExtra("value",String.valueOf(sin(Double.valueOf(editText.getText().toString()))));
                Log.i("sin", String.valueOf(sin(30)));//put in log for check true result
                break;
            }
            case R.id.cos:{
                intent.putExtra("value",String.valueOf(cos(Double.valueOf(editText.getText().toString()))));
                break;
            }
            case R.id.tag:{
                intent.putExtra("value",String.valueOf(tan(Double.valueOf(editText.getText().toString()))));
                break;
            }
            case R.id.ctag:{
                intent.putExtra("value",String.valueOf(1/tan(Double.valueOf(editText.getText().toString()))));
                break;
            }
        }
        setResult(1,intent);
        finish();
    }
}
