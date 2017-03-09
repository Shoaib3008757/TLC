package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.Globals;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.RequestHandler;
import com.ranglerz.tlc.tlc.ThankYouScreen;
import com.ranglerz.tlc.tlc.TlcDatabase;
import com.ranglerz.tlc.tlc.Utility;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.EmailClasses.GMailSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import java.util.HashMap;
import java.util.Locale;

public class TlcInsurance extends AppCompatActivity {

    private static final String TAG = TlcInsurance.class.getSimpleName();
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChoosenTask;
    public static int btnClicked=0;
    public static int licenseButton = 1;
    public static int ssCardButton = 2;
    public static int baseLetterButton = 3;
    public static int utilityBillsbutton = 4;
    public static int mvTitlebutton = 5;
    public static int shlPermitbutton = 6;
    public static int shlpermitreceiptbutton = 7;
    public static int ispermitownedbutton = 8;
    public static int sixhrdrivingbutton = 9;
    public static int certificatebutton = 10;
    public static int fillingbutton = 11;


    private int REQUEST_CAMERA_LICENSE = 21;
    private int REQUEST_CAMERA_SSCARD = 22;
    private int REQUEST_CAMERA_BASELETTER = 23;
    private int REQUEST_CAMERA_UTILITYBILLS = 24;
    private int REQUEST_CAMERA_MVTITLE = 25;
    private int REQUEST_CAMERA_SHLPERMIT = 26;
    private int REQUEST_CAMERA_SHLRECEIPT = 27;
    private int REQUEST_CAMERA_ISPERMITOWNED = 28;
    private int REQUEST_CAMERA_sixhrdriving = 29;
    private int REQUEST_CAMERA_certificate = 30;
    private int REQUEST_CAMERA_filling = 31;



    public int SELECT_FILE_LICENSE = 41;
    public int SELECT_FILE_SSCARD = 42;
    public int SELECT_FILE_BASELETTER = 43;
    public int SELECT_FILE_UTILITYBILLS = 44;
    public int SELECT_FILE_MVTITLE = 45;
    public int SELECT_FILE_SHLPERMIT = 46;
    public int SELECT_FILE_SHLRECEIPT = 47;
    public int SELECT_FILE_ISPERMITOWNED = 48;
    public int SELECT_FILE_sixhrdriving = 49;
    public int SELECT_FILE_certificate = 50;
    public int SELECT_FILE_filling = 51;




    EditText fullname,address, Zipcode , dob,driverLicense,tlcLicense,phoneNum,email,vinnum,einnum,ownername,socialSecurtiyNumber , AddComment,carMake,carModel,carYear, TaxID;
    Button submit , DriverLicence , SSCard , BaseLetter , UtilityBills , MVTitle ,  SixhrDrivingClass,
            SHlPermitCopy , SHLPermitReceipt , IsPermitOwned , CertificateCorporation , FillingRecipt;
    Spinner carType;
    TlcDatabase tlcDatabase;
    ImageView  DriverLicenceImageView , SSCardImageView , BaseLetterImageView , UtilityBillsImageView , MVTitleImageView  , SixhrDrivingClassImageView , SHlPermitCopyImageView , SHLPermitReceiptImageView , IsPermitOwnedImageView , CertificateCorporationImageView , FillingReciptImageView;
    RadioButton OwnedYes ,OwnedNo , CabPolicyYes , CabpolicyNo , NeedFullCoverageYes ,
            NeedFullCoverageNo , PermitRadioGroup , CabPolicyRadioGroup , NeedFullCoverageis;
    RadioGroup IsPermitRadioGroup , IsCabPolicyRadioGroup , NeedFullCoverage  ;

    String DriverLicenceImagepath ,SSCardImagepath ,BaseLetterImagepath ,UtilityBillsImagepath ,
            MVTitleImagepath ,SixhrDrivingClassImagepath ,SHlPermitCopyImagepath ,SHLPermitReceiptImagepath
            ,IsPermitOwnedImagepath ,CertificateCorporationImagepath , FillingReciptImagePath;


    String strfullname , straddress , strzipcode ,strdob ,strdriverLicense, strtlcLicense,strphoneNum,
    stremail,strvinnum,streinnum, strownername , strsocialSecurtiyNumber , strAddComment ,
            strcarType , strcarMake,strcarModel,strcarYear,strTaxID , getTExtPermitRadioGroup ,
    getTextCabPolicyRadioGroup , getTExtNeedFullCoverageis;
    int intIsPermitRadioGroup  , intIsCabPolicyRadioGroup , intNeedFullCoverage ;

    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "TLC Insurance Form";
    public String message = "";
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    int st;
    Uri imageUri;
    Bitmap thumbnail;
    String fname;
    Calendar myCalendar = Calendar.getInstance();
    public ProgressDialog dailog;

    String Response;


    private String[] carTypes = { "Type of Car","Black Car", "SHL Green Cab", "Car Service", "Luxury Base","Corporation" };

    int tag_id ;
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

    GMailSender sender = new GMailSender(fromEmail, password);


    public String SERVER = "http://10.0.3.2/login/upload.php";
    public static final String UPLOAD_KEY = "image";
   // public static final String TAGa = "MY MESSAGE";
    //private static final String TAG = TlcInsurance.class.getSimpleName();
    Bitmap bitmap1 , bitmap2 , bitmap3 , bitmap4 , bitmap5 , bitmap6, bitmap7 , bitmap8 , bitmap9 ;
    String timestamp1 , timestamp2 , timestamp3, timestamp4, timestamp5, timestamp6, timestamp7, timestamp8 ,timestamp9;

