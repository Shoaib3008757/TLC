package com.ranglerz.tlc.tlc;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.ReportAccident.ReportAccidentForm;

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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class RegistrationForm extends AppCompatActivity {
    EditText fullname,address, Addresszipcode , dob,driverLicense,tclLicense,phoneNum  ,SSN, Email ,Password;
    Button submit;
    TlcDatabase tlcDatabase;
    HttpURLConnection connection;
    ProgressDialog progress;
    String abc;
    Calendar myCalendar = Calendar.getInstance();
    String strname , strAddress , strAddressZipCode , strdateofBirth , strdriver_license , strtlc_license ,
            strphone , strssn , stremail  , strpassword;
    SharedPreferences sharedPreferencesToken;
    String Token = "token";
    String userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        tlcDatabase = new TlcDatabase(this);
        progress = new ProgressDialog(this);
        progress.setMessage("wait");
        progress.setCancelable(false);


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

        submit = (Button)findViewById(R.id.submit);

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
                new DatePickerDialog(RegistrationForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


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


//
                    //new RegistrationTask().execute(name , Address , dateofBirth , driver_license , tlc_license , phone ,  ssn ,email   , password);
//
//
//
//                String check = tlcDatabase.checkUserName(fullname.getText().toString());
//                Log.d("checkabc", (check));
//
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
                    Password.setError("Please enter Password atleast 6 Cherecter");
                }
                else
                {
                    //Toast.makeText(RegistrationForm.this, "data "+name + Address + dateofBirth, Toast.LENGTH_SHORT).show();
                    //new RegistrationTask().execute(name, Address, dateofBirth, driver_license, tlc_license, phone, ssn, email, password);
                    new Registratoin().execute();
                }
//
//                       Toast.makeText(RegistrationForm.this, "Account Created", Toast.LENGTH_LONG).show();
//                       fullname.setText("");
//                        address.setText("");
//                        dob.setText("");
//                        driverLicense.setText("");
//                        tclLicense.setText("");
//                        phoneNum.setText("");
//                        Email.setText("");
//                        Password.setText("");

//                       Intent intent = new Intent(RegistrationForm.this,Login.class);
//                       startActivity(intent);

//                else {
//                    if (fullname.getText().toString().equals(check)) {
//                        Toast.makeText(RegistrationForm.this, "Sorry, that Username is already Exist. Please choose another", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        boolean result = tlcDatabase.insertDataInRegistrationForm(fullname.getText().toString(), address.getText().toString(), dob.getText().toString(),
//                                driverLicense.getText().toString(), tclLicense.getText().toString(), phoneNum.getText().toString(),
//                                Email.getText().toString(), Password.getText().toString());
//                        if (result)
//                            Toast.makeText(RegistrationForm.this, "Account Created", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(RegistrationForm.this, "Account Not Created", Toast.LENGTH_LONG).show();


//                        Intent intent = new Intent(RegistrationForm.this, Login.class);
//                        startActivity(intent);


//                    }

//                    }
//                fullname.setText("");
//                address.setText("");
//                dob.setText("");
//                driverLicense.setText("");
//                tclLicense.setText("");
//                phoneNum.setText("");
//                Email.setText("");
//                SSN.setText("");
//                Password.setText("");
                }





        });




    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }


    public class Registratoin extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(RegistrationForm.this);
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


                sharedPreferencesToken = getSharedPreferences("tokent" , 1);
                userToken = sharedPreferencesToken.getString(Token , null);
                Log.d("Save Token" , "Save Token"+userToken);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", strname)
                        .appendQueryParameter("address", strAddress)
                        .appendQueryParameter("zipcode", strAddressZipCode)
                        .appendQueryParameter("dob", strdateofBirth)
                        .appendQueryParameter("driverlicence", strdriver_license)
                        .appendQueryParameter("tlclicence", strtlc_license)
                        .appendQueryParameter("phonenum", strphone)
                        .appendQueryParameter("ssn", strssn)
                        .appendQueryParameter("email" , stremail)
                        .appendQueryParameter("password", strpassword)
                        .appendQueryParameter("token", userToken);

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
                Intent intent = new Intent(RegistrationForm.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();
                RegistrationForm.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(RegistrationForm.this, "This Email is  Already Registered , Please Try With Another Email!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(RegistrationForm.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();
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
                                    RegistrationForm.this.finish();
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
