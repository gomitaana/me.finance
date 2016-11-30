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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityForm extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Context context = this;
    private View coordinatorLayoutView;
    private FloatingActionButton fab;
    private String type;
    private LinearLayout layoutIncome, layoutBill, layoutPayment;
    private EditText incomeAmount, incomeName, incomeDescription;
    private EditText billPaidDate, billDueDate, billName, billDescription, billAmount;
    private Switch billIsPaid;
    private EditText paymentPaidDate, paymentName, paymentDescription, paymentAmount;

    SharedPreferences sharedpreferences;
    public static String name = "nameKey";
    public static String description = "phoneKey";
    public static String monto = "monto";
    public static String fechaPago = "fechaPago";
    public static String limite = "fechaLimite";
    public static String pagado = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null){
            type = intent.getStringExtra("TYPE");
        }
        initializeToolbar();
        initializeComponents();
        initializeListeners();
        loadData();
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
            actionBar.setTitle(getResources().getString(R.string.title_activity_form));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
    }

    public void initializeComponents(){
        coordinatorLayoutView = findViewById(R.id.coordinatorLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        layoutIncome  = (LinearLayout) findViewById(R.id.layout_income);
        layoutBill    = (LinearLayout) findViewById(R.id.layout_bill);
        layoutPayment = (LinearLayout) findViewById(R.id.layout_payment);

        incomeAmount      = (EditText) findViewById(R.id.income_amount);
        incomeName        = (EditText) findViewById(R.id.income_name);
        incomeDescription = (EditText) findViewById(R.id.income_description);

        billPaidDate    = (EditText) findViewById(R.id.bill_paid_date);
        billDueDate     = (EditText) findViewById(R.id.bill_due_date);
        billName        = (EditText) findViewById(R.id.bill_name);
        billDescription = (EditText) findViewById(R.id.bill_description);
        billAmount      = (EditText) findViewById(R.id.bill_amount);
        billIsPaid      = (Switch) findViewById(R.id.bill_is_paid);

        paymentPaidDate    = (EditText) findViewById(R.id.payment_paid_date);
        paymentName        = (EditText) findViewById(R.id.payment_name);
        paymentDescription = (EditText) findViewById(R.id.payment_description);
        paymentAmount      = (EditText) findViewById(R.id.payment_amount);

    }

    public void loadData(){
        if(type.equals("income")){
            layoutIncome.setVisibility(View.VISIBLE);
            layoutBill.setVisibility(View.GONE);
            layoutPayment.setVisibility(View.GONE);
        }else if(type.equals("bill")){
            layoutBill.setVisibility(View.VISIBLE);
            layoutIncome.setVisibility(View.GONE);
            layoutPayment.setVisibility(View.GONE);
        }else if(type.equals("payment")){
            layoutPayment.setVisibility(View.VISIBLE);
            layoutBill.setVisibility(View.GONE);
            layoutIncome.setVisibility(View.GONE);

        }
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

    public void initializeListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double money = 0.0;

                if(type.equals("income")){
                    //showMessage("Income...");
                    monto=incomeAmount.getText().toString();
                    name=incomeName.getText().toString();
                    description=incomeDescription.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("monto",monto);
                        obj.put("name",name);
                        obj.put("description",description);
                        String stg = obj.toString();

                        SharedPreferences shf = getSharedPreferences("ingresoTotal", MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = shf.edit();
                        String strPref = shf.getString("ingresoTotal", null);
                        if(strPref != null) {
                            money = Double.parseDouble(strPref) + Double.parseDouble(monto);
                            editor.putString("ingresoTotal", String.valueOf(money));
                            editor.commit();
                            incomeAmount.setText("");
                            incomeName.setText("");
                            incomeDescription.setText("");
                            showMessage("Ingreso agregado");
                        }else{
                            editor.putString("ingresoTotal", monto);
                            editor.commit();
                            incomeAmount.setText("");
                            incomeName.setText("");
                            incomeDescription.setText("");
                            showMessage("Ingreso agregado");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else if(type.equals("bill")){
                    //showMessage("Bill...");
                    fechaPago=billPaidDate.getText().toString();
                    limite=billDueDate.getText().toString();
                    name=billName.getText().toString();
                    description=billDescription.getText().toString();
                    monto=billAmount.getText().toString();
                    pagado=String.valueOf(billIsPaid.isChecked());

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("monto",monto);
                        obj.put("name",name);
                        obj.put("description",description);
                        obj.put("fechaPago",fechaPago);
                        obj.put("limite",limite);
                        obj.put("pagado",pagado);

                        SharedPreferences shf = getSharedPreferences("pagoTotal", MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = shf.edit();
                        String strPref = shf.getString("pagoTotal", null);
                        if(strPref != null) {
                            money = Double.parseDouble(strPref) + Double.parseDouble(monto);
                            editor.putString("pagoTotal", String.valueOf(money));
                            editor.commit();
                            billPaidDate.setText("");
                            billDueDate.setText("");
                            billName.setText("");
                            billDescription.setText("");
                            billAmount.setText("");
                            showMessage("Pago agregado");
                        }else{
                            editor.putString("pagoTotal", monto);
                            editor.commit();
                            billPaidDate.setText("");
                            billDueDate.setText("");
                            billName.setText("");
                            billDescription.setText("");
                            billAmount.setText("");
                            showMessage("Pago agregado");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(type.equals("payment")){
                    //showMessage("Payment...");
                    fechaPago=paymentPaidDate.getText().toString();
                    name=paymentName.getText().toString();
                    description=paymentDescription.getText().toString();
                    monto=paymentAmount.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("monto",monto);
                        obj.put("name",name);
                        obj.put("description",description);
                        obj.put("fechaPago",fechaPago);
                        String stg = obj.toString();

                        SharedPreferences shf = getSharedPreferences("gastoTotal", MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = shf.edit();

                        String strPref = shf.getString("gastoTotal", null);
                        if(strPref != null) {
                            money = Double.parseDouble(strPref) + Double.parseDouble(monto);
                            editor.putString("gastoTotal", String.valueOf(money));
                            editor.commit();
                            paymentPaidDate.setText("");
                            paymentName.setText("");
                            paymentDescription.setText("");
                            paymentAmount.setText("");
                            showMessage("Gasto agregado");
                        }else{
                            editor.putString("gastoTotal", monto);
                            editor.commit();
                            paymentPaidDate.setText("");
                            paymentName.setText("");
                            paymentDescription.setText("");
                            paymentAmount.setText("");
                            showMessage("Gasto agregado");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
