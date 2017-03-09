package com.ranglerz.tlc.tlc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Invoice.Invoice;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Message.Notification;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.ReportAccident.ReportAccidentForm;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.TlcSummons;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCCarPurchaseRentSale.CarPurchaseRentSale;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument.TlcDocument;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.InsuranceCustomAdapter;

import java.util.ArrayList;

public class SelectOptionNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView SelectOptionListView;
    public ProgressDialog dialog;
    SharedPreferences sharedPreferencesEmail, sharedPreferencesEnglish , sharedPreferencesSpanish ,
            sharedPreferencesArabic , sharedPreferencesUrdu;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SelectOptionListView = (ListView)findViewById(R.id.selectoptionlistview);

        String insurance = getResources().getString(R.string.insuranceheading);
        String summon = getResources().getString(R.string.summonheading);
        String applicationAndClasses = getResources().getString(R.string.applicationclassheading);
        String buyrent = getResources().getString(R.string.buyrentcarheading);
        String reportAccident = getResources().getString(R.string.reportaccidentheading);
        String Message = getResources().getString(R.string.messageheading);


        ArrayList<ImageAndText> list = new ArrayList();
        list.add(new ImageAndText(insurance,R.drawable.insurancemain));
        list.add(new ImageAndText(summon , R.drawable.summons));
        list.add(new ImageAndText(applicationAndClasses , R.drawable.documentmain));
        list.add(new ImageAndText(buyrent ,R.drawable.carpurchasemain ));
        list.add(new ImageAndText(reportAccident,R.drawable.reportaccident));
        list.add(new ImageAndText("invoices" ,R.drawable.invoice));
        list.add(new ImageAndText(Message,R.drawable.message));

        SelectOptionListView.setAdapter(new InsuranceCustomAdapter(SelectOptionNavigation.this, list));
        SelectOptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent =new Intent(SelectOptionNavigation.this, com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.Insurance.class);
                        startActivity(intent);

                        break;
                    case 1:

                        Intent intent1 =new Intent(SelectOptionNavigation.this, TlcSummons.class);
                        startActivity(intent1);

                        break;
                    case 2:

                        Intent intent2 =new Intent(SelectOptionNavigation.this, TlcDocument.class);
                        startActivity(intent2);

                        break;

                    case 3:
                        Intent intent4 =new Intent(SelectOptionNavigation.this, CarPurchaseRentSale.class);
                        startActivity(intent4);

                        break;

                    case 4:

                        Intent intent5 =new Intent(SelectOptionNavigation.this, ReportAccidentForm.class);
                        startActivity(intent5);

                        break;

                    case 5:

                        Intent intent6 =new Intent(SelectOptionNavigation.this, Invoice.class);
                        startActivity(intent6);

                        break;
                    case 6:

                        Intent intent7 =new Intent(SelectOptionNavigation.this, Notification.class);
                        startActivity(intent7);

                        break;
                    default:

                        break;

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Click once again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }

            }, 2000);
        }
        else
        {
         super.onBackPressed();
        }

    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle SelectOptionNavigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.userhome)
        {

        }
        else if (id == R.id.editProfile)
        {
        Intent intent = new Intent(SelectOptionNavigation.this , EditProfile.class);
            startActivity(intent);
        }
        else if (id == R.id.account)
        {

        }
        else if(id == R.id.logout)
        {
//                    dialog = new ProgressDialog(SelectOptionNavigation.this);
//                    dialog.setTitle("Please Wait.... ");
//                    dialog.show();
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            dialog.dismiss();
//                        }
//                    }, 3000); // 3000 milliseconds delay

                    sharedPreferencesEmail = getSharedPreferences("email" , 1);
                    sharedPreferencesEnglish = getSharedPreferences("english" ,2);
                    sharedPreferencesSpanish = getSharedPreferences("spanish" , 3);
                    sharedPreferencesArabic = getSharedPreferences("arabic" , 4);
                    sharedPreferencesUrdu = getSharedPreferences("urdu" , 5);
                    SharedPreferences.Editor editorEmail = sharedPreferencesEmail.edit();
                    SharedPreferences.Editor editorEnglish = sharedPreferencesEnglish.edit();
                    SharedPreferences.Editor editorSpanish = sharedPreferencesSpanish.edit();
                    SharedPreferences.Editor editorArabic = sharedPreferencesArabic.edit();
                    SharedPreferences.Editor editorUrdu = sharedPreferencesUrdu.edit();
                    editorEmail.clear();
                    editorEnglish.clear();
                    editorSpanish.clear();
                    editorArabic.clear();
                    editorUrdu.clear();
                    editorEmail.commit();
                    editorEnglish.commit();
                    editorSpanish.commit();
                    editorArabic.commit();
                    editorUrdu.commit();


                    Intent intent = new  Intent(SelectOptionNavigation.this , SelectLanguage.class);
                    startActivity(intent);
                    this.finish();

                    Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