    String uploadDriverLicense;
    String uploadSSCard;
    String uploadBaseLetter;
    String uploadUtilityBills;
    String uploadMVTitle;
    String uploadSixHr;
    String uploadpowerofAttorney;
    String uploadCertificateIncorporation;
    String uploadFillingReceipt;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlc_insurance);
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        tlcDatabase = new TlcDatabase(this);

        showDialogForGettingUserDataFromSignUp();


        setUpIds();

        DriverLicenceImageView.setVisibility(View.GONE);
        SSCardImageView.setVisibility(View.GONE);
        BaseLetterImageView.setVisibility(View.GONE);
        UtilityBillsImageView.setVisibility(View.GONE);
        MVTitleImageView.setVisibility(View.GONE);
        SixhrDrivingClassImageView.setVisibility(View.GONE);
        SHlPermitCopyImageView.setVisibility(View.GONE);
        SHLPermitReceiptImageView.setVisibility(View.GONE);
        IsPermitOwnedImageView.setVisibility(View.GONE);
        CertificateCorporationImageView.setVisibility(View.GONE);
        FillingReciptImageView.setVisibility(View.GONE);




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, carTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carType.setAdapter(adapter);

        //final int pos = carType.getSelectedItemPosition();
        st =carType.getSelectedItemPosition();
        carType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch (arg2) {
                    case 0:

                        einnum.setVisibility(View.GONE);
                        ownername.setVisibility(View.GONE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);

                        break;
                    case 1:

                        einnum.setVisibility(View.GONE);
                        ownername.setVisibility(View.GONE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);

                        break;
                    case 2:

                        einnum.setVisibility(View.GONE);
                        ownername.setVisibility(View.GONE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);

                        break;
                    case 3:

                        einnum.setVisibility(View.GONE);
                        ownername.setVisibility(View.GONE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);

                        break;
                    case 4:

                        einnum.setVisibility(View.GONE);
                        ownername.setVisibility(View.GONE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);

                        break;

                    case 5:
                        einnum.setVisibility(View.VISIBLE);
                        ownername.setVisibility(View.VISIBLE);
                        SHlPermitCopy.setVisibility(View.GONE);
                        SHLPermitReceipt.setVisibility(View.GONE);
                        SHlPermitCopyImageView.setVisibility(View.GONE);
                        SHLPermitReceiptImageView.setVisibility(View.GONE);
                        break;
                    default:

                        break;

                }

            }


            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        DriverLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = licenseButton;
            }
        });

        SSCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = ssCardButton;

            }
        });

        BaseLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = baseLetterButton;
            }
        });

        UtilityBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = utilityBillsbutton;
            }
        });

        MVTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = mvTitlebutton;
            }
        });
        SixhrDrivingClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = sixhrdrivingbutton;
            }
        });
        SHlPermitCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = shlPermitbutton;
            }
        });
        SHLPermitReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = shlpermitreceiptbutton;
            }
        });
        IsPermitOwned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = ispermitownedbutton;
            }
        });
        CertificateCorporation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = certificatebutton;
            }
        });
        FillingRecipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = fillingbutton;
            }
        });
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
                new DatePickerDialog(TlcInsurance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                boolean result = tlcDatabase.insertDataInTlcInsurance(fullname.getText().toString(), address.getText().toString(), dob.getText().toString(),
//                        driverLicense.getText().toString(), tlcLicense.getText().toString(), phoneNum.getText().toString(),
//                        email.getText().toString(), vinnum.getText().toString(),socialSecurtiyNumber.getText().toString(),carMake.getText().toString(),
//                        carModel.getText().toString(),carYear.getText().toString(),einnum.getText().toString(),ownername.getText().toString());
//                if (result)
//                    Toast.makeText(TlcInsurance.this, "Data Submited successfully", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(TlcInsurance.this, "Data not Submited successfully", Toast.LENGTH_LONG).show();

                strfullname = fullname.getText().toString();
                straddress = address.getText().toString();
                strzipcode = Zipcode.getText().toString();
                strdob = dob.getText().toString();
                strdriverLicense = driverLicense.getText().toString();
                strtlcLicense = tlcLicense.getText().toString();
                strphoneNum = phoneNum.getText().toString();
                stremail = email.getText().toString();
                strvinnum = vinnum.getText().toString();
                strsocialSecurtiyNumber = socialSecurtiyNumber.getText().toString();
                strAddComment = AddComment.getText().toString();
                strcarType = carType.getSelectedItem().toString();
                streinnum = einnum.getText().toString();
                strownername= ownername.getText().toString();
                strcarMake = carMake.getText().toString();
                strcarModel = carModel.getText().toString();
                strcarYear = carYear.getText().toString();
                intIsPermitRadioGroup = IsPermitRadioGroup.getCheckedRadioButtonId();
                PermitRadioGroup = (RadioButton) findViewById(intIsPermitRadioGroup);
                intIsCabPolicyRadioGroup = IsCabPolicyRadioGroup.getCheckedRadioButtonId();
                CabPolicyRadioGroup = (RadioButton) findViewById(intIsCabPolicyRadioGroup);
                intNeedFullCoverage = NeedFullCoverage.getCheckedRadioButtonId();
                NeedFullCoverageis = (RadioButton) findViewById(intNeedFullCoverage);
                strTaxID = TaxID.getText().toString();




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
                    Zipcode.setError("Please Enter Zip Code");
                }
                else if(strdob.equals(""))
                {
                    dob.setError("Please Enter Date of Birth");
                }
                else if(strdriverLicense.equals(""))
                {
                    driverLicense.setError("Please Enter DMV License");
                }
                else if(strtlcLicense.equals(""))
                {
                    tlcLicense.setError("Please Enter TLC License");
                }
                else if(strphoneNum.equals(""))
                {
                    phoneNum.setError("Please Enter Phone Number");
                }
                else if(stremail.equals(""))
                {
                    email.setError("Please Enter Email Address");
                }
                else if(strsocialSecurtiyNumber.equals(""))
                {
                    socialSecurtiyNumber.setError("Please Enter Social Security Number");
                }
                else if(strvinnum.equals(""))
                {
                    vinnum.setError("Please Enter VIN Number");
                }
                else if(DriverLicenceImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Driver License Image" , Toast.LENGTH_SHORT).show();
                }
                else if(SSCardImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload SS Card Image" , Toast.LENGTH_SHORT).show();
                }
                else if(BaseLetterImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Base Letter Image" , Toast.LENGTH_SHORT).show();
                }
                else if(UtilityBillsImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Utility Bills Image" , Toast.LENGTH_SHORT).show();
                }
                else if(MVTitleImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please MV50/Title Letter Image" , Toast.LENGTH_SHORT).show();
                }
                else if(SixhrDrivingClassImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Six HR Driving Class Image" , Toast.LENGTH_SHORT).show();
                }
                else if(strcarType.equals("Type of Car"))
                {
                    Toast.makeText(getApplicationContext() , "Please (Select Type of) Car Drop Down Menu" , Toast.LENGTH_SHORT).show();
                }
                else if(strcarType.equals("Corporation") && streinnum.length()==0)
                {
                    einnum.setError("Please Enter EIN Number");

                }
                 else if(strcarType.equals("Corporation") && strownername.length()==0)
                 {
                     ownername.setError("Please Enter Owner Name");

                 }
                else if (IsPermitRadioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext() , "Please Select Is Permit Owned Radio Button" , Toast.LENGTH_SHORT).show();
                }
                else if(PermitRadioGroup.getText().equals("No") && IsPermitOwnedImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Power Attorney Image" , Toast.LENGTH_SHORT).show();
                }
                else if(IsCabPolicyRadioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext() , "Please Select Is Cab Policy Radio Button" , Toast.LENGTH_SHORT).show();
                }
                else if(CabPolicyRadioGroup.getText().equals("Yes") && CertificateCorporationImageView.getDrawable() == null )
                {
                        Toast.makeText(getApplicationContext() , "Please Upload Certificate of Incorporation Image" , Toast.LENGTH_SHORT).show();

                }
                 else if(CabPolicyRadioGroup.getText().equals("Yes") && FillingReciptImageView.getDrawable() == null )
                 {
                     Toast.makeText(getApplicationContext() , "Please Upload Filling Receipt Image" , Toast.LENGTH_SHORT).show();
                 }
                 else if(CabPolicyRadioGroup.getText().equals("Yes")  && strTaxID.equals("")) {

                     TaxID.setError("Please Enter Tax ID/EIN number");
                 }
                else if(NeedFullCoverage.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext() , "Please Select Need Full Coverage Radio Button" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getTExtPermitRadioGroup = PermitRadioGroup.getText().toString();
                    getTextCabPolicyRadioGroup = CabPolicyRadioGroup.getText().toString();
                    getTExtNeedFullCoverageis = NeedFullCoverageis.getText().toString();


                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email + "\n\nFull Name : " + strfullname + "\nAddress : " + straddress + "\nAddress Zip Code: " + strzipcode + "\nData of Birth : " + strdob +
                                "\nDMV License : " + strdriverLicense + "\nTlc License : " + strtlcLicense + "\nPhone Number : " + strphoneNum
                                +"Email : " + stremail + "\nVIN Number : " + strvinnum + "\nSocial Security Number : " + strsocialSecurtiyNumber +
                                "\nSelected Car Type " + strcarType +
                                "\nEIN Number : " + streinnum + "\nOwner Name : " + strownername + "\nCar Make :" + strcarMake +
                                "\nCar Model :" + strcarModel + "\nCar Year :" + strcarYear + "Tax ID: " + strTaxID +
                                "\nIs Permit Owned :" + getTExtPermitRadioGroup + "\nIs Cab Policy :" + getTextCabPolicyRadioGroup + "\nNeed Full Coverage :" + getTExtNeedFullCoverageis;



                    //new SendEmail().execute();
                    new uploadData().execute();



                }
                //Toast.makeText(TlcInsurance.this, "aslfkafa", Toast.LENGTH_SHORT).show();





