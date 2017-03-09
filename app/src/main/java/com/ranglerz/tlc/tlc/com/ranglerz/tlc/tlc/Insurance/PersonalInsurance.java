    package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.ThankYouScreen;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.EmailClasses.GMailSender;

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

    public class PersonalInsurance extends AppCompatActivity {

        public String toEmail = "tlcappnyc@gmail.com";
        public String fromEmail = "tlcapp16@gmail.com";
        public String password = "tlcapp@1234.";
        public String subject = "Personal Insurance";
        public String message;
        SharedPreferences sharedPreferences;
        String emailkey = "emailkey";
        String userID = "userID";
        public ProgressDialog dailog;

        String tag_name = "name" ;
        String tag_address = "address" ;
        String tag_zipcode = "zipcode" ;
        String tag_dob = "dob" ;
        String tag_driverlicense = "driverlicense" ;
        String tag_tlclicense = "tlclicense" ;
        String tag_phonenum = "phonenum" ;
        String tag_ssn = "ssn" ;
        String tag_email = "email" ;
        JSONObject jsonObject;
        JSONArray jsonArray;
        String Response;



        EditText fullname,address,dob,driverLicense,phoneNum,email,vinnum,einnum,ownername,socialSecurtiyNumber,
                ZipCod ,carMake,carModel,carYear, TaxID , JobDecription , NameOfCompnay , SpouseName ,
                Dateofissue , DateofExpiry , AddComment;
        Button submit , DriverLicence , SSCard , BaseLetter , UtilityBills , MVTitle , SHlPermitCopy , SHLPermitReceipt , IsPermitOwned , CertificateCorporation , FillingRecipt;
        Spinner carType , InsuranceLength;
        RadioGroup MariatalStatus , PriorInsurance , Accident ,  DDC;
        RadioButton MariatalStatusYes ,MariatalStatusNo , PriorInsuranceYes , PriorInsuranceNO , DDCYes , DDCNo , AccidentYes , AccidentNo ,
                MaritialRadioButton , PriorRadioButton ,AccidentRadioButton ,DDCRadioButton  ;
        String strfullname,straddress,strdob,strdriverLicense,strphoneNum,strvinnum,strsocialSecurtiyNumber,strNameOfCompnay,strSpouseName,strDateofissue,strDateofExpiry,
                strInsuranceLength ,strzipcode, getTextMariatalStatus , getTextPriorInsurance , getTextAccident , getTextDDC , strAddcomment;
        int intMariatalStatus,intPriorInsurance,intDDC, intAccident;
        private String[] insurancelength = { "Length of Insurane" , "6 Month" , "1 Year", "2 Or More" };
        Calendar myCalendar = Calendar.getInstance();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_insurance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //createNetErrorDialog();
        showDialogForGettingUserDataFromSignUp();


        fullname = (EditText) findViewById(R.id.fullnameInsuranceForm);
        address = (EditText) findViewById(R.id.addressInsuranceForm);
        ZipCod = (EditText) findViewById(R.id.zipcodepersonal);
        dob = (EditText) findViewById(R.id.dobInsuranceForm);
        driverLicense = (EditText) findViewById(R.id.licensenumberInsuranceForm);
        //tlcLicense = (EditText) findViewById(R.id.tlclicenseInsuranceForm);
        phoneNum = (EditText) findViewById(R.id.phonenumInsuranceForm);
        //email = (EditText) findViewById(R.id.emailInsuranceForm);
        vinnum = (EditText) findViewById(R.id.vinnumInsuranceForm);
        //einnum = (EditText) findViewById(R.id.einnumInsuranceForm);
        //TaxID = (EditText) findViewById(R.id.taxid);
        //ownername = (EditText) findViewById(R.id.ownernameInsuranceForm);
        NameOfCompnay = (EditText) findViewById(R.id.nameofcompany);
        submit = (Button) findViewById(R.id.submitPersonalInsurance);
        socialSecurtiyNumber = (EditText) findViewById(R.id.socialSecurityNumberInsuranceForm);
        AddComment = (EditText) findViewById(R.id.personaladdcoment);

        InsuranceLength = (Spinner)findViewById(R.id.lengthinsurance);



        MariatalStatus = (RadioGroup) findViewById(R.id.martialstatusradiogroup);
        PriorInsurance = (RadioGroup) findViewById(R.id.priorinsuranceradiogroup);
        DDC = (RadioGroup) findViewById(R.id.ddcradiogroup);
        Accident = (RadioGroup) findViewById(R.id.accidentradiogroup);

        MariatalStatusYes = (RadioButton) findViewById(R.id.maritalstatusyes);
        MariatalStatusNo = (RadioButton) findViewById(R.id.maritalstatusno);
        PriorInsuranceYes = (RadioButton) findViewById(R.id.priorinsuranceyes);
        MariatalStatusYes = (RadioButton) findViewById(R.id.priorinsuranceno);
        DDCYes = (RadioButton) findViewById(R.id.ddcyes);
        DDCNo = (RadioButton) findViewById(R.id.ddcno);
        AccidentYes = (RadioButton) findViewById(R.id.accidentyes);
        AccidentNo = (RadioButton) findViewById(R.id.accidenteno);




        NameOfCompnay = (EditText) findViewById(R.id.nameofcompany);
        SpouseName = (EditText) findViewById(R.id.spousename);

        InsuranceLength = (Spinner) findViewById(R.id.lengthinsurance);

        Dateofissue = (EditText) findViewById(R.id.dateofissue);
        DateofExpiry = (EditText) findViewById(R.id.dateofexpiry);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, insurancelength);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InsuranceLength.setAdapter(adapter);

        SpouseName.setVisibility(View.GONE);
        NameOfCompnay.setVisibility(View.GONE);
        InsuranceLength.setVisibility(View.GONE);
        vinnum.setVisibility(View.GONE);
        Dateofissue.setVisibility(View.GONE);
        DateofExpiry.setVisibility(View.GONE);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateofBirth();
            }

        };
        final DatePickerDialog.OnDateSetListener issuedate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                IssueDate();
            }

        };
        final DatePickerDialog.OnDateSetListener expiryDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ExpiryDate();
            }

        };



        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PersonalInsurance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Dateofissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PersonalInsurance.this, issuedate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DateofExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PersonalInsurance.this, expiryDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Accident.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.accidentyes){

                } else {

                }
            }
        });


        MariatalStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.maritalstatusno){
                    SpouseName.setVisibility(View.GONE);
                    vinnum.setVisibility(View.GONE);

                } else {
                    SpouseName.setVisibility(View.VISIBLE);
                    vinnum.setVisibility(View.VISIBLE);


                }
            }
        });

        PriorInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.priorinsuranceno){
                    NameOfCompnay.setVisibility(View.GONE);
                    InsuranceLength.setVisibility(View.GONE);
                } else {
                    NameOfCompnay.setVisibility(View.VISIBLE);
                    InsuranceLength.setVisibility(View.VISIBLE);
                }
            }
        });

        DDC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.ddcyes){
                    Dateofissue.setVisibility(View.VISIBLE);
                    DateofExpiry.setVisibility(View.VISIBLE);
                } else {
                    Dateofissue.setVisibility(View.GONE);
                    DateofExpiry.setVisibility(View.GONE);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strfullname = fullname.getText().toString();
                straddress = address.getText().toString();
                strzipcode = ZipCod.getText().toString();
                strdob = dob.getText().toString();
                strdriverLicense = driverLicense.getText().toString();
                strphoneNum = phoneNum.getText().toString();
                strsocialSecurtiyNumber = socialSecurtiyNumber.getText().toString();
                strAddcomment = AddComment.getText().toString();
                intMariatalStatus = MariatalStatus.getCheckedRadioButtonId();
                MaritialRadioButton = (RadioButton)findViewById(intMariatalStatus);
                strSpouseName = SpouseName.getText().toString();
                strvinnum = vinnum.getText().toString();
                intPriorInsurance = PriorInsurance.getCheckedRadioButtonId();
                PriorRadioButton = (RadioButton)findViewById(intPriorInsurance);
                strNameOfCompnay = NameOfCompnay.getText().toString();
                strInsuranceLength = InsuranceLength.getSelectedItem().toString();
                intAccident= Accident.getCheckedRadioButtonId();
                AccidentRadioButton = (RadioButton)findViewById(intAccident);
                intDDC = DDC.getCheckedRadioButtonId();
                DDCRadioButton = (RadioButton)findViewById(intDDC);
                strDateofissue = Dateofissue.getText().toString();
                strDateofExpiry = DateofExpiry.getText().toString();



                if(strfullname.equals(""))
                {
                    fullname.setError("Please Enter Full Name");
                }
                else if(straddress.equals(""))
                {
                    address.setError("Please Enter Full Address");
                }
                else if(strzipcode.equals(""))
                {
                    ZipCod.setError("Please Enter Zip Code");
                }
                else if(strdob.equals(""))
                {
                    dob.setError("Please Enter Date of Birth");
                }
                else if(strdriverLicense.equals(""))
                {
                    driverLicense.setError("Please Enter License Number");
                }
                else if(strphoneNum.equals("")) {
                    phoneNum.setError("Please Enter Phone Number");
                }
                else if(strsocialSecurtiyNumber.equals(""))
                {
                    socialSecurtiyNumber.setError("Please Enter Social Security Number");
                }
                else if(DDC.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(PersonalInsurance.this, "Please Select DDC Radio Button", Toast.LENGTH_SHORT).show();
                }
                else if(DDCRadioButton.getText().toString().equals("Yes") && strDateofissue.equals("")) {

                    Dateofissue.setError("Please Enter Issue Date");
                }
                else if(DDCRadioButton.getText().toString().equals("Yes") && strDateofExpiry.equals(""))
                {
                    DateofExpiry.setError("Please Enter Expiry Date");
                }
                else if(PriorInsurance.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(PersonalInsurance.this, "Please Select Proir Insuranse Radio Button", Toast.LENGTH_SHORT).show();
                }
                else if(PriorRadioButton.getText().toString().equals("YES") && strNameOfCompnay.equals(""))
                {
                    NameOfCompnay.setError("Please Enter Name of Companay");
                }
                else if(PriorRadioButton.getText().toString().equals("Yes") && strInsuranceLength.equals("Length of Insurane"))
                {
                    Toast.makeText(PersonalInsurance.this, "Please Select (Length of Insurance) Drop Down Menu", Toast.LENGTH_SHORT).show();
                }
                else if(MariatalStatus.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(PersonalInsurance.this, "Please Select Martial Status Radio Button", Toast.LENGTH_SHORT).show();
                }
                else if(MaritialRadioButton.getText().toString().equals("Yes") && strSpouseName.equals(""))
                {
                     SpouseName.setError("Please Enter Spouse Name");
                }
                else if(MaritialRadioButton.getText().toString().equals("Yes") && strvinnum.equals(""))
                {
                    vinnum.setError("Please Enter VIN Number");
                }
                else if(Accident.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(PersonalInsurance.this, "Please Select Accidnet Radio Button", Toast.LENGTH_SHORT).show();
                }
                else
                {
//                    Intent intent = new Intent(PersonalInsurance.this , ThankYouScreen.class);
//                    startActivity(intent);

                    getTextMariatalStatus = MaritialRadioButton.getText().toString();
                    getTextPriorInsurance = PriorRadioButton.getText().toString();
                    getTextAccident = AccidentRadioButton.getText().toString();
                    getTextDDC = DDCRadioButton.getText().toString();

                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email +"\n\nFull namer: " + strfullname + "\nAddress: " + straddress + "\nZip Code: " + strzipcode + "\nData of Birth: " + strdob +
                                "\nLicense Number: " + strdriverLicense + "\nPhone Number: " + strphoneNum + "\nSocial Securtiy Number: " + strsocialSecurtiyNumber +
                                "\nMariatal Status: " + getTextMariatalStatus + "\nSpouse Name: " + strSpouseName + "\nVin Num: " + strvinnum +
                                "\nPrior Insurance: " + getTextPriorInsurance + "\nName Of Compnay: " + strNameOfCompnay + "\nInsurance Lengthr: " + strInsuranceLength +
                                "\nAccident: " + getTextAccident + "\nDDC: " + getTextDDC + "\nDate of issue: " + strDateofissue + "\nDate of Expiry: " + strDateofExpiry;



                    new Personal().execute();
                    //new SendEmail().execute();

                }





                //Toast.makeText(getApplicationContext() , message , Toast.LENGTH_LONG).show();

            }
        });

    }

        private void DateofBirth() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dob.setText(sdf.format(myCalendar.getTime()));

        }
        private void IssueDate() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Dateofissue.setText(sdf.format(myCalendar.getTime()));
        }
        private void ExpiryDate() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            DateofExpiry.setText(sdf.format(myCalendar.getTime()));
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

        private void showDialogForGettingUserDataFromSignUp() {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_action_paste);
            builder.setTitle("Auto Fill Data");
            builder.setMessage("Do you want to Load Your Information From Sign Up");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                    new autofill().execute();

                    // Load Data From Session or Shared Preferences
                    // OR Load Data from Back Panel

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Use Fill the form by it self
                    dialog.dismiss();

                }
            });

            builder.show();


        }


        public class Personal extends  AsyncTask<String , Void ,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog = new ProgressDialog(PersonalInsurance.this);
                dailog.setTitle("Please Wait.... ");
                dailog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                HttpURLConnection connection = null;
                try {

                    //URL url = new URL("http://10.0.3.2/login/Registration.php");
                    URL url = new URL("http://www.tlcapp.nyc/admin/services/insurance_personal.php");
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
                            .appendQueryParameter("fullname", strfullname)
                            .appendQueryParameter("address", straddress)
                            .appendQueryParameter("zipcode", strzipcode)
                            .appendQueryParameter("dob", strdob)
                            .appendQueryParameter("driverlicense", strdriverLicense)
                            .appendQueryParameter("phonenumber", strphoneNum)
                            .appendQueryParameter("socialsecuritynumber", strsocialSecurtiyNumber)
                            .appendQueryParameter("addcomment", strAddcomment)
                            .appendQueryParameter("ddc", getTextDDC )
                            .appendQueryParameter("issuedate", strDateofissue )
                            .appendQueryParameter("expirydate", strDateofExpiry )
                            .appendQueryParameter("priorinsurance", getTextPriorInsurance )
                            .appendQueryParameter("nameofcompany", strNameOfCompnay )
                            .appendQueryParameter("insurancelenght", strInsuranceLength )
                            .appendQueryParameter("maritalstatus", getTextMariatalStatus )
                            .appendQueryParameter("spousename", strSpouseName )
                            .appendQueryParameter("strvinnumber", strvinnum )
                            .appendQueryParameter("accident", getTextAccident )




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
                    //Toast.makeText(PersonalInsurance.this, "Something wrong", Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    dailog.dismiss();
                    Toast.makeText(PersonalInsurance.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }

        }


        //this asynctask use to get data from registration
        public class autofill extends AsyncTask<String , Void ,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog = new ProgressDialog(PersonalInsurance.this);
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
                            // String tag_password = finalobject.getString("");

                            Log.d("tag_name", "name" + tag_name);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    fullname.setText(tag_name);
                    address.setText(tag_address);
                    ZipCod.setText(tag_zipcode);
                    dob.setText(tag_dob);
                    driverLicense.setText(tag_driverlicense);
                    phoneNum.setText(tag_phonenum);
                    socialSecurtiyNumber.setText(tag_ssn);

                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                    dailog.dismiss();
                    Toast.makeText(getApplicationContext(),"Data Loaded SuccessFully",Toast.LENGTH_SHORT).show();

                    //Login.this.finish();
                }else if (Response.equalsIgnoreCase("false")){

                    // If username and password does not match display a error message
                    dailog.dismiss();
                    Toast.makeText(getApplicationContext(),"Data Not Loaded",Toast.LENGTH_SHORT).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    dailog.dismiss();
                    Toast.makeText(PersonalInsurance.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }

        }



        public class SendEmail extends AsyncTask<Void,Void,String>
        {
            public ProgressDialog dailog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog = new ProgressDialog(PersonalInsurance.this);
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
                                        PersonalInsurance.this.finish();
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
            //createNetErrorDialog();
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
                    Intent intent = new Intent(PersonalInsurance.this,ThankYouScreen.class);
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
