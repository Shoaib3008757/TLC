package com.ranglerz.tlc.tlc;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.TlcInsurance;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    EditText fullname,address, Addresszipcode , dob,driverLicense,tclLicense,phoneNum  ,SSN, Email ,Password;
    Button submit;
    String strname , strAddress , strAddressZipCode , strdateofBirth , strdriver_license , strtlc_license ,
            strphone , strssn , stremail  , strpassword;

    String tag_name = "name" ;
    String tag_address = "address" ;
    String tag_zipcode = "zipcode" ;
    String tag_dob = "dob" ;
    String tag_driverlicense = "driverlicense" ;
    String tag_tlclicense = "tlclicense" ;
    String tag_phonenum = "phonenum" ;
    String tag_ssn = "ssn" ;
    String tag_email = "email" ;
    String tag_password = "password";
    JSONObject jsonObject;
    JSONArray jsonArray;
    public ProgressDialog dailog;
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    Calendar myCalendar = Calendar.getInstance();
    String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createNetErrorDialog();
        new geteditprofileasynctask().execute();



        fullname = (EditText)findViewById(R.id.fullname);
        address = (EditText)findViewById(R.id.address);
        Addresszipcode = (EditText)findViewById(R.id.addresszipcode);
        dob = (EditText)findViewById(R.id.dob);
        driverLicense = (EditText)findViewById(R.id.driverlicense);
        tclLicense = (EditText)findViewById(R.id.tcllicense);
        phoneNum = (EditText)findViewById(R.id.phonenum);
        SSN = (EditText)findViewById(R.id.ssn);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);

        submit = (Button)findViewById(R.id.savechangebtn);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strname =fullname.getText().toString();
                strAddress =address.getText().toString();
                strAddressZipCode =Addresszipcode.getText().toString();
                strdateofBirth =dob.getText().toString();
                strdriver_license =driverLicense.getText().toString();
                strtlc_license =tclLicense.getText().toString();
                strphone =phoneNum.getText().toString();
                strssn =SSN.getText().toString();
                stremail =Email.getText().toString();
                strpassword =Password.getText().toString();

                if(strname.equals(""))
                {
                    fullname.setError("Please Enter Name");
                }
                else if(strAddress.equals(""))
                {
                    address.setError("Please Enter Address");
                }
                else if(strAddressZipCode.equals(""))
                {
                    Addresszipcode.setError("Please Enter Zip Code");
                }
                else if(strdateofBirth.equals(""))
                {
                    dob.setError("Please Enter Date of Birth");
                }
                else if(strdriver_license.equals(""))
                {
                    driverLicense.setError("Please Enter Driver Licencese ");
                }
                else if(strtlc_license.equals(""))
                {
                    tclLicense.setError("Please Enter Tlc Licencese ");
                }
                else if(strphone.equals(""))
                {
                    phoneNum.setError("Please Enter Phone Number ");
                }
                else if(strssn.equals(""))
                {
                    SSN.setError("Please Enter SSN ");
                }
                else if(!emailValidator(stremail))
                {
                    Email.setError("Invalid Email Address!");
                }
                else if(strpassword.trim().equals("") || Password.length() <= 5)
                {
                    Password.setError("Please password atleast 6 Cherecter");
                }
                else
                {
                    new editProfileAysncTask().execute();
                }

            }
        });

    }

    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    //this asynctask use to get data from registration
    public class geteditprofileasynctask extends AsyncTask<String , Void ,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(EditProfile.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://www.tlcapp.nyc/admin/services/get_user_info.php");
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
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
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

                    String abc = result.toString();


                    return abc;


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
            }  finally {
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
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                try {
                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("array data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject finalobject = jsonArray.getJSONObject(i);
                        tag_name = finalobject.getString("user_name");
                        tag_address = finalobject.getString("user_address");
                        tag_zipcode = finalobject.getString("user_zip");
                        tag_dob = finalobject.getString("user_dob");
                        tag_driverlicense = finalobject.getString("user_dmv_license");
                        tag_tlclicense = finalobject.getString("user_tlc_license");
                        tag_phonenum = finalobject.getString("user_phone");
                        tag_ssn = finalobject.getString("user_ssn");
                        tag_email = finalobject.getString("user_email");
                        tag_password = finalobject.getString("");

                        Log.d("tag_name", "name" + tag_name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Log.d("tag_name" , "name" +tag_name);
                fullname.setText(tag_name);
                address.setText(tag_address);
                Addresszipcode.setText(tag_zipcode);
                dob.setText(tag_dob);
                driverLicense.setText(tag_driverlicense);
                tclLicense.setText(tag_tlclicense);
                phoneNum.setText(tag_phonenum);
                SSN.setText(tag_ssn);
                Email.setText(tag_email);
                Password.setText(tag_password);

                dailog.dismiss();

                Toast.makeText(getApplicationContext(),"Data Loaded Successfully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();
            }else if (Response.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(EditProfile.this, "Data Not Loaded", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(EditProfile.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public class editProfileAysncTask extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(EditProfile.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/Registration.php");
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
                        .appendQueryParameter("username", strname)
                        .appendQueryParameter("address", strAddress)
                        .appendQueryParameter("zipcode", strAddressZipCode)
                        .appendQueryParameter("dob", strdateofBirth)
                        .appendQueryParameter("driverlicence", strdriver_license)
                        .appendQueryParameter("tlclicence", strtlc_license)
                        .appendQueryParameter("phonenum", strphone)
                        .appendQueryParameter("ssn", strssn)
                        .appendQueryParameter("email" , stremail)
                        .appendQueryParameter("password", strpassword);

                Log.d("name" , "strname"+strname);
                Log.d("name" , "straddress"+strAddress);

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

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfile.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();
                EditProfile.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(EditProfile.this, "This Email is Already Registered , Please Try With Another Email!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(EditProfile.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

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
                                    EditProfile.this.finish();
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

}
