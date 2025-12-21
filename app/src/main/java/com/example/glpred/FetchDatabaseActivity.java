package com.example.glpred;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FetchDatabaseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CSV = 100;
    // This variable will hold the CSV data as a List of String arrays
    private List<String[]> csvData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_database);

        Button openDownloadsButton = findViewById(R.id.button_FetchDatabaseActivity);
        openDownloadsButton.setOnClickListener(v -> openDownloadsFolder());
    }

    // Open the Downloads folder / file picker
    private void openDownloadsFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv"); // Only CSV files
        startActivityForResult(intent, REQUEST_CODE_PICK_CSV);
    }

    // Handle the picked file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CSV && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                readCsv(uri);
            }
        }
    }

    // Read CSV file into csvData variable
    private void readCsv(Uri uri) {
        csvData.clear(); // clear previous data

        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Split CSV line by comma
                String[] columns = line.split(",");
                csvData.add(columns);
            }

            // For debugging: print CSV data to Logcat
            for (String[] row : csvData) {
                Log.d("CSV", String.join(", ", row));
            }

            Toast.makeText(this, "CSV loaded: " + csvData.size() + " rows", Toast.LENGTH_SHORT).show();

            // Now you can start processing csvData programmatically
            // Example: access first row: csvData.get(0)
            // Example: access first cell of first row: csvData.get(0)[0]

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV", Toast.LENGTH_SHORT).show();
        }
    }

    // Getter if you want to access CSV from other methods
    public List<String[]> getCsvData() {
        return csvData;
    }
}
