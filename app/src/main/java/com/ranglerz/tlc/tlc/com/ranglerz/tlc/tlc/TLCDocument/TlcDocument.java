package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ranglerz.tlc.tlc.ImageAndText;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.InsuranceCustomAdapter;

import java.util.ArrayList;

public class TlcDocument extends AppCompatActivity {

    Button IndividualsCorporation , CorporationApplicant , PartnershipApplicant , LLCApplicant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlc_document);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        IndividualsCorporation = (Button)findViewById(R.id.individualspartnership);
//        CorporationApplicant = (Button)findViewById(R.id.corporationapplicant);
//        PartnershipApplicant = (Button)findViewById(R.id.partnershipapplicant);
//        LLCApplicant = (Button)findViewById(R.id.llcapplicant);
//
//
//        IndividualsCorporation.setOnClickListener(new View.OnClickListener() {
//                @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(TlcDocument.this , IndividualsCorporationPartnership.class);
//                startActivity(intent);
//
//            }
//        });
//
//        CorporationApplicant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.CorporationApplicant.class);
//                startActivity(intent);
//
//            }
//        });
//
//        PartnershipApplicant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.PartnershipApplicant.class);
//                startActivity(intent);
//
//            }
//        });
//
//        LLCApplicant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.LLCApplicant.class);
//                startActivity(intent);
//
//            }
//        });


        ListView tcldocumentlist = (ListView)findViewById(R.id.tcldocument);


        String individual = getResources().getString(R.string.individual);
        String corporation = getResources().getString(R.string.corporation);
        String partnership = getResources().getString(R.string.partnership);
        String llc = getResources().getString(R.string.llc);

        ArrayList<ImageAndText> list = new ArrayList();

        list.add(new ImageAndText(individual, R.drawable.individualcorparationandpartnership));
        list.add(new ImageAndText(corporation, R.drawable.corporationapplicent));
        list.add(new ImageAndText(partnership, R.drawable.partnershipapplicent));
        list.add(new ImageAndText(llc , R.drawable.llcapplicent));

        tcldocumentlist.setAdapter(new InsuranceCustomAdapter(TlcDocument.this, list));
        tcldocumentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(TlcDocument.this , IndividualsCorporationPartnership.class);
                        startActivity(intent);

                        break;
                    case 1:

                        Intent intent1 = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.CorporationApplicant.class);
                        startActivity(intent1);

                        break;
                    case 2:
                        Intent intent2 = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.PartnershipApplicant.class);
                        startActivity(intent2);

                        break;
                    case 3:
                        Intent intent3 = new Intent(TlcDocument.this , com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.LLCApplicant.class);
                        startActivity(intent3);
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
