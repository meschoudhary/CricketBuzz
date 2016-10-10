package com.cricketbuzz.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cricketbuzz.R;
import com.cricketbuzz.Utils.ConnectionDetect;
import com.cricketbuzz.Utils.SplashProgress;
import com.cricketbuzzsdk.Listeners.SplashListener;
import com.cricketbuzzsdk.SplashData;

public class LoadingActivity extends AppCompatActivity implements SplashListener {

    private SplashProgress progress;

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.activity_loading);

        ConnectionDetect connect = new ConnectionDetect(getApplicationContext());

        if (connect.isConnectingToInternet()) {

            progress = new SplashProgress(LoadingActivity.this);
            progress.setCancelable(false);
            progress.show();

            // Get Splash Data

            SplashData load = new SplashData(this);
            load.getsplashdata(getApplicationContext());

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    LoadingActivity.this);
            builder.setMessage("This App Requires Internet Connection. Please Connect to Working Internet Connection!");
            builder.setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    dialog.dismiss();
                                    startActivityForResult(
                                            new Intent(
                                                    android.provider.Settings.ACTION_SETTINGS),
                                            0);
                                    LoadingActivity.this.finish();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    LoadingActivity.this.finish();
                                }
                            });

            AlertDialog alertdialog = builder.create();
            alertdialog.show();

        }
    }

    @Override
    public void GetSplashData(Boolean data) {

        progress.dismiss();

        if (data) {

            Intent i = new Intent(LoadingActivity.this,
                    MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        } else {

            Toast.makeText(getApplicationContext(),
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();

        }
    }
}
