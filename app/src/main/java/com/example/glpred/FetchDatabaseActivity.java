package com.example.glpred;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.*;

public class FetchDatabaseActivity extends AppCompatActivity{

    private final OkHttpClient client = new OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .build();

    EditText eText_emailInput;
    EditText eText_passwordInput;
    Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_database);

        eText_emailInput = findViewById(R.id.editText_emailAddress);
        eText_passwordInput = findViewById(R.id.editText_password);
        downloadButton = findViewById(R.id.button_FetchDatabaseActivity);
    }
    public void loginAndDownload(View view) {
        String email = eText_emailInput.getText().toString();
        String password = eText_passwordInput.getText().toString();
        loginAndDownloadInternal(email, password);
    }

    private void loginAndDownloadInternal(String email, String password) {

        new Thread(() -> {
            try {
                // 1. LOGIN REQUEST
                String loginUrl = "https://www2.libreview.com/Account/Login";

                RequestBody formBody = new FormBody.Builder()
                        .add("Email", email)
                        .add("Password", password)
                        .build();

                Request loginRequest = new Request.Builder()
                        .url(loginUrl)
                        .post(formBody)
                        .build();

                Response loginResponse = client.newCall(loginRequest).execute();

                if (!loginResponse.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(FetchDatabaseActivity.this, "Login failed", Toast.LENGTH_LONG).show()
                    );
                    return;
                }

                // Extract session cookies
                StringBuilder cookies = new StringBuilder();
                for (String cookie : loginResponse.headers("Set-Cookie")) {
                    cookies.append(cookie).append("; ");
                }

                // 2. DOWNLOAD CSV USING THE SESSION COOKIE
                String csvUrl = "https://www2.libreview.com/api/report/export?format=csv";

                Request csvRequest = new Request.Builder()
                        .url(csvUrl)
                        .get()
                        .addHeader("Cookie", cookies.toString())
                        .build();

                Response csvResponse = client.newCall(csvRequest).execute();

                if (!csvResponse.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(FetchDatabaseActivity.this, "CSV download failed", Toast.LENGTH_LONG).show()
                    );
                    return;
                }


                ResponseBody responseBody = csvResponse.body();

                if (responseBody == null) {
                    runOnUiThread(() ->
                            Toast.makeText(
                                    FetchDatabaseActivity.this,
                                    "CSV response body is empty",
                                    Toast.LENGTH_LONG
                            ).show()
                    );
                    return;
                }

                assert csvResponse.body() != null;
                byte[] csvBytes = csvResponse.body().bytes();

                // 3. SAVE CSV FILE
                File file = new File(
                        getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                        "libreview_data.csv"
                );

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(csvBytes);
                fos.close();

                runOnUiThread(() ->
                        Toast.makeText(FetchDatabaseActivity.this,
                                "CSV saved to: " + file.getAbsolutePath(),
                                Toast.LENGTH_LONG).show()
                );

            } catch (IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(FetchDatabaseActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }

}
