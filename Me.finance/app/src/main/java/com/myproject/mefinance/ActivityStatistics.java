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
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ActivityStatistics extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Context context = this;
    private View coordinatorLayoutView;
    private LineChart chart;
    private ArrayList<Entry> dataListEntries;
    private ArrayList<String> dataListLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeToolbar();
        initializeComponents();
        initializeListeners();

        loadEntries();
        loadChart(dataListEntries);
    }

    float initializeGraph(){
        double money=0.0;
        SharedPreferences shf = getSharedPreferences("ingresoTotal", MODE_WORLD_READABLE);
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
        }
        return (float)money;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.title_activity_statistics));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
    }

    public void initializeComponents(){
        coordinatorLayoutView = findViewById(R.id.coordinatorLayout);
        chart = (LineChart) findViewById(R.id.chart);
    }

    public void initializeListeners(){

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

    public void loadEntries(){
        float money = initializeGraph();
        dataListEntries = new ArrayList<>();

        dataListEntries.add(new Entry(0f, 0));
        dataListEntries.add(new Entry(0f, 1));
        dataListEntries.add(new Entry(0f, 2));
        dataListEntries.add(new Entry(0f, 3));
        dataListEntries.add(new Entry(money, 4));
        dataListEntries.add(new Entry(0f, 5));

        dataListLabels = new ArrayList<>();

        dataListLabels.add("July");
        dataListLabels.add("August");
        dataListLabels.add("September");
        dataListLabels.add("October");
        dataListLabels.add("November");
        dataListLabels.add("December");

    }

    public void loadChart(ArrayList<Entry> entries){
        LineDataSet dataSet = new LineDataSet(entries, ("Dinero"));
        dataSet.disableDashedLine();
        dataSet.setValueTextColor(getResources().getColor(R.color.colorPrimaryText));
        dataSet.setValueTextSize(16f);
        dataSet.setColor(getResources().getColor(R.color.colorAccent));
        //dataSet.setDrawFilled(true);
        dataSet.setLineWidth(2f);
        //dataSet.setDrawCubic(true);
        //dataSet.setFillColor(getResources().getColor(R.color.Green));
        //dataSet.setFillAlpha(65);

        LineData data = new LineData(dataListLabels, dataSet);

        chart.setData(data);

        chart.animateY(2500);
        chart.setDescription("Table of the month");
        chart.getXAxis().setTextColor(getResources().getColor(R.color.colorPrimaryText));
        chart.getAxisLeft().setTextColor(getResources().getColor(R.color.colorPrimaryText));
        chart.getAxisRight().setTextColor(getResources().getColor(R.color.colorPrimaryText));
        chart.getLegend().setTextColor(getResources().getColor(R.color.colorPrimaryText));
        chart.setVisibleXRangeMaximum(5);

        showMessage("Listo Cargado...");
    }
}