//                UploadImage ui1 = new UploadImage();
//                ui1.execute(bitmap1);




//
            }
        });



        IsPermitOwned.setVisibility(View.GONE);
        //IsPermitOwnedImageView.setVisibility(View.GONE);

        IsPermitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ispermitownedno) {
                    IsPermitOwned.setVisibility(View.VISIBLE);
                    //IsPermitOwnedImageView.setVisibility(View.VISIBLE);
                } else {
                    IsPermitOwned.setVisibility(View.GONE);
                    IsPermitOwnedImageView.setVisibility(View.GONE);

                }
            }
        });


        CertificateCorporation.setVisibility(View.GONE);
      //  CertificateCorporationImageView.setVisibility(View.GONE);
        FillingRecipt.setVisibility(View.GONE);
       // FillingReciptImageView.setVisibility(View.GONE);
        TaxID.setVisibility(View.GONE);


        IsCabPolicyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cabpolicyyes) {
                    CertificateCorporation.setVisibility(View.VISIBLE);
                   // CertificateCorporationImageView.setVisibility(View.VISIBLE);
                    FillingRecipt.setVisibility(View.VISIBLE);
                    //FillingReciptImageView.setVisibility(View.VISIBLE);
                    TaxID.setVisibility(View.VISIBLE);
                } else {
                    CertificateCorporation.setVisibility(View.GONE);
                    CertificateCorporationImageView.setVisibility(View.GONE);
                    FillingRecipt.setVisibility(View.GONE);
                    FillingReciptImageView.setVisibility(View.GONE);
                    TaxID.setVisibility(View.GONE);

                }
            }
        });
    }



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    private void showDialogForGettingUserDataFromSignUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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






    private void setUpIds() {

        fullname = (EditText) findViewById(R.id.fullnameInsuranceForm);
        address = (EditText) findViewById(R.id.addressInsuranceForm);
        Zipcode = (EditText) findViewById(R.id.zipcodetlcinsurance);
        dob = (EditText) findViewById(R.id.dobInsuranceForm);
        driverLicense = (EditText) findViewById(R.id.driverlicenseInsuranceForm);
        tlcLicense = (EditText) findViewById(R.id.tlclicenseInsuranceForm);
        phoneNum = (EditText) findViewById(R.id.phonenumInsuranceForm);
        email = (EditText) findViewById(R.id.emailInsuranceForm);
        vinnum = (EditText) findViewById(R.id.vinnumInsuranceForm);
        einnum = (EditText) findViewById(R.id.einnumInsuranceForm);
        TaxID = (EditText) findViewById(R.id.taxid);
        ownername = (EditText) findViewById(R.id.ownernameInsuranceForm);
        submit = (Button) findViewById(R.id.submitInsuranceForm);
        socialSecurtiyNumber = (EditText) findViewById(R.id.socialSecurityNumberInsuranceForm);
        AddComment = (EditText) findViewById(R.id.tlcinsuranceaddcoment);

        // optional fields don't add validation here
        carMake  = (EditText) findViewById(R.id.carMakeInsuranceForm);
        carModel = (EditText) findViewById(R.id.carModelInsuranceForm);
        carYear = (EditText) findViewById(R.id.carYearInsuranceForm);

        DriverLicence = (Button)findViewById(R.id.tlcInsuranceDriverLicense);
        SSCard = (Button)findViewById(R.id.tlcInsuranceSScard);
        BaseLetter = (Button)findViewById(R.id.tlcInsuranceBaseLetter);
        UtilityBills = (Button)findViewById(R.id.tlcInsuranceUtillityBills);
        MVTitle = (Button)findViewById(R.id.tlcInsuranceMvTitle);
        SixhrDrivingClass = (Button)findViewById(R.id.tlcInsurancesixhrclass);
        SHlPermitCopy = (Button)findViewById(R.id.tlcInsuranceshlPermit);
        SHLPermitReceipt = (Button)findViewById(R.id.tlcInsuranceShlReceipt);
        IsPermitOwned = (Button)findViewById(R.id.tlcInsuranceowned);
        CertificateCorporation = (Button)findViewById(R.id.certificatincorporation);
        FillingRecipt = (Button)findViewById(R.id.fillingrecipt);


        DriverLicenceImageView = (ImageView) findViewById(R.id.tlcInsuranceDriverLicenseImageView);
        SSCardImageView = (ImageView)findViewById(R.id.tlcInsuranceSScardImageView);
        BaseLetterImageView = (ImageView)findViewById(R.id.tlcInsuranceBaseLetterImageView);
        UtilityBillsImageView = (ImageView)findViewById(R.id.tlcInsuranceUtillityBillsImageView);
        MVTitleImageView = (ImageView)findViewById(R.id.tlcInsuranceMvTitleImageView);
        SixhrDrivingClassImageView = (ImageView)findViewById(R.id.tlcInsurancesixhrclassImageView);
        SHlPermitCopyImageView = (ImageView)findViewById(R.id.tlcInsuranceshlPermitImageView);
        SHLPermitReceiptImageView = (ImageView)findViewById(R.id.tlcInsuranceShlReceiptImageView);
        IsPermitOwnedImageView =(ImageView)findViewById(R.id.tlcInsuranceownedImageView);
        CertificateCorporationImageView =(ImageView)findViewById(R.id.certificatincorporationImageView);
        FillingReciptImageView =(ImageView)findViewById(R.id.fillingreciptImageView);

        OwnedYes = (RadioButton) findViewById(R.id.ispermitownedyes);
        OwnedNo = (RadioButton) findViewById(R.id.ispermitownedno);
        CabPolicyYes = (RadioButton) findViewById(R.id.cabpolicyyes);
        CabpolicyNo = (RadioButton) findViewById(R.id.cabpolicyno);
        NeedFullCoverageYes= (RadioButton) findViewById(R.id.needfullcoverageyes);
        NeedFullCoverageNo = (RadioButton) findViewById(R.id.needfullcoverageno);

        IsPermitRadioGroup = (RadioGroup) findViewById(R.id.ispermitownedgroup);
        IsCabPolicyRadioGroup = (RadioGroup) findViewById(R.id.cabpolicyradiogroup);
        NeedFullCoverage = (RadioGroup) findViewById(R.id.needfullcoverageradiogroup);

        carType = (Spinner) findViewById(R.id.spinnerInsuranceForm);
//        TextView SpinnerValidaio = (TextView) carType.getSelectedView();
//        SpinnerValidaio.setError("Please Select Car Type");



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



    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(TlcInsurance.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(TlcInsurance.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Globals.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }

    private void checkReadExternalStoragePermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(TlcInsurance.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(TlcInsurance.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Globals.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Phone Storage"))
                        galleryIntent();

                    Log.d("tag", "Permission Granted Write External ");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted Write External ");
                }
                return;
            }


            case Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                    Log.d("tag", "Permission Granted Read External");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted Read External ");
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //take iamge from camera and gallery

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Phone Storage",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(TlcInsurance.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(TlcInsurance.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Phone Storage")) {
                    userChoosenTask ="Choose from Phone Storage";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        if(btnClicked == licenseButton) {
            Log.d(TAG, "galleryIntent: License Button");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_LICENSE);
//            Intent gallaryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(gallaryIntent, SELECT_FILE_LICENSE);
        }
        else if(btnClicked == ssCardButton) {
            Log.d(TAG, "galleryIntent: SSCARD Button");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_SSCARD);
        }
        else if(btnClicked == baseLetterButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_BASELETTER);
        }
        else if(btnClicked == utilityBillsbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_UTILITYBILLS);
        }
        else if(btnClicked == mvTitlebutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_MVTITLE);
        }
        else if(btnClicked == sixhrdrivingbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_sixhrdriving);
        }
        else if(btnClicked == shlPermitbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_SHLPERMIT);
        }
        else if(btnClicked == shlpermitreceiptbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_SHLRECEIPT);
        }
        else if(btnClicked == ispermitownedbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ISPERMITOWNED);
        }
        else if(btnClicked == certificatebutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_certificate);
        }
        else if(btnClicked == fillingbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_filling);
        }
    }

    private void cameraIntent()
    {
        if(btnClicked == licenseButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_LICENSE);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_LICENSE);

        }
        else if(btnClicked == ssCardButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_SSCARD);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_SSCARD);
        }
        else if(btnClicked == baseLetterButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_BASELETTER);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_BASELETTER);
        }
        else if(btnClicked == utilityBillsbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_UTILITYBILLS);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_UTILITYBILLS);
        }
        else if(btnClicked == mvTitlebutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_MVTITLE);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_MVTITLE);
        }
        else if(btnClicked == sixhrdrivingbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_sixhrdriving);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_sixhrdriving);
        }
        else if(btnClicked == shlPermitbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_SHLPERMIT);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_SHLPERMIT);
        }
        else if(btnClicked == shlpermitreceiptbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_SHLRECEIPT);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_SHLRECEIPT);
        }
        else if(btnClicked == ispermitownedbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ISPERMITOWNED);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ISPERMITOWNED);
        }
        else if(btnClicked == certificatebutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_certificate);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_certificate);
        }
        else if(btnClicked == fillingbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_filling);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_filling);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_LICENSE)
                onSelectFromGalleryResult(data,SELECT_FILE_LICENSE);
            else if (requestCode == SELECT_FILE_SSCARD)
                onSelectFromGalleryResult(data,SELECT_FILE_SSCARD);
            else if (requestCode == SELECT_FILE_BASELETTER)
                onSelectFromGalleryResult(data,SELECT_FILE_BASELETTER);
            else if (requestCode == SELECT_FILE_UTILITYBILLS)
                onSelectFromGalleryResult(data,SELECT_FILE_UTILITYBILLS);
            else if (requestCode == SELECT_FILE_MVTITLE)
                onSelectFromGalleryResult(data,SELECT_FILE_MVTITLE);
            else if (requestCode == SELECT_FILE_sixhrdriving)
                onSelectFromGalleryResult(data,SELECT_FILE_sixhrdriving);
            else if (requestCode == SELECT_FILE_SHLPERMIT)
                onSelectFromGalleryResult(data,SELECT_FILE_SHLPERMIT);
            else if (requestCode == SELECT_FILE_SHLRECEIPT)
                onSelectFromGalleryResult(data,SELECT_FILE_SHLRECEIPT);
            else if (requestCode == SELECT_FILE_ISPERMITOWNED)
                onSelectFromGalleryResult(data,SELECT_FILE_ISPERMITOWNED);
            else if (requestCode == SELECT_FILE_certificate)
                onSelectFromGalleryResult(data,SELECT_FILE_certificate);
            else if (requestCode == SELECT_FILE_filling)
                onSelectFromGalleryResult(data,SELECT_FILE_filling);
            else if (requestCode == REQUEST_CAMERA_LICENSE)
                onCaptureImageResult(data , REQUEST_CAMERA_LICENSE);
            else if (requestCode == REQUEST_CAMERA_SSCARD)
                onCaptureImageResult(data , REQUEST_CAMERA_SSCARD);
            else if (requestCode == REQUEST_CAMERA_BASELETTER)
                  onCaptureImageResult(data , REQUEST_CAMERA_BASELETTER);
            else if (requestCode == REQUEST_CAMERA_UTILITYBILLS)
                onCaptureImageResult(data , REQUEST_CAMERA_UTILITYBILLS);
            else if (requestCode == REQUEST_CAMERA_MVTITLE)
                onCaptureImageResult(data , REQUEST_CAMERA_MVTITLE);
            else if (requestCode == REQUEST_CAMERA_sixhrdriving)
                onCaptureImageResult(data , REQUEST_CAMERA_sixhrdriving);
            else if (requestCode == REQUEST_CAMERA_SHLPERMIT)
                onCaptureImageResult(data , REQUEST_CAMERA_SHLPERMIT);
            else if (requestCode == REQUEST_CAMERA_SHLRECEIPT)
                onCaptureImageResult(data , REQUEST_CAMERA_SHLRECEIPT);
            else if (requestCode == REQUEST_CAMERA_ISPERMITOWNED)
                onCaptureImageResult(data , REQUEST_CAMERA_ISPERMITOWNED);
            else if (requestCode == REQUEST_CAMERA_certificate)
                onCaptureImageResult(data , REQUEST_CAMERA_certificate);
            else if (requestCode == REQUEST_CAMERA_filling)
                onCaptureImageResult(data , REQUEST_CAMERA_filling);
        }
    }


    private void onCaptureImageResult(Intent data , int requestCode) {

//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        try {
            thumbnail = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
//        File myDir = new File(root + "/TLC");
//        myDir.mkdirs();
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        fname = "Image-" + n + ".jpg";
//        File file = new File(myDir, fname);
//        if (file.exists())
//            file.delete();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        // Tell the media scanner about the new file so that it is
//        // immediately available to the user.
//        MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });


//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if(requestCode == REQUEST_CAMERA_LICENSE) {

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DriverLicenceImageView.setVisibility(View.VISIBLE);
            DriverLicenceImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();


//            DriverLicenceImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+DriverLicenceImagepath);
//            bitmap1 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            DriverLicenceImageView.setImageBitmap(thumbnail);
//            DriverLicenceImageView.setVisibility(View.VISIBLE);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp1 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_SSCARD) {

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SSCardImageView.setVisibility(View.VISIBLE);
            SSCardImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();


//            SSCardImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+SSCardImagepath);

//            bitmap2 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            SSCardImageView.setVisibility(View.VISIBLE);
//
//            SSCardImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp2 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_BASELETTER) {

            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BaseLetterImageView.setVisibility(View.VISIBLE);
            BaseLetterImageView.setImageBitmap(bitmap3);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();


//            BaseLetterImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+BaseLetterImagepath);

//            bitmap3 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            BaseLetterImageView.setVisibility(View.VISIBLE);
//
//            BaseLetterImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp3 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_UTILITYBILLS) {

            try {
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UtilityBillsImageView.setVisibility(View.VISIBLE);
            UtilityBillsImageView.setImageBitmap(bitmap4);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();


//            UtilityBillsImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+UtilityBillsImagepath);

//            bitmap4 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap4.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            UtilityBillsImageView.setVisibility(View.VISIBLE);
//
//            UtilityBillsImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp4 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_MVTITLE) {

            try {
                bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MVTitleImageView.setVisibility(View.VISIBLE);
            MVTitleImageView.setImageBitmap(bitmap5);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp5 = tsLong.toString();



//            MVTitleImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+MVTitleImagepath);

//            bitmap5 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap5.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            MVTitleImageView.setVisibility(View.VISIBLE);
//
//            MVTitleImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp5 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_sixhrdriving) {


            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SixhrDrivingClassImageView.setVisibility(View.VISIBLE);
            SixhrDrivingClassImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


//            SixhrDrivingClassImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+SixhrDrivingClassImagepath);

//            bitmap6 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap6.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            SixhrDrivingClassImageView.setVisibility(View.VISIBLE);
//
//            SixhrDrivingClassImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp6 = tsLong.toString();
        }


        else if(requestCode == REQUEST_CAMERA_ISPERMITOWNED) {


            try {
                bitmap7 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            IsPermitOwnedImageView.setVisibility(View.VISIBLE);
            IsPermitOwnedImageView.setImageBitmap(bitmap7);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp7 = tsLong.toString();

//            IsPermitOwnedImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+IsPermitOwnedImagepath);


//            bitmap9 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap9.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            IsPermitOwnedImageView.setVisibility(View.VISIBLE);
//
//            IsPermitOwnedImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_certificate) {


            try {
                bitmap8 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CertificateCorporationImageView.setVisibility(View.VISIBLE);
            CertificateCorporationImageView.setImageBitmap(bitmap8);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp8 = tsLong.toString();


//            CertificateCorporationImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+CertificateCorporationImagepath);


//            bitmap10 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap10.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            CertificateCorporationImageView.setVisibility(View.VISIBLE);
//            CertificateCorporationImageView.setImageBitmap(thumbnail);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
        }
        else if(requestCode == REQUEST_CAMERA_filling) {


            try {
                bitmap9 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FillingReciptImageView.setVisibility(View.VISIBLE);
            FillingReciptImageView.setImageBitmap(bitmap9);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp9 = tsLong.toString();


//            FillingReciptImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+FillingReciptImagePath);

//            bitmap11 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap11.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            FillingReciptImageView.setImageBitmap(thumbnail);
//            FillingReciptImageView.setVisibility(View.VISIBLE);

//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data , int requestCode) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == SELECT_FILE_LICENSE) {
            //Log.d(TAG, "onSelectFromGalleryResult: license file");

//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp1 = tsLong.toString();
//            try {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            DriverLicenceImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + DriverLicenceImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                DriverLicenceImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                DriverLicenceImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }



                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
//            DriverLicenceImageView.setVisibility(View.VISIBLE);
//
//            DriverLicenceImageView.setImageBitmap(bm);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //DriverLicenceImageView.setImageBitmap(bitmap);
        }
            else if(requestCode == SELECT_FILE_SSCARD) {

//            Log.d(TAG, "onSelectFromGalleryResult:  sscard ");
//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//               timestamp2 = tsLong.toString();
//               timestamp2 = tsLong.toString();
//            try {


//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            SSCardImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + SSCardImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                SSCardImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                SSCardImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }


                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
               // bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            SSCardImageView.setVisibility(View.VISIBLE);

            SSCardImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //SSCardImageView.setImageBitmap(bitmapstoreimg);
        }
        else if(requestCode == SELECT_FILE_BASELETTER) {


//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp3 = tsLong.toString();
//            try {
//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            BaseLetterImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + BaseLetterImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();
            try {
                BaseLetterImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                BaseLetterImageView.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }


                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
               // bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            BaseLetterImageView.setVisibility(View.VISIBLE);

            BaseLetterImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            //BaseLetterImageView.setImageBitmap(bitmapstoreimg);
        }
        else if(requestCode == SELECT_FILE_UTILITYBILLS) {

//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp4 = tsLong.toString();
//            try {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            UtilityBillsImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + UtilityBillsImagepath );


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();
            try {
                UtilityBillsImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                UtilityBillsImageView.setImageBitmap(bitmap4);
            } catch (IOException e) {
                e.printStackTrace();
            }

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                //Log.d(TAG, "onSelectFromGalleryResult: abc"+bitmap4);
            UtilityBillsImageView.setVisibility(View.VISIBLE);

            UtilityBillsImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            //UtilityBillsImageView.setImageBitmap(bitmapstoreimg);
        }
        else if(requestCode == SELECT_FILE_MVTITLE) {

//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp5 = tsLong.toString();
//            try {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            MVTitleImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + MVTitleImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp5 = tsLong.toString();
            try {
                MVTitleImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                MVTitleImageView.setImageBitmap(bitmap5);
            } catch (IOException e) {
                e.printStackTrace();
            }



                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            MVTitleImageView.setVisibility(View.VISIBLE);

            MVTitleImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

           // MVTitleImageView.setImageBitmap(bitmap5);
        }
        else if(requestCode == SELECT_FILE_sixhrdriving) {
//
//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp6 = tsLong.toString();
//            try {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            SixhrDrivingClassImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + SixhrDrivingClassImagepath );


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                SixhrDrivingClassImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                SixhrDrivingClassImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }


                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
               // bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
//            SixhrDrivingClassImageView.setVisibility(View.VISIBLE);
//
//            SixhrDrivingClassImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            // MVTitleImageView.setImageBitmap(bitmap5);
        }

        else if(requestCode == SELECT_FILE_ISPERMITOWNED) {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            IsPermitOwnedImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + IsPermitOwnedImagepath );

//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
//            try {

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp7 = tsLong.toString();
            try {
                IsPermitOwnedImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap7 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                IsPermitOwnedImageView.setImageBitmap(bitmap7);
            } catch (IOException e) {
                e.printStackTrace();
            }


                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap9 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
//            IsPermitOwnedImageView.setVisibility(View.VISIBLE);
//
//            IsPermitOwnedImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            // /IsPermitOwnedImageView.setImageBitmap(bitmapstoreimg);
        }
        else if(requestCode == SELECT_FILE_certificate) {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            CertificateCorporationImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + CertificateCorporationImagepath );
//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
//            try {


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp8 = tsLong.toString();
            try {
                CertificateCorporationImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap8 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                CertificateCorporationImageView.setImageBitmap(bitmap8);
            } catch (IOException e) {
                e.printStackTrace();
            }

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap10 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
//            CertificateCorporationImageView.setVisibility(View.VISIBLE);
//
//            CertificateCorporationImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            // /IsPermitOwnedImageView.setImageBitmap(bitmapstoreimg);
        }
        else if(requestCode == SELECT_FILE_filling) {

//            String gallarypath = GetFilePathFromDevice.getPath(TlcInsurance.this, data.getData());
//            Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//            String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result right" + abc);
//            String [] str = gallarypath.split(abc);
//            String firstImagepath = str[0];
//            Log.d("ReportAccidentForm", "result left" + firstImagepath);
//            String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result back" + pathback);
//            String subfinal = pathback + abc;
//            Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//            String [] left = firstImagepath.split(pathback);
//            String left1 = left[0];
//            Log.d("ReportAccidentForm", "result left1" + left1);
//            String finalpath = left1.substring(left1.lastIndexOf("/") );
//            Log.d("ReportAccidentForm", "result final" + finalpath);
//            FillingReciptImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + FillingReciptImagePath );

//            Uri image = data.getData();
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp8 = tsLong.toString();
//            try {


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp9 = tsLong.toString();
            try {
                FillingReciptImageView.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap9 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                FillingReciptImageView.setImageBitmap(bitmap9);
            } catch (IOException e) {
                e.printStackTrace();
            }

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                //bitmap11 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
//            FillingReciptImageView.setVisibility(View.VISIBLE);
//
//            FillingReciptImageView.setImageBitmap(bm);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            // /IsPermitOwnedImageView.setImageBitmap(bitmapstoreimg);
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public class uploadData extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(TlcInsurance.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/insurance_tlc.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);


                handleImageWithAysncTask();

                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("fullname", strfullname)
                        .appendQueryParameter("address", straddress)
                        .appendQueryParameter("zipcode", strzipcode)
                        .appendQueryParameter("dob", strdob)
                        .appendQueryParameter("driverLicense", strdriverLicense)
                        .appendQueryParameter("tlcLicense", strtlcLicense)
                        .appendQueryParameter("phoneNum", strphoneNum)
                        .appendQueryParameter("email", stremail)
                        .appendQueryParameter("vinnum", strvinnum)
                        .appendQueryParameter("socialsecurtiynumber", strsocialSecurtiyNumber)
                        .appendQueryParameter("addcomment", strAddComment)
                        .appendQueryParameter("typeofcar", strcarType)
                        .appendQueryParameter("einnum", streinnum)
                        .appendQueryParameter("ownername", strownername)
                        .appendQueryParameter("carmake", strcarMake)
                        .appendQueryParameter("carmodel", strcarModel)
                        .appendQueryParameter("caryear", strcarYear)
                        .appendQueryParameter("permit", getTExtPermitRadioGroup)
                        .appendQueryParameter("cab", getTextCabPolicyRadioGroup)
                        .appendQueryParameter("needfullcoverage", getTExtNeedFullCoverageis)
                        .appendQueryParameter("taxiD", strTaxID)
                        .appendQueryParameter("driverLicenseimg", uploadDriverLicense)
                        .appendQueryParameter("timestampdriverLicense", timestamp1)
                        .appendQueryParameter("sscard", uploadSSCard)
                        .appendQueryParameter("timestampsscard", timestamp2)
                        .appendQueryParameter("baseletter", uploadBaseLetter)
                        .appendQueryParameter("timestampbaseletter", timestamp3)
                        .appendQueryParameter("utilitybills", uploadUtilityBills)
                        .appendQueryParameter("timestamputilitybills", timestamp4)
                        .appendQueryParameter("mvtitle", uploadMVTitle)
                        .appendQueryParameter("timestampmvtitle", timestamp5)
                        .appendQueryParameter("sixhr", uploadSixHr)
                        .appendQueryParameter("timestampsixhr", timestamp6)
                        .appendQueryParameter("powerofattorney", uploadpowerofAttorney)
                        .appendQueryParameter("timestamppowerofattorney", timestamp7)
                        .appendQueryParameter("certificateincorporation", uploadCertificateIncorporation)
                        .appendQueryParameter("timestampcertificateincorporation", timestamp8)
                        .appendQueryParameter("fillingreceipt", uploadFillingReceipt)
                        .appendQueryParameter("timestampfillingreceipt", timestamp9)




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
//                Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(TlcInsurance.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(TlcInsurance.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(TlcInsurance.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }












    public class UploadImage extends AsyncTask<Bitmap,Void,String>{

            //String timeStamp = null;
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

//            public UploadImage(String timestamp )
//            {
//                this.timeStamp = timestamp;
//
//            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TlcInsurance.this, "Uploading Image", "Please wait...",true,true);
                Log.d("bitmap one", "onPreExecute");
            }
            @Override
            protected String doInBackground(Bitmap... params) {
               // Bitmap bitmap = params[0];
                String uploadImagedriverlicecnce = getStringImage(bitmap1);
                String uploadImagesscard = getStringImage(bitmap2);
                String uploadImagebaseletter = getStringImage(bitmap3);
                String uploadImageutilitybills = getStringImage(bitmap4);
                String uploadImagemvtitle = getStringImage(bitmap5);
                String uploadImageshlpermit = getStringImage(bitmap6);
                String uploadImagepermitrecipt = getStringImage(bitmap7);
                String uploadImagepowerattorny = getStringImage(bitmap8);

                HashMap<String,String> data = new HashMap<>();
                data.put("driverlicecnce", uploadImagedriverlicecnce);
                data.put("sscard", uploadImagesscard);
                data.put("baseletter", uploadImagebaseletter);
                data.put("utilitybills", uploadImageutilitybills);
                data.put("mvtitle", uploadImagemvtitle);
                data.put("shlpermit", uploadImageshlpermit);
                data.put("permitrecipt", uploadImagepermitrecipt);
                data.put("powerattorny", uploadImagepowerattorny);


                data.put("timestampDriverlicecnce",timestamp1);
                data.put("timestampSscard",timestamp2);
                data.put("timestampBaseletter",timestamp3);
                data.put("timestampUtilitybills",timestamp4);
                data.put("timestampMvtitle",timestamp5);
                data.put("timestampShlpermit",timestamp6);
                data.put("timestampPermitrecipt",timestamp7);
                data.put("timestampPowerattorny",timestamp8);


                String result = rh.sendPostRequest(SERVER,data);
                Log.d("bitmap one", "doInBackground: ");
                return result;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Log.d("bitmap one", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                UploadImageone ui2 = new UploadImageone("IMG_"+timestamp);
//                ui2.execute(bitmap2);

            }

        }








    //this asynctask use to get data from registration
    public class autofill extends AsyncTask<String , Void ,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(TlcInsurance.this);
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



                Log.d("tag_name" , "name" +tag_name);
                fullname.setText(tag_name);
                address.setText(tag_address);
                Zipcode.setText(tag_zipcode);
                dob.setText(tag_dob);
                driverLicense.setText(tag_driverlicense);
                tlcLicense.setText(tag_tlclicense);
                phoneNum.setText(tag_phonenum);
                socialSecurtiyNumber.setText(tag_ssn);
                email.setText(tag_email);



                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Data Loaded SuccessFully",Toast.LENGTH_SHORT).show();

                //Login.this.finish();
            }else if (Response.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Data Not Loaded ",Toast.LENGTH_SHORT).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(TlcInsurance.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



    public class tlcInsuranceAsynctask extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(TlcInsurance.this);
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
                        .appendQueryParameter("fullname", strfullname)
                        .appendQueryParameter("address", straddress)
                        .appendQueryParameter("zipcode", strzipcode)
                        .appendQueryParameter("dob", strdob)
                        .appendQueryParameter("driverlicense", strdriverLicense)
                        .appendQueryParameter("phonenumber", strtlcLicense)
                        .appendQueryParameter("socialsecuritynumber", strphoneNum)
                        .appendQueryParameter("socialsecuritynumber", stremail)
                        .appendQueryParameter("addcomment", strvinnum)
                        .appendQueryParameter("ddc", strsocialSecurtiyNumber )
                        .appendQueryParameter("issuedate", strAddComment )
                        .appendQueryParameter("expirydate", streinnum )
                        .appendQueryParameter("priorinsurance", strownername )
                        .appendQueryParameter("nameofcompany", strcarMake )
                        .appendQueryParameter("insurancelenght", strcarModel )
                        .appendQueryParameter("maritalstatus", strcarYear )
                        .appendQueryParameter("spousename", strTaxID )





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

            if(result.equalsIgnoreCase("true"))
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
                Toast.makeText(TlcInsurance.this, "Something wrong", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(TlcInsurance.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }




    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(TlcInsurance.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {


                ImagePaths();
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
            Intent intent = new Intent(TlcInsurance.this , ThankYouScreen.class);
            startActivity(intent);
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


            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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
                                    TlcInsurance.this.finish();
                                }
                            }
                    );
            android.support.v7.app.AlertDialog alert = builder.create();
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
    public void ImagePaths()
    {
        if(CertificateCorporationImageView.getDrawable() == null
                && FillingReciptImageView.getDrawable() == null
                && IsPermitOwnedImageView.getDrawable() == null) {

            try {
                sender.addAttachment(Environment.getExternalStorageDirectory() + DriverLicenceImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SSCardImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + BaseLetterImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + UtilityBillsImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + MVTitleImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SixhrDrivingClassImagepath);

                Log.d("" , "Images if");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(IsPermitOwnedImageView.getDrawable() == null)
        {
            try {
                sender.addAttachment(Environment.getExternalStorageDirectory() + DriverLicenceImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SSCardImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + BaseLetterImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + UtilityBillsImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + MVTitleImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SixhrDrivingClassImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + CertificateCorporationImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + FillingReciptImagePath);

                Log.d("" , "Images else if 1st");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(CertificateCorporationImageView.getDrawable() == null
                && FillingReciptImageView.getDrawable() == null)
        {
            try {
                sender.addAttachment(Environment.getExternalStorageDirectory() + DriverLicenceImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SSCardImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + BaseLetterImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + UtilityBillsImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + MVTitleImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SixhrDrivingClassImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + IsPermitOwnedImagepath);

                Log.d("" , "Images else if 2nd");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            try {
                sender.addAttachment(Environment.getExternalStorageDirectory() + DriverLicenceImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SSCardImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + BaseLetterImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + UtilityBillsImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + MVTitleImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + SixhrDrivingClassImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + IsPermitOwnedImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + CertificateCorporationImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory() + FillingReciptImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


//    public void handleImageWithAysncTask()
//    {
//        if(IsPermitOwnedImageView.getDrawable() == null)
//        {
//            new uploadDatawithoutPowerAttorney().execute();
//        }
//        else if(CertificateCorporationImageView.getDrawable() == null && FillingReciptImageView.getDrawable() == null)
//        {
//            new uploadDatawithoutcertificateandfilling().execute();
//        }
//        else if(IsPermitOwnedImageView.getDrawable() == null && CertificateCorporationImageView.getDrawable() == null
//                && FillingReciptImageView.getDrawable() == null)
//        {
//            new uploadDatawithoutlastthreepic().execute();
//        }
//        else
//        {
//           new uploadData().execute();
//        }
//    }


        public void handleImageWithAysncTask()
      {
          if(IsPermitOwnedImageView.getDrawable() == null && CertificateCorporationImageView.getDrawable() == null
              && FillingReciptImageView.getDrawable() == null)
        {

            uploadDriverLicense = getStringImage(bitmap1);
            uploadSSCard = getStringImage(bitmap2);
            uploadBaseLetter = getStringImage(bitmap3);
            uploadUtilityBills = getStringImage(bitmap4);
            uploadMVTitle = getStringImage(bitmap5);
            uploadSixHr = getStringImage(bitmap6);

            Log.d("if_else","3");


        }
        else if(CertificateCorporationImageView.getDrawable() == null && FillingReciptImageView.getDrawable() == null)
        {
            uploadDriverLicense = getStringImage(bitmap1);
            uploadSSCard = getStringImage(bitmap2);
            uploadBaseLetter = getStringImage(bitmap3);
            uploadUtilityBills = getStringImage(bitmap4);
            uploadMVTitle = getStringImage(bitmap5);
            uploadSixHr = getStringImage(bitmap6);
            uploadpowerofAttorney = getStringImage(bitmap7);

            Log.d("if_else","2");


        }
         else if(IsPermitOwnedImageView.getDrawable() == null)

          {
            uploadDriverLicense = getStringImage(bitmap1);
            uploadSSCard = getStringImage(bitmap2);
            uploadBaseLetter = getStringImage(bitmap3);
            uploadUtilityBills = getStringImage(bitmap4);
            uploadMVTitle = getStringImage(bitmap5);
            uploadSixHr = getStringImage(bitmap6);
            uploadCertificateIncorporation = getStringImage(bitmap8);
            uploadFillingReceipt = getStringImage(bitmap9);

            Log.d("if_else","1");


          }
        else
        {
            uploadDriverLicense = getStringImage(bitmap1);
            uploadSSCard = getStringImage(bitmap2);
            uploadBaseLetter = getStringImage(bitmap3);
            uploadUtilityBills = getStringImage(bitmap4);
            uploadMVTitle = getStringImage(bitmap5);
            uploadSixHr = getStringImage(bitmap6);
            uploadpowerofAttorney = getStringImage(bitmap7);
            uploadCertificateIncorporation = getStringImage(bitmap8);
            uploadFillingReceipt = getStringImage(bitmap9);

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
                Intent intent = new Intent(TlcInsurance.this,ThankYouScreen.class);
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
