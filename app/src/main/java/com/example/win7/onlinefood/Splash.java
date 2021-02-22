package com.example.win7.onlinefood;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    Handler mWaitHandler = new Handler();
    ImageView imageViewlogo;
    AlertDialog.Builder builder;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewlogo = (ImageView) findViewById(R.id.imageViewlogo);
        builder = new AlertDialog.Builder(this);

        if (!DetectConnection.checkInternetConnection(this)) {

            builder.setMessage("Do you want to close this application ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                        }
                    })
                    .setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            //dialog.cancel();
                            //Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                            //Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("No Internet Connection");
            alert.show();
        } else {

            mWaitHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(getApplicationContext(), A.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }, 3000);  // Give a 5 seconds delay.
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaitHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        imageViewlogo = (ImageView) findViewById(R.id.imageViewlogo);
        builder = new AlertDialog.Builder(this);

        if (!DetectConnection.checkInternetConnection(this)) {

            builder.setMessage("Do you want to close this application ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                        }
                    })
                    .setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            //dialog.cancel();
                            //Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                            //Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("No Internet Connection");
            alert.show();
        } else {

            mWaitHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(getApplicationContext(), A.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }, 3000);  // Give a 5 seconds delay.
        }
    }
}
