package com.olio.v8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    Context context;
    BottleDispenser bd = BottleDispenser.getInstance();
    TextView bd_console, balance_display, seekBarProgress;
    SeekBar moneySeekBar;
    Spinner nameSpinner, sizeSpinner;
    Receipt lastPurchaseReceipt;
    int receiptnumber = 0;
    double progressDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        bd_console = (TextView) findViewById(R.id.Bd_console);
        balance_display = (TextView) findViewById(R.id.balance_display);
        seekBarProgress = (TextView) findViewById(R.id.seekBarProgress);
        balance_display.setText("0.00");
        //Initialize SeekBar for adding money in 10 cent increments
        moneySeekBar = (SeekBar) findViewById(R.id.seekBar);
        moneySeekBar.setMax(50);
        moneySeekBar.setProgress(0);
        moneySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressDouble = (double) progress/10;
                seekBarProgress.setText(String.valueOf(progressDouble));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ArrayList<String> BottleNames = new ArrayList<>();
        ArrayList<Double> BottleSizes = new ArrayList<>();
        //Get distinct bottle names and sizes for spinners
        for(Bottle b : bd.BottleList){
            if (!BottleNames.contains(b.getName())) {
                BottleNames.add(b.getName());
            }
            if (!BottleSizes.contains(b.getSize())){
                BottleSizes.add(b.getSize());
            }
        }
        //Initialize spinners for bottle name and size
        ArrayAdapter<String> namespinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BottleNames);
        ArrayAdapter<Double> sizespinner_adapter = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, BottleSizes);
        nameSpinner = (Spinner) findViewById(R.id.spinner);
        sizeSpinner = (Spinner) findViewById(R.id.spinner2);
        namespinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizespinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(namespinner_adapter);
        sizeSpinner.setAdapter(sizespinner_adapter);
    }

    //Add amount of money selected in the SeekBar to the dispenser
    public void addMoney(View v){
        balance_display.setText(String.format("%.2f", bd.addMoney(progressDouble)));
        bd_console.setText("Klink! Added more money!");
    }
    //Return money from the dispenser
    public void returnMoney(View v){
        String balance = balance_display.getText().toString();
        bd.returnMoney();
        balance_display.setText("0.00");
        bd_console.setText(String.format("Klink klink. Money came out! You got %s back.", balance));
    }

    //Buy a bottle from the dispenser if there is a bottle with selected name and size
    public void buyBottle(View v){
        boolean noBreak = true;
        String nameSelection = nameSpinner.getSelectedItem().toString();
        double sizeSelection = (double) sizeSpinner.getSelectedItem();
        Iterator<Bottle> it = bd.BottleList.iterator();
        while (it.hasNext()){
            Bottle b = it.next();
            if ((b.getName().equals(nameSelection)) && (b.getSize() == sizeSelection)){
                if (bd.getBalance() >= b.getPrice()) {
                    bd_console.setText("KACHUNK! " + b.getName() + "  came out of the dispenser!");
                    it.remove();
                    balance_display.setText(String.format("%.2f", bd.removeMoney(b.getPrice())));
                    lastPurchaseReceipt = new Receipt(b.getName(), b.getPrice());
                    noBreak = false;
                    break;
                } else{
                    bd_console.setText(String.format("Add more money! %s of size %.1f costs %.2f!", b.getName(), b.getSize(), b.getPrice()));
                    noBreak = false;
                }
            }
        }
        if (noBreak) {
            bd_console.setText("We don't have that bottle in that size, pick something else.");
        }
    }

    //Saves the receipt for the last purchase
    public void saveReceipt(View v){
        String filename = "receipt_" + receiptnumber + ".txt";
        try{
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            String receiptBody = String.format("************ RECEIPT ************\n%s\tQty: 1\t " +
                            "Price: %.2f\n*********************************",
                    lastPurchaseReceipt.getItem(), lastPurchaseReceipt.getPrice());
            osw.write(receiptBody);
            osw.close();
            bd_console.setText(String.format("Receipt saved to file '%s'.", filename));
            receiptnumber ++;
        } catch (IOException e){
            Log.e("IOException", "Faulty input.");
        }
    }
}