package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCCarPurchaseRentSale;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CarPurchaseRentSale extends AppCompatActivity {

    EditText model , make , year , color, mileage , Contect , Address  ,Zip , AddComment;
    Spinner spinnerCartype;
    RadioGroup Carpurchaseorrent;
    RadioButton Forcarpuchase,Forcarrent , ForSale , CarpurchaseRadioButton;
    private String[] stringCarType = { "Select Your Car", "Medallion", "Black Car", "Car SUV","Green Cab" , "Limousine" , "Taxi" };
    String strmodel , strmake ,stryear ,strcolor ,strmileage ,strspinnerCartype , strContect ,
            strAdress  ,  strZipcode , strAddComment ,  GetTextCarPurchse;
    int intCarpurchaseorrent;
    Button submit;
    TextView SpinnerError;
    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "Car Purchase / Rent";
    public String message = "";
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    Calendar myCalendar = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_purchase_rent_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();

        spinnerCartype = (Spinner) findViewById(R.id.spinnerCarPurchase);
        model = (EditText) findViewById(R.id.carModelCarPurchase);
        make = (EditText) findViewById(R.id.carMakeCarPurchase);
        year = (EditText) findViewById(R.id.carYearCarPurchase);
        color = (EditText) findViewById(R.id.carColorCarPurchase);
        mileage = (EditText) findViewById(R.id.carMileageCarPurchase);
        Contect = (EditText) findViewById(R.id.contectno);
        Address = (EditText) findViewById(R.id.address);
        Zip = (EditText) findViewById(R.id.zipcodecar);
        AddComment = (EditText) findViewById(R.id.carpurchaseaddcoment);


        Carpurchaseorrent = (RadioGroup) findViewById(R.id.carpurchaseorrent);
        Forcarpuchase = (RadioButton) findViewById(R.id.forcarpuchase);
        Forcarrent = (RadioButton) findViewById(R.id.forcarrent);
        ForSale = (RadioButton) findViewById(R.id.forsale);


        submit = (Button) findViewById(R.id.submitCarPurchase);

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

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CarPurchaseRentSale.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Carpurchaseorrent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        if(checkedId == R.id.forcarpuchase){
//
//                        }
//                        else if(checkedId == R.id.forcarrent)
//                        {
//
//                        }
//                        else
//                        {
//                            Toast.makeText(getApplicationContext() , "Please Select Car Purchase / Rent" ,Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                strmodel = model.getText().toString();
                strmake = make.getText().toString();
                stryear = year.getText().toString();
                strcolor = color.getText().toString();
                strmileage = mileage.getText().toString();
                strContect = Contect.getText().toString();
                strAdress = Address.getText().toString();
                strZipcode = Zip.getText().toString();
                strAddComment = AddComment.getText().toString();
                strspinnerCartype = spinnerCartype.getSelectedItem().toString();
                intCarpurchaseorrent = Carpurchaseorrent.getCheckedRadioButtonId();
                CarpurchaseRadioButton = (RadioButton) findViewById(intCarpurchaseorrent);

                if(Carpurchaseorrent.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(CarPurchaseRentSale.this, "Please Select (Car Purchase , Rent , Sale) Radio Button", Toast.LENGTH_SHORT).show();

                }
                else if(strspinnerCartype.equals("Select Your Car"))
                {
                    Toast.makeText(CarPurchaseRentSale.this, "Please (Select Your Car) Drop Down Menu", Toast.LENGTH_SHORT).show();
                }
                else if(strmodel.equals(""))
                {
                    model.setError("Please Enter Model");
                }
                else if(strmake.equals(""))
                {
                    make.setError("Please Enter Make");
                }
                else if(stryear.equals(""))
                {
                    year.setError("Please Enter Year");
                }
                else if(strcolor.equals(""))
                {
                    color.setError("Please Enter Color");
                }
                else if(strmileage.equals(""))
                {
                    mileage.setError("Please Enter Mileage");
                }
                else if(strAdress.equals(""))
                {
                    Address.setError("Please Enter Address");
                }
                else if(strZipcode.equals(""))
                {
                    Zip.setError("Please Enter Address Zip Code");
                }
                else if(strContect.equals(""))
                {
                    Contect.setError("Please Enter Contect Number");
                }
                else
                {

                    GetTextCarPurchse = CarpurchaseRadioButton.getText().toString();

                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

//                        message = "User Email: " +email + "\nReport Accident Image";

                        message = "User Email: " +email + "\n\nCar Purchase / Rent : " + GetTextCarPurchse + "\nSelected Car : " + strspinnerCartype +  "\nModel : " + strmodel + "\nMake : " + strmake +
                                "\nYear: " + stryear + "\nColor: " + strcolor + "\nMileage: " + strmileage
                                 + "\nAddress: " + strAdress + "\nAddress Zip Code: " + strZipcode +"\nContect Number : " + strContect;




                    //Toast.makeText(CarPurchaseRentSale.this, ""+message, Toast.LENGTH_SHORT).show();
                    //new SendEmail().execute();
                    new CarPurchase_Rent().execute();
                }

            }
        });




        final String[] SelectCar = getResources().getStringArray(R.array.Car_purchase_rent_sale);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, SelectCar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCartype.setAdapter(adapter);


        spinnerCartype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch (arg2) {
                    case 0:


                        break;
                    case 1:


                        break;
                    case 2:


                        break;
                    case 3:


                        break;
                    case 4:


                        break;

                    case 5:
                        break;
                    default:

                        break;

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });







    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        year.setText(sdf.format(myCalendar.getTime()));
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


//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId())
//            case R.id.radio_ForPurchaseCarPurchase:
//                if (checked) {
//                    // Rent Clicked
//                    Log.d("tag", "Purchase Clicked");
//                }
//                break;
//            case R.id.radio_ForSaleCarPurchase:
//                if (checked) {
//                    // Sale Clicked
//                    Log.d("tag", "Sale Clicked");
//                }
//                break;
//        }
//    }


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
                                    CarPurchaseRentSale.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            //remainging
        }
    }




    //Car Purchas / rent Asynctask
    public class CarPurchase_Rent extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CarPurchaseRentSale.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/car_purchase_rent.php");
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
                        .appendQueryParameter("carpurchaserent", GetTextCarPurchse)
                        .appendQueryParameter("selectedcar",  strspinnerCartype)
                        .appendQueryParameter("model", strmodel)
                        .appendQueryParameter("make", strmake)
                        .appendQueryParameter("year", stryear)
                        .appendQueryParameter("color", strcolor)
                        .appendQueryParameter("mileage", strmileage)
                        .appendQueryParameter("address" , strAdress)
                        .appendQueryParameter("zipcode", strZipcode)
                        .appendQueryParameter("contact", strContect)
                        .appendQueryParameter("addcomment", strAddComment)



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
                //Toast.makeText(getApplicationContext(),"Date sent Successfully",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(RegistrationForm.this,SelectOptionNavigation.class);
//                startActivity(intent);
//                //Login.this.finish();
//                RegistrationForm.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(CarPurchaseRentSale.this, "Date Not Sent", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(CarPurchaseRentSale.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }




    //send email asynctask
    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CarPurchaseRentSale.this);
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

                //sender.addAttachment(Environment.getExternalStorageDirectory().getPath());

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
            Intent intent = new Intent(CarPurchaseRentSale.this , TlcCarPurchase.class);
            startActivity(intent);
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
                Intent intent = new Intent(CarPurchaseRentSale.this,ThankYouScreen.class);
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
