package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.RegistrationForm;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.CurrentDriverList;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.addDriverText;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.AddDriverCustomAdapter;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.SummonScheduleCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ScheduleSummon extends AppCompatActivity {
    SharedPreferences sharedPreferences , sharedPreferencesSummonType , sharedPreferencesTableid;
    String userID = "userID";
    String SummonTypeSP = "summontype";
    String SummonTableid = "tableid";
    JSONObject jsonObject;
    JSONArray jsonArray;
    String tag_id = "summon_id" ;
    String tag_user_id = "user_id" ;
    String tag_hearing_location = "hearing_location" ;
    String tag_hearing_date = "hearing_date" ;
    String tag_contact_number = "contact_number" ;
    String tag_attorney_rep = "attorney_rep" ;
    String tag_remarks = "remarks" ;
    String tag_compliance = "compliance" ;
    String tag_summon_type = "summon_type" ;
    String tag_schedule_status = "schedule_status" ;
    ArrayList<ScheduleText> list;
    ListView listView;
    String SummonTableID;
    String SummonType;
    String Response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_summon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        new getSchedule().execute();

        listView = (ListView)findViewById(R.id.summonschedule);

         SummonTableID = getIntent().getExtras().getString("SummonTableID");
         SummonType = getIntent().getExtras().getString("SummonType");
         View empty = findViewById(R.id.datanotfound);
         listView.setEmptyView(empty);

        Log.d("Schedule Summon" , "SummonTableID "+SummonTableID);
        Log.d("Schedule Summon " , "SummonType "+SummonType);


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
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    protected void createNetErrorDialog() {

        if (isNetworkAvailable()==false){


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                    .setTitle("Unable to connect")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivity(i);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ScheduleSummon.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            //remainging
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createNetErrorDialog();
    }
    public class getSchedule extends AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(ScheduleSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/get_schedule_data.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                sharedPreferences = getSharedPreferences("getuserid" , 2);
//                sharedPreferencesSummonType = getSharedPreferences("summontype", 1);
//                sharedPreferencesTableid = getSharedPreferences("summontableid", 2);
                String user = sharedPreferences.getString(userID , null);
//                String SummonType = sharedPreferencesSummonType.getString(SummonTypeSP , null);
//                String SummonTableID = sharedPreferencesTableid.getString(SummonTableid , null);

                Log.d("user " , "user" +user);
//                Log.d("user " , "SummonType" +SummonType);
//                Log.d("user " , "SummonTableID" +SummonTableID);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("summon_type", SummonType)
                        .appendQueryParameter("summontableid", SummonTableID)

                        ;
                String query = builder.build().getEncodedQuery().toString();

                // Open connection for sending data
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                connection.connect();

                int response_code = connection.getResponseCode();

                Log.d("tag", "doInBackground: code is  "+response_code);
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {


                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.d("tag", "doInBackground: Response is "+result.toString());
                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {
                jsonObject = new JSONObject(result);
                Response = jsonObject.getString("response");

                Log.d("sdfjad" ,"abc 1 " +Response);



            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(result == null)
            {
                Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_SHORT).show();

            }

            else if(Response.equalsIgnoreCase("true"))
            {

                try {

                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("schedule_data");
                    list = new ArrayList();


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject finalobject = jsonArray.getJSONObject(i);
                        tag_user_id = finalobject.getString("user_id");
                        tag_id = finalobject.getString("schedule_id");
                        tag_hearing_location = finalobject.getString("hearing_location");
                        tag_hearing_date = finalobject.getString("hearing_date");
                        tag_contact_number = finalobject.getString("contact_number");
                        tag_attorney_rep = finalobject.getString("attorney_rep");
                        tag_remarks = finalobject.getString("remarks");
                        tag_compliance = finalobject.getString("compliance");
                        tag_summon_type = finalobject.getString("summon_type");
                        tag_schedule_status = finalobject.getString("schedule_status");


                        list.add(new ScheduleText(tag_id , tag_hearing_location , tag_hearing_date , tag_contact_number , tag_attorney_rep , tag_remarks , tag_compliance ));


                    }

                    listView.setAdapter(new SummonScheduleCustomAdapter(ScheduleSummon.this , list));

                    dailog.dismiss();



                    //Log.d("doinback", "plate " + tag_plate + " VIN " + tag_vin + "policy " + tag_policy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                Toast.makeText(getApplicationContext(),"Data Fetch Successfully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();
            }else if (Response.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(ScheduleSummon.this, "Data Not Found", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(ScheduleSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

}
