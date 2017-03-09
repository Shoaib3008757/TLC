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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;

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

public class ListOfSummons extends AppCompatActivity {
//  Button ScheduleSummon , Result;
    SharedPreferences sharedPreferences , sharedPreferencesSummonType , sharedPreferencesTableid;
    String userID = "userID";
    String SummonTypeSP = "summontype";
    String SummonTableid = "tableid";

    JSONObject jsonObject;
    JSONArray driver , Base , SHL , FHV , Medallion , Corporation;
    String tag_id = "id" ;
    String tag_summonno = "summonno" ;
    String tag_summondate = "summondate" ;
    String tag_hearingLocation = "hearingLocation" ;
    String tag_tlclicensesummon = "tlclicensesummon" ;
    String tag_platemedallion = "platemedallion" ;
    String tag_summontype = "summontype" ;
    String tag_summontableid = "summontableid" ;
    ArrayList<ScheduleResultText> list;
    ListView scheduleResultListView;
    String SummonNumber;
    String SummonTableID;
    String SummonType;
    Button ScheduleSummonResultBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_summons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        new getSummons().execute();
        scheduleResultListView = (ListView) findViewById(R.id.scheduleandresultlist);
        View empty = findViewById(R.id.datanotfound);
        scheduleResultListView.setEmptyView(empty);

//        ScheduleSummon = (Button) findViewById(R.id.schedulesummon);
//        Result = (Button) findViewById(R.id.resuult);
//
//        ScheduleSummon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListOfSummons.this , ScheduleSummon.class);
//                startActivity(intent);
//            }
//        });
//
//        Result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListOfSummons.this , Result.class);
//                startActivity(intent);
//            }
//        });


        scheduleResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                SummonTableID = ((TextView) view.findViewById(R.id.summontableidlist)).getText().toString();
                SummonType = ((TextView) view.findViewById(R.id.summontypelist)).getText().toString();

                //Toast.makeText(ListOfSummons.this, "table "+SummonTableID + "type " +SummonType, Toast.LENGTH_SHORT).show();


//                sharedPreferencesSummonType = getSharedPreferences("summontype", 1);
//                sharedPreferencesTableid = getSharedPreferences("summontableid", 2);
//                SharedPreferences.Editor editorSummonType = sharedPreferencesSummonType.edit();
//                SharedPreferences.Editor editorSummonTableId = sharedPreferencesTableid.edit();
//                editorSummonType.putString(SummonTypeSP , SummonType);
//                editorSummonTableId.putString(SummonTableid , SummonTableID);
//                editorSummonType.apply();
//                editorSummonTableId.apply();


               // Toast.makeText(ListOfSummons.this, "id "+SummonTableID +"type"+SummonType, Toast.LENGTH_SHORT).show();
//                ScheduleSummonResultBtn = ((Button) view.findViewById(R.id.schedulesummonresultbutton));
//                ScheduleSummonResultBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
                       // new getSchedule().execute();
//                    }
//                });

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
                                    ListOfSummons.this.finish();
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




    public class getSummons extends AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(ListOfSummons.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/get_summons_data.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)


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
                driver = jsonObject.getJSONArray("driversummon");
                Base = jsonObject.getJSONArray("basesummon");
                SHL = jsonObject.getJSONArray("shlsummon");
                FHV = jsonObject.getJSONArray("fhvsummon");
                Medallion = jsonObject.getJSONArray("medallion_yellow_cab_summon");
                Corporation = jsonObject.getJSONArray("corporation_summon");

                list = new ArrayList();


