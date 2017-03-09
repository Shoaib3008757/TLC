package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.ReportAccident;

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

public class ReportAccident extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_accident);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String reportAccident = getResources().getString(R.string.reportaccidentheading);

        ListView reportaccidentlsit = (ListView)findViewById(R.id.reportaccidentlsit);

        ArrayList<ImageAndText> list = new ArrayList();
        list.add(new ImageAndText(reportAccident , R.drawable.reportaccidentabc));
        reportaccidentlsit.setAdapter(new InsuranceCustomAdapter(ReportAccident.this, list));

        reportaccidentlsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(ReportAccident.this, ReportAccidentForm.class);
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
