package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons;

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

public class TlcSummons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlc_summons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ListView tlcsumoons = (ListView)findViewById(R.id.tlcsummons);
        ArrayList<ImageAndText> list = new ArrayList();

        String summon = getResources().getString(R.string.newsummon);
        String schedule = getResources().getString(R.string.schedulesummonheading);
        String monthly = getResources().getString(R.string.monthlymonitoringheading);
        String file = getResources().getString(R.string.inquestmotionappealheading);

        list.add(new ImageAndText(summon ,R.drawable.newsummons));
        list.add(new ImageAndText(schedule , R.drawable.summonupdate));
        list.add(new ImageAndText(monthly ,R.drawable.monitoringupdate));
        list.add(new ImageAndText(file  ,R.drawable.fileappeal));


        tlcsumoons.setAdapter(new InsuranceCustomAdapter(TlcSummons.this, list));
        tlcsumoons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(TlcSummons.this, NewSummon.class);
                        startActivity(intent);

                        break;
                    case 1:

                        Intent intent3 = new Intent(TlcSummons.this, ListOfSummons.class);
                        startActivity(intent3);


                        break;
                    case 2:

                        Intent intent1 = new Intent(TlcSummons.this, MonthlyMonitoring.class);
                        startActivity(intent1);

                        break;
                    case 3:

                        Intent intent2 = new Intent(TlcSummons.this, FileApeal.class);
                        startActivity(intent2);

                        break;
                     default:
                         Log.d("TlcSummons","Invalid Position");
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