                for (int i = 0; i < driver.length(); i++) {

                    JSONObject Driverobject = driver.getJSONObject(i);
                    tag_id = Driverobject.getString("user_id");
                    tag_summonno = Driverobject.getString("summon_no");
                    tag_summondate = Driverobject.getString("date_of_summon");
                    tag_hearingLocation = Driverobject.getString("hearing_location");
                    tag_tlclicensesummon = Driverobject.getString("tlc_license");
                    tag_platemedallion = Driverobject.getString("plate_medallion");
                    tag_summontype = Driverobject.getString("summon_type");
                    tag_summontableid = Driverobject.getString("summon_id");

                    list.add(new ScheduleResultText( tag_summontype , tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                for (int i = 0; i < Base.length(); i++) {

                    JSONObject baseobject = Base.getJSONObject(i);
                    tag_id = baseobject.getString("user_id");
                    tag_summonno = baseobject.getString("summon_no");
                    tag_summondate = baseobject.getString("date_of_summon");
                    tag_hearingLocation = baseobject.getString("hearing_location");
                    tag_tlclicensesummon = baseobject.getString("tlc_license");
                    tag_platemedallion = baseobject.getString("plate_medallion");
                    tag_summontype = baseobject.getString("summon_type");
                    tag_summontableid = baseobject.getString("summon_id");



                    list.add(new ScheduleResultText( tag_summontype ,tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                for (int i = 0; i < SHL.length(); i++) {

                    JSONObject SHLobject = SHL.getJSONObject(i);
                    tag_id = SHLobject.getString("user_id");
                    //tag_id = finalobject.getString("drivername");
                    tag_summonno = SHLobject.getString("summon_no");
                    tag_summondate = SHLobject.getString("date_of_summon");
                    tag_hearingLocation = SHLobject.getString("hearing_location");
                    tag_tlclicensesummon = SHLobject.getString("tlc_license");
                    tag_platemedallion = SHLobject.getString("plate_medallion");
                    tag_summontype = SHLobject.getString("summon_type");
                    tag_summontableid = SHLobject.getString("summon_id");



                    list.add(new ScheduleResultText( tag_summontype , tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                for (int i = 0; i < FHV.length(); i++) {

                    JSONObject FHVobject = FHV.getJSONObject(i);
                    tag_id = FHVobject.getString("user_id");
                    //tag_id = finalobject.getString("drivername");
                    tag_summonno = FHVobject.getString("summon_no");
                    tag_summondate = FHVobject.getString("date_of_summon");
                    tag_hearingLocation = FHVobject.getString("hearing_location");
                    tag_tlclicensesummon = FHVobject.getString("tlc_license");
                    tag_platemedallion = FHVobject.getString("plate_medallion");
                    tag_summontype = FHVobject.getString("summon_type");
                    tag_summontableid = FHVobject.getString("summon_id");



                    list.add(new ScheduleResultText(  tag_summontype , tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                for (int i = 0; i < Medallion.length(); i++) {

                    JSONObject Medallionobject = Medallion.getJSONObject(i);
                    //tag_id = finalobject.getString("drivername");
                    tag_id = Medallionobject.getString("user_id");

                    tag_summonno = Medallionobject.getString("summon_no");
                    tag_summondate = Medallionobject.getString("date_of_summon");
                    tag_hearingLocation = Medallionobject.getString("hearing_location");
                    tag_tlclicensesummon = Medallionobject.getString("tlc_license");
                    tag_platemedallion = Medallionobject.getString("plate_medallion");
                    tag_summontype = Medallionobject.getString("summon_type");
                    tag_summontableid = Medallionobject.getString("summon_id");



                    list.add(new ScheduleResultText( tag_summontype ,tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                for (int i = 0; i < Corporation.length(); i++) {

                    JSONObject Corporationobject = Corporation.getJSONObject(i);
                    //tag_id = finalobject.getString("drivername");
                    tag_id = Corporationobject.getString("user_id");

                    tag_summonno = Corporationobject.getString("summon_no");
                    tag_summondate = Corporationobject.getString("date_of_summon");
                    tag_hearingLocation = Corporationobject.getString("hearing_location");
                    tag_tlclicensesummon = Corporationobject.getString("tlc_license");
                    tag_platemedallion = Corporationobject.getString("plate_medallion");
                    tag_summontype = Corporationobject.getString("summon_type");
                    tag_summontableid = Corporationobject.getString("summon_id");



                    list.add(new ScheduleResultText(  tag_summontype , tag_summonno , tag_summondate , tag_hearingLocation , tag_tlclicensesummon , tag_platemedallion , tag_summontableid));

                }

                scheduleResultListView.setAdapter(new ScheduleResultCustomAdapterInnerClass(ListOfSummons.this , list));

                dailog.dismiss();



                //Log.d("doinback", "plate " + tag_plate + " VIN " + tag_vin + "policy " + tag_policy);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(result == null)
            {
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_SHORT).show();
            }

            else if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();
            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(ListOfSummons.this, "Something wrong", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(ListOfSummons.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



    public class ScheduleResultCustomAdapterInnerClass extends BaseAdapter {

        private ArrayList<ScheduleResultText> mListItems;
        private LayoutInflater mLayoutInflater;
        Context context;

        public ScheduleResultCustomAdapterInnerClass (Context context, ArrayList<ScheduleResultText> arrayList){
            this.context = context;
            mListItems = arrayList;

            //get the layout inflater
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            //getCount() represents how many items are in the list
            return mListItems.size();
        }

        @Override
        //get the data of an item from a specific position
        //i represents the position of the item in the list
        public Object getItem(int i) {
            return null;
        }

        @Override
        //get the position id of the item from the list
        public long getItemId(int i) {
            return 0;
        }

        @Override

        public View getView(final int position, View view, ViewGroup viewGroup) {

            // create a ViewHolder reference
            final ViewHolder holder;

            //check to see if the reused view is null or not, if is not null then reuse it
            if (view == null) {
                holder = new ViewHolder();

                view = mLayoutInflater.inflate(R.layout.listofsummons, viewGroup, false);

                // get all views you need to handle from the cell and save them in the view holder
                holder.Summonno = (TextView) view.findViewById(R.id.summonlistlist);
                holder.SummonData = (TextView) view.findViewById(R.id.summondataatelist);
                holder.HearingLocation = (TextView) view.findViewById(R.id.hearinglocationsummonlist);
                holder.TlcLicense = (TextView) view.findViewById(R.id.tlclicencesummonlist);
                holder.PlataMedallion = (TextView) view.findViewById(R.id.platemedallionlist);
                holder.SummonType = (TextView) view.findViewById(R.id.summontypelist);
                holder.SummonTableId = (TextView) view.findViewById(R.id.summontableidlist);
                holder.SummonTableId.setVisibility(View.GONE);
                holder.ScheduleResultButtton = (Button) view.findViewById(R.id.schedulesummonresultbutton);
                holder.ScheduleResultButtton.setTag(position);

                holder.ScheduleResultButtton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ScheduleSummon.class);

                        SummonTableID = holder.SummonTableId.getText().toString();
                        SummonType = holder.SummonType.getText().toString();
                        intent.putExtra("SummonTableID" , SummonTableID);
                        intent.putExtra("SummonType" , SummonType);

                        v.getContext().startActivity(intent);
                    }
                });
                // save the view holder on the cell view to get it back latter
                view.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder)view.getTag();
            }

            //get the string item from the position "position" from array list to put it on the TextView
            ScheduleResultText stringItem = mListItems.get(position);
            if (stringItem != null) {
                //set the item name on the TextView
                holder.Summonno.setText(stringItem.getSummonNumber());
                holder.SummonData.setText(stringItem.getSummonData());
                holder.HearingLocation.setText(stringItem.getHearingLocation());
                holder.TlcLicense.setText(stringItem.getTlcLicense());
                holder.PlataMedallion.setText(stringItem.getPlateMedallion());
                holder.SummonType.setText(stringItem.getSummonType());
                holder.SummonTableId.setText(stringItem.getSummonTableId());
            } else {
                // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
                holder.Summonno.setText("Unknown");
                holder.SummonData.setText("Unknown");
                holder.HearingLocation.setText("Unknown");
                holder.TlcLicense.setText("Unknown");
                holder.PlataMedallion.setText("Unknown");
                holder.SummonType.setText("Unknown");
                holder.SummonTableId.setText("Unknown");
            }



            //this method must return the view corresponding to the data at the specified position.
            return view;

        }

        /**
         * Used to avoid calling "findViewById" every time the getView() method is called,
         * because this can impact to your application performance when your list is large
         */
        private class ViewHolder {

            protected TextView Summonno;
            protected TextView SummonData;
            protected TextView HearingLocation;
            protected TextView TlcLicense;
            protected TextView PlataMedallion;
            protected TextView SummonType;
            protected TextView SummonTableId;
            protected Button ScheduleResultButtton;

        }

    }

}
