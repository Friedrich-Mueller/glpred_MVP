package com.example.glpred;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmDataActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    String date, time, glucose_level, carb_units, bolus, basis;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Start or create database
        myDb = new DatabaseHelper(this);


        // Get the Intent that started this activity
        Intent intent = getIntent();

        // extract the strings
        date = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_DATE);
        time = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_TIME);
        glucose_level = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_GL);
        carb_units = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_CU);
        bolus = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_BOLUS);
        basis = intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_BASIS);

        if (glucose_level.length() == 0){
            glucose_level = "0";
        }

        if (carb_units.length() == 0){
            carb_units = "0.0";
        }

        if (bolus.length() == 0){
            bolus = "0";
        }

        if (basis.length() == 0){
            basis = "0";
        }


        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView_DATE);
        textView.setText(date);

        textView = findViewById(R.id.textView_TIME);
        textView.setText(time);

        textView = findViewById(R.id.textView_GL);
        textView.setText(glucose_level);

        textView = findViewById(R.id.textView_CU);
        textView.setText(carb_units);

        textView = findViewById(R.id.textView_BOLUS);
        textView.setText(bolus);

        textView = findViewById(R.id.textView_BASIS);
        textView.setText(basis);

        btnAddData = findViewById(R.id.button_confirm_entry);

        AddData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(date, time, glucose_level, carb_units, bolus, basis);
                        if (isInserted) {
                            Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                        } else{

                            Toast.makeText(getApplicationContext(), "Data not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
