package com.example.glpred;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    /** Toast Example */
    public void onButtonTabToast(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "Oops!", Toast.LENGTH_LONG);

        myToast.show();
    }

    /** Called when the user taps the Send button */
    public void gotoEnterDataActivity(View view) {

        Intent intent = new Intent(this, EnterDataActivity.class);

        startActivity(intent);
    }
}