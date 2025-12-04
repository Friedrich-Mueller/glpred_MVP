package com.example.glpred;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EnterDataActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_DATE = "com.example.glpred.MESSAGE_DATE";
    public static final String EXTRA_MESSAGE_TIME = "com.example.glpred.MESSAGE_TIME";
    public static final String EXTRA_MESSAGE_GL = "com.example.glpred.MESSAGE_GL";
    public static final String EXTRA_MESSAGE_CU = "com.example.glpred.MESSAGE_CU";
    public static final String EXTRA_MESSAGE_BOLUS = "com.example.glpred.MESSAGE_BOLUS";
    public static final String EXTRA_MESSAGE_BASIS = "com.example.glpred.MESSAGE_BASIS";
    TimePickerDialog time_picker;
    EditText eText_date;
    EditText eText_time;
    DatePickerDialog date_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_data);

        // DatePicker
        eText_date=findViewById(R.id.editText_DATE);

        eText_date.setInputType(InputType.TYPE_NULL);

        eText_date.setShowSoftInputOnFocus(false);
        eText_date.setFocusable(false);
        eText_date.setClickable(true);


        eText_date.setOnClickListener(v -> {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                DatePickerDialog date_picker = new DatePickerDialog(
                        EnterDataActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Update the EditText with the selected date
                            String formattedDate = getString(R.string.date_format,
                                    selectedDay, selectedMonth + 1, selectedYear);
                            eText_date.setText(formattedDate);
                        },
                        year, month, day
                );
                date_picker.show();
        });

        // TimePicker (set to 24h format)
        eText_time=findViewById(R.id.editText_TIME);
        eText_time.setInputType(InputType.TYPE_NULL);
        eText_time.setShowSoftInputOnFocus(false);
        eText_time.setFocusable(false);
        eText_time.setClickable(true);

        eText_time.setOnClickListener(v -> {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                // time picker dialog
                TimePickerDialog time_picker = new TimePickerDialog(
                        EnterDataActivity.this,
                        (tp, selectedHour, selectedMinute) ->{
                                eText_time.setText(selectedHour + ":" + selectedMinute);
//                                eText_time.setText(String.format("%02d:%02d", selectedHour, selectedMinute)); ## in case leading 0s are wanted later
                        }, hour, minutes, true);
                time_picker.show();
        });
    }

    /** Toast Example */
    public void onButtonTabToast(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "Oops!", Toast.LENGTH_LONG);

        myToast.show();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        Intent intent = new Intent(this, ConfirmDataActivity.class);

        EditText editText = findViewById(R.id.editText_DATE);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_DATE, message);

        editText = findViewById(R.id.editText_TIME);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_TIME, message);

        editText = findViewById(R.id.editText_GL);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_GL, message);

        editText = findViewById(R.id.editText_CU);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_CU, message);

        editText = findViewById(R.id.editText_BOLUS);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_BOLUS, message);

        editText = findViewById(R.id.editText_BASIS);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_BASIS, message);


        // Require at least one more Entry
        Boolean b = TextUtils.isEmpty(intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_GL));
        Boolean c = TextUtils.isEmpty(intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_CU));
        Boolean d = TextUtils.isEmpty(intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_BOLUS));
        Boolean e = TextUtils.isEmpty(intent.getStringExtra(EnterDataActivity.EXTRA_MESSAGE_BASIS));


        if (b && c && d && e){
            // Toast
            Toast.makeText(getApplicationContext(), "at least one more entry required..", Toast.LENGTH_SHORT).show();

        } else {
            startActivity(intent);
        }

    }
}
