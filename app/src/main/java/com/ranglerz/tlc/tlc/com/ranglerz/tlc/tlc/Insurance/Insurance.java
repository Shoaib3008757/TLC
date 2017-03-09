package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranglerz.tlc.tlc.ImageAndText;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.InsuranceCustomAdapter;

import java.util.ArrayList;

public class Insurance extends AppCompatActivity {


    ListView insurancelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


         insurancelist = (ListView)findViewById(R.id.insuranccelist);

        String tlcinsurance = getResources().getString(R.string.tlcinsuranceheading);
        String personalinsurance = getResources().getString(R.string.personalInsurance);
        String fleetmanagement = getResources().getString(R.string.fleetmanagmentheading);
        String reportaccident = getResources().getString(R.string.reportaccidentheading);

        ArrayList<ImageAndText> list = new ArrayList();
        list.add(new ImageAndText(tlcinsurance ,R.drawable.insurance));
        list.add(new ImageAndText(personalinsurance , R.drawable.personalinsurance));
        //list.add(new ImageAndText("Fast Quote" , R.drawable.fastquote));
        //list.add(new ImageAndText("MVR Only" ,R.drawable.mvr ));
        list.add(new ImageAndText(fleetmanagement , R.drawable.updateandreplace));
        list.add(new ImageAndText(reportaccident ,R.drawable.reportaccidentabc));

        insurancelist.setAdapter(new InsuranceCustomAdapter(Insurance.this, list));
        insurancelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(Insurance.this, TlcInsurance.class);
                        startActivity(intent);

                        break;
                    case 1:

                        Intent intent3 = new Intent(Insurance.this, PersonalInsurance.class);
                        startActivity(intent3);

                        break;
//                    case 2:
//
//                        Intent intent2 = new Intent(Insurance.this, FastQuote.class);
//                        startActivity(intent2);
//
//                        break;
//                    case 3:
//
//                        Intent intent1 = new Intent(Insurance.this, MVR.class);
//                        startActivity(intent1);
//
//
//                        break;
                    case 2:

                        Intent intent4 = new Intent(Insurance.this, FleetManagement.class);
                        startActivity(intent4);

                        break;

                    case 3:

                        Intent intent5 = new Intent(Insurance.this, com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident.class);
                        startActivity(intent5);

                        break;
                    default:

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
