package com.myproject.mefinance;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ActivityMain extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Context context = this;
    private View coordinatorLayoutView;
    private FloatingActionButton fabData, fabStatistics, fabSettings;
    private TextView text1, text2, text3,textmes;
    private double money=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();
        initializeComponents();
        initializeListeners();

        /*SharedPreferences shf = getSharedPreferences("ingresoTotal", MODE_WORLD_READABLE);
        String strPref = shf.getString("ingresoTotal", null);
        if(strPref != null) {
            money += Double.parseDouble(strPref);
            shf = getSharedPreferences("pagoTotal", MODE_WORLD_READABLE);
            strPref = shf.getString("pagoTotal", null);
            if(strPref != null) {
                money -= Double.parseDouble(strPref);
            }
            shf = getSharedPreferences("gastoTotal", MODE_WORLD_READABLE);
            strPref = shf.getString("gastoTotal", null);
            if(strPref != null) {
                money -= Double.parseDouble(strPref);
            }
        }else{
            SharedPreferences.Editor editor = shf.edit();
            editor.putString("ingresoTotal", String.valueOf(money));
            editor.commit();
            editor.putString("pagoTotal", String.valueOf(money));
            editor.commit();
            editor.putString("gastoTotal", String.valueOf(money));
            editor.commit();
        }
        textmes.setText("$"+money);*/

    }

    @Override
    public void onResume(){
        super.onResume();
        money = 0.0;
        // put your code here...
        SharedPreferences shf = getSharedPreferences("ingresoTotal", MODE_WORLD_READABLE);
        String strPref = shf.getString("ingresoTotal", null);
        if(strPref != null) {
            money = money + Double.parseDouble(strPref);
            shf = getSharedPreferences("pagoTotal", MODE_WORLD_READABLE);
            strPref = shf.getString("pagoTotal", null);
            if(strPref != null) {
                money = money - Double.parseDouble(strPref);
            }
            shf = getSharedPreferences("gastoTotal", MODE_WORLD_READABLE);
            strPref = shf.getString("gastoTotal", null);
            if(strPref != null) {
                money = money - Double.parseDouble(strPref);
            }
        }
        textmes.setText("$"+money);
    }

    public void initializeToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.title_activity_main));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
    }

    public void initializeComponents(){
        coordinatorLayoutView = findViewById(R.id.coordinatorLayout);

        fabData       = (FloatingActionButton) findViewById(R.id.fab_data);
        fabStatistics = (FloatingActionButton) findViewById(R.id.fab_statistics);
        fabSettings   = (FloatingActionButton) findViewById(R.id.fab_settings);


        text1 = (TextView) findViewById(R.id.text_1);
        text2 = (TextView) findViewById(R.id.text_2);
        text3 = (TextView) findViewById(R.id.text_3);
        textmes = (TextView) findViewById(R.id.text_mes);

    }

    public void initializeListeners(){
        fabData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityData.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        fabStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatistics.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivitySettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void showMessage(String message){
        Snackbar
                .make(coordinatorLayoutView, message, Snackbar.LENGTH_LONG)
                //.setAction(R.string.snackbar_action_undo, clickListener)
                .show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
