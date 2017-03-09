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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.ThankYouScreen;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.EmailClasses.GMailSender;

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

public class MonthlyMonitoring extends AppCompatActivity {


    EditText DiamondNumber , BaseNumber , Medallion_Plate , BaseName , FleetName   , NoofVehicle , AddComment , MoreThenSixty;
    Button submit;
    Spinner Payment;
    TextView mOutputText;
    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "Monthly Monitoring";
    public String message;
    String strCompanyName , strBaseNumber , strDiamondNumber ,  strMedallion_Plate ,   strFleetName , strnumbrofvehicle  , strAddComment, strPayment;
    private String[] paymentinformation = { "Select your Cars Limit" , "1-10 Cars" , "10-30 Cars", "30-60 Cars" , "More then 60"};
    //public String message = "hello";
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_monitoring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();


        //noOfCars = (EditText) findViewById(R.id.noOfCarsMonthlyMonitoring);
        BaseNumber = (EditText) findViewById(R.id.basenumbermonthly);
        DiamondNumber = (EditText) findViewById(R.id.diamondnumbermonthly);
        Medallion_Plate = (EditText) findViewById(R.id.medallionplatemonthly);
        BaseName = (EditText) findViewById(R.id.companynamemonthly);
        FleetName = (EditText) findViewById(R.id.fleetmonthly);
        NoofVehicle = (EditText) findViewById(R.id.numberofvehicle);
        AddComment = (EditText) findViewById(R.id.monthlyaddcoment);
        submit = (Button) findViewById(R.id.submitMonthlyMonitoring);
//        MoreThenSixty = (EditText) findViewById(R.id.morethensixty);
//        Payment = (Spinner) findViewById(R.id.payment);
//        MoreThenSixty.setVisibility(View.GONE);
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, paymentinformation);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Payment.setAdapter(adapter);

//        Payment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                switch (position) {
//
//                    case 0:
//                        MoreThenSixty.setVisibility(View.GONE);
//                        break;
//                    case 1:
//                        MoreThenSixty.setVisibility(View.GONE);
//                        break;
//                    case 2:
//                        MoreThenSixty.setVisibility(View.GONE);
//                        break;
//                    case 3:
//                        MoreThenSixty.setVisibility(View.GONE);
//                        break;
//                    case 4:
//                        MoreThenSixty.setVisibility(View.VISIBLE);
//                        break;
//                    default:
//                        break;
//
//                }
//            }
//        });

        //MedallionOwner = (EditText) findViewById(R.id.medallionmonthly);



        // mOutputText = (TextView) findViewById(R.id.internetConnectivityMonthlyMonitoring);

//        if(isDeviceOnline()) {
//            //  sendEmail.setEnabled(false);
//            mOutputText.setText("Network Connected");
//            mOutputText.setTextColor(Color.GREEN);
//        }
//        else {
//            mOutputText.setText("No Network Connection Available");
//            Toast.makeText(getApplicationContext(), "No Network Connection Available", Toast.LENGTH_SHORT).show();
//        }

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Email Them The User's No Of Cars
//
//                if(isDeviceOnline()) {
//                    //  sendEmail.setEnabled(false);
//
//                    // send email
//                    new SendEmail().execute();
//
//                }
//                else {
//                    mOutputText.setText("No Network Connection Available");
//                    Toast.makeText(getApplicationContext(), "No Network Connection Available", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // new SendEmail().execute();
                strDiamondNumber= DiamondNumber.getText().toString();
                strBaseNumber= BaseNumber.getText().toString();
                strMedallion_Plate= Medallion_Plate.getText().toString();
                strCompanyName= BaseName.getText().toString();
                strFleetName= FleetName.getText().toString();
                strnumbrofvehicle= NoofVehicle.getText().toString();
                strAddComment= AddComment.getText().toString();
                //strPayment = Payment.getSelectedItem().toString();



                if(strCompanyName.equals(""))
                {
                    BaseName.setError("Please Enter Base Name");
                }
                else if(strBaseNumber.equals(""))
                {
                    BaseNumber.setError("Please Enter Base Number");
                }
                else if(strDiamondNumber.equals(""))
                {
                    DiamondNumber.setError("Please Enter Diamond Number");
                }
                else if(strMedallion_Plate.equals(""))
                {
                    Medallion_Plate.setError("Please Enter Medallion/Plate");
                }
                else if(strFleetName.equals(""))
                {
                    FleetName.setError("Please Enter Fleet Name");
                }
                else if(strnumbrofvehicle.equals(""))
                {
                    NoofVehicle.setError("Please Enter No of Vehicle");
                }
//                else if(strPayment.equals("Select your Cars Limit"))
//                {
//                    Toast.makeText(MonthlyMonitoring.this, "Please Select your Cars Limit", Toast.LENGTH_SHORT).show();
//                }
                else{

                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email + "\n\nBase Name: " +  strCompanyName+ "\nBase Number: " + strBaseNumber + "\nDiamond Number: " + strDiamondNumber + "\nMedallion/Plate Number: " + strMedallion_Plate +
                                "\nFleet / Medallion Owner Name: " + strFleetName +   "\nNumber of Vehicles: " + strnumbrofvehicle +  "\nSelected Cars Limit: " + strPayment;
                        new monthlymonitoring().execute();



                }

            }
        });

    }
   //                 Intent intent = new Intent(MonthlyMonitoring.this , ThankYouScreen.class);
//                    startActivity(intent);
//                    Toast.makeText(getApplicationContext() , message , Toast.LENGTH_LONG).show();

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


//    private boolean isDeviceOnline() {
//        ConnectivityManager connMgr =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//
//
//

    public class monthlymonitoring extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(MonthlyMonitoring.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/monthly_monitering.php");
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
                        .appendQueryParameter("basename", strCompanyName)
                        .appendQueryParameter("basenumber", strBaseNumber)
                        .appendQueryParameter("diamondnumber", strDiamondNumber)
                        .appendQueryParameter("medallionplate", strMedallion_Plate)
                        .appendQueryParameter("fleetmedallion", strFleetName)
                        .appendQueryParameter("numberofvehicle", strnumbrofvehicle)
                        .appendQueryParameter("addcomment", strAddComment)
                        .appendQueryParameter("selectedcarlimit", strPayment)




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
                dataSent();
                //Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(MonthlyMonitoring.this, "Something wrong", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(MonthlyMonitoring.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(MonthlyMonitoring.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {

                GMailSender sender = new GMailSender(fromEmail, password);
                sender.sendMail(subject,
                        message,
                        fromEmail,
                        toEmail);


            } catch (Exception e) {
                Log.d("tag", "Exception Occur" + e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String data) {
            Log.d("tag", "Post Excute Data is " + data);
            dailog.dismiss();
            Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_SHORT).show();
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
                                    MonthlyMonitoring.this.finish();
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
                Intent intent = new Intent(MonthlyMonitoring.this,ThankYouScreen.class);
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




}
