package com.ranglerz.tlc.tlc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

public class SelectOption extends AppCompatActivity {
    ImageView insurance,tlcdocument,tlcsummons,tlccarrental,tlccarpurchase,reportaccident,radioyellowmedallion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


//         insurance =(ImageView) findViewById(R.id.insuranceimageview);
//         tlcdocument = (ImageView)findViewById(R.id.documentimageview);
//         tlcsummons = (ImageView)findViewById(R.id.carsummonsimageview);
//         tlccarrental = (ImageView)findViewById(R.id.carrentalimageview);
//         tlccarpurchase = (ImageView)findViewById(R.id.carpurchaseimageview);
//         reportaccident = (ImageView)findViewById(R.id.reportaccidentimageview);
//        radioyellowmedallion = (ImageView)findViewById(R.id.finetab);
//
//        insurance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, Insurance.class);
//                startActivity(intent);
//            }
//        });
//
//        tlcdocument.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, TlcDocument.class);
//                startActivity(intent);
//            }
//        });
//        tlcsummons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, TlcSummons.class);
//                startActivity(intent);
//            }
//        });
//        tlccarrental.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, TlcCarRental.class);
//                startActivity(intent);
//            }
//        });
//        tlccarpurchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, TlcCarPurchase.class);
//                startActivity(intent);
//            }
//        });
//        reportaccident.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, ReportAccident.class);
//                startActivity(intent);
//            }
//        });
//        radioyellowmedallion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("tag","here");
//                Intent intent =new Intent(SelectOption.this, Notification.class);
//                startActivity(intent);
//            }
//        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
