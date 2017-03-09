package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCCarPurchaseRentSale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranglerz.tlc.tlc.ImageAndText;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.InsuranceCustomAdapter;

import java.util.ArrayList;

public class TlcCarPurchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcl_car_purchase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String buyrent = getResources().getString(R.string.buyrentcarheading);

        ListView carpurchaselist = (ListView)findViewById(R.id.carpurchaselist);

        ArrayList<ImageAndText> list = new ArrayList();
        list.add(new ImageAndText(buyrent,R.drawable.carpurchasemain));
        carpurchaselist.setAdapter(new InsuranceCustomAdapter(TlcCarPurchase.this, list));

        carpurchaselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(TlcCarPurchase.this, CarPurchaseRentSale.class);
                        startActivity(intent);

                        break;
                    case 1:


                        break;
                    case 2:


                        break;
                    case 3:


                        break;
                    default:
                        Log.d("TlcSummons", "Invalid Position");
                        break;

                }
            }
        });

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
