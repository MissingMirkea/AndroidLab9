package com.example.laborator9;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar simpleProgressBar;
    private ProgressBar progressBard;
    private Handler progressHandler;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inițializăm componentele UI
        simpleProgressBar = findViewById(R.id.progressBar2);
        progressBard = findViewById(R.id.progressBar3);
        Button startButton = findViewById(R.id.startButton);
        Button secondButton = findViewById(R.id.button2); // Al doilea buton

        // Creăm un Handler pentru a actualiza UI-ul dintr-un thread de fundal
        progressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = msg.what;
                simpleProgressBar.setProgress(progress);
                progressBard.setProgress(progress);
            }
        };

        // Setăm click listener pentru butonul de start
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vizibilitatea progress bar-urilor
                simpleProgressBar.setVisibility(View.VISIBLE);
                simpleProgressBar.setProgress(0); // Resetăm progresul
                progressBard.setVisibility(View.VISIBLE);
                progressBard.setProgress(0); // Resetăm progresul

                // Creăm un thread care va actualiza progresul pentru ambele ProgressBar-uri
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            try {
                                // Simulăm progresul
                                Thread.sleep(50);

                                // Trimitem progresul prin Handler pentru actualizarea UI-ului
                                progressHandler.sendMessage(progressHandler.obtainMessage(i));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // După ce progresul este complet, ascundem ProgressBar-urile
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                simpleProgressBar.setVisibility(View.GONE);
                                progressBard.setVisibility(View.GONE);
                            }
                        });
                    }
                }).start();
            }
        });

        // Setăm click listener pentru al doilea buton (pentru ProgressDialog)
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creăm și arătăm ProgressDialog
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setTitle("Loading...");
                progressDialog.setCancelable(false); // Nu se poate închide prin apăsarea afară
                progressDialog.show();

                // Simulăm o acțiune care durează 5 secunde (pentru a arăta ProgressDialog-ul)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Simulăm un proces care durează 5 secunde
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // După ce procesul s-a încheiat, ascundem ProgressDialog
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
