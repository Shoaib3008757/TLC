package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.RegistrationForm;
import com.ranglerz.tlc.tlc.ThankYouScreen;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.MonthlyMonitoring;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters.AddDriverCustomAdapter;

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

public class CurrentDriverList extends AppCompatActivity {

    String CurrentDriver = "userID";
    SharedPreferences sharedPreferences , CurrentDriversharedPreferences;
    String userID = "userID";
    JSONObject jsonObject;
    JSONArray jsonArray;
    String tag_id = "id" ;
    String tag_drivername = "drivername" ;
    String tag_driverlicense = "driverlicense" ;
    String tag_tlclicense = "tlclicense" ;
    String tag_platenumber = "platenumber" ;
    String tag_policynumber = "policynumber" ;
    String tag_base_name_corporation = "base_name_corporation" ;
    String Response;


    ArrayList<addDriverText> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_driver_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView) findViewById(R.id.currentdriverlist);
        View empty = findViewById(R.id.datanotfound);
        listView.setEmptyView(empty);
        createNetErrorDialog();
        new getDriverData().execute();

//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                list.remove(position);
//                listView.deferNotifyDataSetChanged();
//                return false;
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
                                    CurrentDriverList.this.finish();
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

    public class getDriverData extends AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CurrentDriverList.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/get_insurance_driver_data.php");
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
                Response = jsonObject.getString("response");

                Log.d("sdfjad" ,"abc 1 " +Response);



            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(Response.equalsIgnoreCase("true"))
            {

                try {

                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("array data");
                    list = new ArrayList();


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject finalobject = jsonArray.getJSONObject(i);
                        tag_id = finalobject.getString("id");
                        tag_drivername = finalobject.getString("drivername");
                        tag_driverlicense = finalobject.getString("driverlicense");
                        tag_tlclicense = finalobject.getString("tlclicense");
                        tag_platenumber = finalobject.getString("platenumber");
                        tag_policynumber = finalobject.getString("policynumber");
                        tag_base_name_corporation = finalobject.getString("base_name_corporation");


                        list.add(new addDriverText(tag_id , tag_drivername , tag_driverlicense , tag_tlclicense , tag_platenumber , tag_policynumber , tag_base_name_corporation));




                    }
                    listView.setAdapter(new AddDriverCustomAdapterInnerClass(CurrentDriverList.this , list));

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
                Toast.makeText(CurrentDriverList.this, "Data Not Found Please Add Driver First!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(CurrentDriverList.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public class deleteCurrentDriver extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CurrentDriverList.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/delete-insurance-driver.php");
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
                        .appendQueryParameter("currentdriverid" , strings[0])





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
            if (result == null)
            {
                dailog.dismiss();
                serverNotResponding();
                //Toast.makeText(getApplicationContext(), "Server not Responding", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Driver Delete Successfully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();

                Toast.makeText(CurrentDriverList.this, "Driver Not Deleted Please try again!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(CurrentDriverList.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    public void serverNotResponding()
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Server not Responding" );
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".

            }
        })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });

        final android.app.AlertDialog alert = dialog.create();
        alert.show();
    }
    public void dataSent()
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Data Sent Successfully" );
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(CurrentDriverList.this,ThankYouScreen.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });

        final android.app.AlertDialog alert = dialog.create();
        alert.show();
    }
    public void dataNotSent()
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Data Not Sent" );
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".

            }
        })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });

        final android.app.AlertDialog alert = dialog.create();
        alert.show();
    }



    public class AddDriverCustomAdapterInnerClass extends BaseAdapter {
        private ArrayList<addDriverText> mListItems;
        private LayoutInflater mLayoutInflater;
        Context context;

        public AddDriverCustomAdapterInnerClass(Context context, ArrayList<addDriverText> arrayList){
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

                view = mLayoutInflater.inflate(R.layout.listofadddriver, viewGroup, false);

                // get all views you need to handle from the cell and save them in the view holder
                holder.itemID = (TextView) view.findViewById(R.id.listofadddrivertableidlist);
                holder.itemDriverName = (TextView) view.findViewById(R.id.driverlist);
                holder.itemDriverLicense = (TextView) view.findViewById(R.id.driverlicenselist);
                holder.itemTlcLicense = (TextView) view.findViewById(R.id.tlclicencelist);
                holder.itemPlateNumber = (TextView) view.findViewById(R.id.platenumberlist);
                holder.itemPolicyNumber = (TextView) view.findViewById(R.id.policyumberlist);
                holder.itemBaseName = (TextView) view.findViewById(R.id.basenamecorporationlist);
                holder.itemDelete = (ImageView) view.findViewById(R.id.adddriverdelete);
                holder.itemID.setVisibility(View.GONE);

                holder.itemDelete.setTag(position);

                holder.itemDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
                        dialog.setCancelable(false);
                        dialog.setTitle("Delete Confirmation");
                        dialog.setMessage("Are you sure you want to delete this entry?" );
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Action for "Delete".
//                            mListItems.remove(position);
//                            notifyDataSetChanged();
                                String CurrentDriverID= holder.itemID.getText().toString();
                                new deleteCurrentDriver().execute(CurrentDriverID);
                                Toast.makeText(v.getContext(), "ID"+CurrentDriverID, Toast.LENGTH_SHORT).show();



                            }
                        })
                                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Action for "Cancel".
                                    }
                                });

                        final android.app.AlertDialog alert = dialog.create();
                        alert.show();


                    }
                });



                // save the view holder on the cell view to get it back latter
                view.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder)view.getTag();
            }

            //get the string item from the position "position" from array list to put it on the TextView
            addDriverText stringItem = mListItems.get(position);
            if (stringItem != null) {
                //set the item name on the TextView
                holder.itemID.setText(stringItem.getAddDriverID());
                holder.itemDriverName.setText(stringItem.getDriverName());
                holder.itemDriverLicense.setText(stringItem.getDriverLicense());
                holder.itemTlcLicense.setText(stringItem.getTLCLicense());
                holder.itemPlateNumber.setText(stringItem.getPlateNumber());
                holder.itemPolicyNumber.setText(stringItem.getPolicyNumber());
                holder.itemBaseName.setText(stringItem.getBaseName());
            } else {
                // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
                holder.itemID.setText("Unknown");
                holder.itemDriverName.setText("Unknown");
                holder.itemDriverLicense.setText("Unknown");
                holder.itemTlcLicense.setText("Unknown");
                holder.itemPlateNumber.setText("Unknown");
                holder.itemPolicyNumber.setText("Unknown");
                holder.itemBaseName.setText("Unknown");
            }



            //this method must return the view corresponding to the data at the specified position.
            return view;

        }

        /**
         * Used to avoid calling "findViewById" every time the getView() method is called,
         * because this can impact to your application performance when your list is large
         */
        private class ViewHolder {

            protected TextView itemID;
            protected TextView itemDriverName;
            protected TextView itemDriverLicense;
            protected TextView itemTlcLicense;
            protected TextView itemPlateNumber;
            protected TextView itemPolicyNumber;
            protected TextView itemBaseName;
            protected ImageView itemDelete;

        }






    }




}
