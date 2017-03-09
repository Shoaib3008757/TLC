package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons;

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

/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ranglerz.tlc.tlc.GetFilePathFromDevice;*/
import com.ranglerz.tlc.tlc.Globals;
import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.RequestHandler;
import com.ranglerz.tlc.tlc.SelectOptionNavigation;
import com.ranglerz.tlc.tlc.ThankYouScreen;
import com.ranglerz.tlc.tlc.Utility;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.EmailClasses.GMailSender;

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

public class NewSummon extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    public static int btnClicked=0;
    public static int summonimg = 1;
    public static int complianceimg = 2;

    private int REQUEST_CAMERA_summonimg = 11;
    private int REQUEST_CAMERA_complianceimg = 12;

    public int SELECT_FILE_summonimgn = 21;
    public int SELECT_FILE_complianceimg = 22;


    EditText summonno, summondate , TlcLicenseHack , PlateNumber , ShlPermitHolder , BaseNumber
            , DiamondNo , MedallionNo , MedallionOwner , FleetMedallion , ShlPermit , HearingLocation , addcomment;
    Button submit , SummonPicture , CompliancePicture ;
    Spinner spinnerSummonType , SpinnerBaseSummon;
    ImageView takePictureNewSummonImageView , takePictureComplianceImageView;
    RadioGroup seizedCarSelectionNewSummon , AppearanceRequired;
    RadioButton seizedRadioButton , AppearanceButton;
    int intseizedCar , intAppearance;
    String Imagepath , fname;

    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    Uri imageUri;
    Bitmap thumbnail;
    Calendar myCalendar = Calendar.getInstance();



    RadioButton carSeizedYesNewSummon,carSeizedNoNewSummon;
    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "New Summon";
    public String message;
    String strspinnerSummonType , strSpinnerBaseSummon ,  strsummonno ,strsummondate ,strTlcLicenseHack ,strPlateNumber ,strShlPermitHolder ,strBaseNumber ,strDiamondNo ,strMedallionNo ,
            strMedallionOwner ,strFleetMedallion ,strShlPermit , strHearingLocation , getTextSeized , getTextAppearance , straddcoment;
    private String[] stringSummonType = { "Select Your Summon", "Driver Summon", "Base Summon", "SHL Summon", "FHV Summon" , "Medallion/Yellow Cab" , "Corporation" };
    private String[] baseSummons = { "Select Your Base Car" , "Black Car", "Car Service", "Luxury" };
    Bitmap bitmap1 ,bitmap2;
    String timestamp1 , timestamp2;
    public String SERVER = "http://www.tlcapp.nyc/admin/services/report_accident.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_summon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();
        setupIds();


//        // permission for Marshmallow
//        checkWriteExternalPermission();
//        checkReadExternalStoragePermission();
//
//        getRespondentSignature.setOnClickListener(new View.OnClickListener() {
    //            @Override
//            public void onClick(View v) {
//                showSignatureDialog();
//            }
//        });


        // setup Spinner id
        spinnerSummonType = (Spinner) findViewById(R.id.spinnerNewSummon);
        SpinnerBaseSummon = (Spinner) findViewById(R.id.basesummoncar);


        SpinnerBaseSummon.setVisibility(View.GONE);
        summonno.setVisibility(View.GONE);
        summondate.setVisibility(View.GONE);
        TlcLicenseHack.setVisibility(View.GONE);
        PlateNumber.setVisibility(View.GONE);
        ShlPermitHolder.setVisibility(View.GONE);
        BaseNumber.setVisibility(View.GONE);
        DiamondNo.setVisibility(View.GONE);
        MedallionNo.setVisibility(View.GONE);
        MedallionOwner.setVisibility(View.GONE);
        FleetMedallion.setVisibility(View.GONE);
        ShlPermit.setVisibility(View.GONE);
        HearingLocation.setVisibility(View.GONE);
        takePictureNewSummonImageView.setVisibility(View.GONE);
        takePictureComplianceImageView.setVisibility(View.GONE);
        addcomment.setVisibility(View.GONE);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, stringSummonType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSummonType.setAdapter(adapter);

        ArrayAdapter<String> adapterbasesummon = new ArrayAdapter<String>(this, R.layout.spinner_item, baseSummons);
        adapterbasesummon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerBaseSummon.setAdapter(adapterbasesummon);




        spinnerSummonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch (arg2) {
                    case 0:
                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.GONE);
                        summondate.setVisibility(View.GONE);
                        TlcLicenseHack.setVisibility(View.GONE);
                        PlateNumber.setVisibility(View.GONE);
                        ShlPermitHolder.setVisibility(View.GONE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.GONE);
                        MedallionNo.setVisibility(View.GONE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.GONE);
                        HearingLocation.setVisibility(View.GONE);
                        addcomment.setVisibility(View.GONE);



                        break;
                    case 1:

                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.VISIBLE);
                        ShlPermitHolder.setVisibility(View.GONE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.GONE);
                        MedallionNo.setVisibility(View.GONE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.GONE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);






                        break;
                    case 2:
                        SpinnerBaseSummon.setVisibility(View.VISIBLE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.VISIBLE);
                        ShlPermitHolder.setVisibility(View.VISIBLE);
                        BaseNumber.setVisibility(View.VISIBLE);
                        DiamondNo.setVisibility(View.VISIBLE);
                        MedallionNo.setVisibility(View.GONE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.GONE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);


                        break;
                    case 3:

                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.VISIBLE);
                        ShlPermitHolder.setVisibility(View.VISIBLE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.VISIBLE);
                        MedallionNo.setVisibility(View.GONE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.VISIBLE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);

                        break;
                    case 4:

                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.VISIBLE);
                        ShlPermitHolder.setVisibility(View.GONE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.GONE);
                        MedallionNo.setVisibility(View.GONE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.GONE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);

                        break;

                    case 5:
                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.GONE);
                        ShlPermitHolder.setVisibility(View.GONE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.GONE);
                        MedallionNo.setVisibility(View.VISIBLE);
                        MedallionOwner.setVisibility(View.VISIBLE);
                        FleetMedallion.setVisibility(View.VISIBLE);
                        ShlPermit.setVisibility(View.GONE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);

                        break;

                    case 6:
                        SpinnerBaseSummon.setVisibility(View.GONE);
                        summonno.setVisibility(View.VISIBLE);
                        summondate.setVisibility(View.VISIBLE);
                        TlcLicenseHack.setVisibility(View.VISIBLE);
                        PlateNumber.setVisibility(View.VISIBLE);
                        ShlPermitHolder.setVisibility(View.GONE);
                        BaseNumber.setVisibility(View.GONE);
                        DiamondNo.setVisibility(View.GONE);
                        MedallionNo.setVisibility(View.VISIBLE);
                        MedallionOwner.setVisibility(View.GONE);
                        FleetMedallion.setVisibility(View.GONE);
                        ShlPermit.setVisibility(View.VISIBLE);
                        HearingLocation.setVisibility(View.VISIBLE);
                        addcomment.setVisibility(View.VISIBLE);

                        break;
                    default:

                        break;

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

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


        summondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewSummon.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        AppearanceRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.apperanceradiogroupyes){

                } else {

                }
            }
        });

        seizedCarSelectionNewSummon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.carSeizedYesNewSummon){

                } else {

                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strspinnerSummonType = spinnerSummonType.getSelectedItem().toString();
                strSpinnerBaseSummon = SpinnerBaseSummon.getSelectedItem().toString();
                strsummonno = summonno.getText().toString();
                strsummondate = summondate.getText().toString();
                strTlcLicenseHack = TlcLicenseHack.getText().toString();
                strPlateNumber = PlateNumber.getText().toString();
                strShlPermitHolder = ShlPermitHolder.getText().toString();
                strBaseNumber = BaseNumber.getText().toString();
                strDiamondNo = DiamondNo.getText().toString();
                strMedallionNo = MedallionNo.getText().toString();
                strMedallionOwner = MedallionOwner.getText().toString();
                strFleetMedallion = FleetMedallion.getText().toString();
                strShlPermit = ShlPermit.getText().toString();
                strHearingLocation = HearingLocation.getText().toString();
                straddcoment = addcomment.getText().toString();
                intseizedCar = seizedCarSelectionNewSummon.getCheckedRadioButtonId();
                intAppearance = AppearanceRequired.getCheckedRadioButtonId();
                seizedRadioButton = (RadioButton) findViewById(intseizedCar);
                AppearanceButton = (RadioButton) findViewById(intAppearance);



                if(strspinnerSummonType.equals("Select Your Summon"))
                {
                    Toast.makeText(NewSummon.this, "Please Select Your Summons", Toast.LENGTH_SHORT).show();
                }
                else if(strspinnerSummonType.equals("Driver Summon"))
                {

                    if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strPlateNumber.equals(""))
                    {
                        PlateNumber.setError("Please Enter Plate Number");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        sharedPreferences = getSharedPreferences("getuserid" , 2);
                        String email = sharedPreferences.getString(emailkey , null);
                        String Userid = sharedPreferences.getString(userID , null);

                        Log.d("" , "email " +email);
                        Log.d("" , "userid " +Userid);



                            message = "User Email: " +email + "\n   \nSelected Summon: "  + strspinnerSummonType + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +   "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                            new driversummon().execute();
//                            UploadImage ui1 = new UploadImage();
//                            ui1.execute(bitmap1);

                        Log.d("abc", "onClick: 1");
                    }

                }
                else if(strspinnerSummonType.equals("Base Summon"))
                {
                    if(strSpinnerBaseSummon.equals("Select Your Base Car"))
                    {
                    Toast.makeText(NewSummon.this, "Please Select Your Base Car", Toast.LENGTH_SHORT).show();
                    }
                    else if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strPlateNumber.equals(""))
                    {
                        PlateNumber.setError("Please Enter Plate Number");
                    }
                    else if(strShlPermitHolder.equals(""))
                    {
                        ShlPermitHolder.setError("Please Enter SHL Permit Holder");
                    }
                    else if(strBaseNumber.equals(""))
                    {
                        BaseNumber.setError("Please Enter Base Number");
                    }
                    else if(strDiamondNo.equals(""))
                    {
                        DiamondNo.setError("Please Enter Diamond Number");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\n\nSelected Summon: " +  strspinnerSummonType+ "\nSelected Base Summon: " + strSpinnerBaseSummon + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +  "\nSHL Permit Holder: " + strShlPermitHolder
                                    + "\nBase Number: " +  strBaseNumber+ "\nDiamond Number: " + strDiamondNo  +  "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                            new basesummon().execute();



                        Log.d("abc", "onClick: 2");
                    }

                }
                else if(strspinnerSummonType.equals("SHL Summon"))
                {
                    if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strPlateNumber.equals(""))
                    {
                        PlateNumber.setError("Please Enter Plate Number");
                    }
                    else if(strShlPermitHolder.equals(""))
                    {
                        ShlPermitHolder.setError("Please Enter SHL Permit Holder");
                    }
                    else if(strDiamondNo.equals(""))
                    {
                        DiamondNo.setError("Please Enter Diamond Number");
                    }
                    else if(strShlPermit.equals(""))
                    {
                        ShlPermit.setError("Please Enter SHL Permit");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);


                            message = "User Email: " +email + "\n\nSelected Summon: " +  strspinnerSummonType+ "\nSelected Base Summon: "  + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +  "\nSHL Permit Holder: " + strShlPermitHolder
                                    + "\nDiamond Number: " + strDiamondNo +   "\nSHL Permit: " + strShlPermit +  "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                            new shlsummon().execute();


                    }


                }
                else if(strspinnerSummonType.equals("FHV Summon"))
                {
                    if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strPlateNumber.equals(""))
                    {
                        PlateNumber.setError("Please Enter Plate Number");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);


                            message = "User Email: " +email + "\n\nSelected Summon: " +  strspinnerSummonType + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +  "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                            new fhvsummon().execute();



                    }

                }
                else if(strspinnerSummonType.equals("Medallion/Yellow Cab"))
                {
                    if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strMedallionNo.equals(""))
                    {
                        MedallionNo.setError("Please Enter Medallion Number");
                    }
                    else if(strMedallionOwner.equals(""))
                    {
                        MedallionOwner.setError("Please Enter Medallion Owner Name");
                    }
                    else if(strFleetMedallion.equals(""))
                    {
                        FleetMedallion.setError("Please Enter Fleet / Medallion Name");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);


                            message = "User Email: " +email + "\n\nSelected Summon: " +  strspinnerSummonType + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nMedallion Number: " + strMedallionNo + "\nMedallion Owner: " + strMedallionOwner +
                                    "\nFleet Medallion : " + strFleetMedallion +    "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                        new medallionyellowcab().execute();



                        Log.d("abc", "onClick: 5");
                    }

                }
                else if(strspinnerSummonType.equals("Corporation"))
                {
                    if(strsummonno.equals(""))
                    {
                        summonno.setError("Please Enter Summon Number");
                    }
                    else if(strsummondate.equals(""))
                    {
                        summondate.setError("Please Enter Summon Date");
                    }
                    else if(strTlcLicenseHack.equals(""))
                    {
                        TlcLicenseHack.setError("Please Enter TLC License Number ");
                    }
                    else if(strPlateNumber.equals(""))
                    {
                        PlateNumber.setError("Please Enter Plate Number");
                    }
                    else if(strMedallionNo.equals(""))
                    {
                        MedallionNo.setError("Please Enter Medallion Number");
                    }
                    else if(strShlPermit.equals(""))
                    {
                        ShlPermit.setError("Please Enter SHL Permit");
                    }
                    else if(strHearingLocation.equals(""))
                    {
                        HearingLocation.setError("Please Enter Hearing Location");
                    }
                    else if(AppearanceRequired.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Appearance  Required) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(seizedCarSelectionNewSummon.getCheckedRadioButtonId()== -1)
                    {
                        Toast.makeText(NewSummon.this, "Please Select (Car Seized) Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureNewSummonImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Summon Image" , Toast.LENGTH_SHORT).show();
                    }
                    else if(takePictureComplianceImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Compliance Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        getTextSeized = seizedRadioButton.getText().toString();
                        getTextAppearance = seizedRadioButton.getText().toString();

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);


                            message = "User Email: " +email + "\n\nSelected Summon: " + strspinnerSummonType + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                    "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +  "\nMedallion Number: " + strMedallionNo +   "\nSHL Permit: " + strShlPermit +  "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                            //new SendEmail().execute();
                            new corporation().execute();




                        Log.d("abc", "onClick: 6");
                    }

                }
                /*else
                {
                    getTextSeized = seizedRadioButton.getText().toString();

                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);
                    if(!email.equals(null))
                    {

                        message = "User Email: " +email + "\n\nSelected Summon: " +  strspinnerSummonType+ "\nSelected Base Summon: " + strSpinnerBaseSummon + "\nSummon Number: " + strsummonno + "\nDate of Summon: " + strsummondate +
                                "\nTLC License No: " + strTlcLicenseHack +   "\nPlate Number / Medallion: " + strPlateNumber +  "\nSHL Permit Holder: " + strShlPermitHolder
                                + "Base Number: " +  strBaseNumber+ "\nDiamond Number: " + strDiamondNo + "\nMedallion Number: " + strMedallionNo + "\nMedallion Owner: " + strMedallionOwner +
                                "\nFleet Medallion : " + strFleetMedallion +   "\nSHL Permit: " + strShlPermit +  "\nHearing Location: " + strHearingLocation +  "\nCar Seized: " + getTextSeized;

                        new SendEmail().execute();

                    }



                    //Toast.makeText(NewSummon.this, "" +message, Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(NewSummon.this , ThankYouScreen.class);
//                    startActivity(intent);

                }*/
            }
        });


    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        summondate.setText(sdf.format(myCalendar.getTime()));
    }


    public class driversummon extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("platenumber", strPlateNumber)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)




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
//                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public class basesummon extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("selectedbasesummon", strSpinnerBaseSummon)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("platenumber", strPlateNumber)
                        .appendQueryParameter("shlpermitholder", strShlPermitHolder)
                        .appendQueryParameter("basenumber", strBaseNumber)
                        .appendQueryParameter("diamondnumber", strDiamondNo)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)



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
                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public class shlsummon extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("platenumber", strPlateNumber)
                        .appendQueryParameter("shlpermitholder", strShlPermitHolder)
                        .appendQueryParameter("diamondnumber", strDiamondNo)
                        .appendQueryParameter("shlpermit", strShlPermit)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)



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
                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public class fhvsummon extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("platenumber", strPlateNumber)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)




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
                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public class medallionyellowcab extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("medallionno", strMedallionNo)
                        .appendQueryParameter("medallionowner", strMedallionOwner)
                        .appendQueryParameter("fleetmedallion", strFleetMedallion)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)




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
                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public class corporation extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/summon.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadImagenewsummon = getStringImage(bitmap1);
                String uploadImageCompliance = getStringImage(bitmap2);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("selectedsummon", strspinnerSummonType)
                        .appendQueryParameter("summonnumber", strsummonno)
                        .appendQueryParameter("dateofsummon", strsummondate)
                        .appendQueryParameter("tlclicennse", strTlcLicenseHack)
                        .appendQueryParameter("platenumber", strPlateNumber)
                        .appendQueryParameter("medallionno", strMedallionNo)
                        .appendQueryParameter("shlpermit", strShlPermit)
                        .appendQueryParameter("hearing", strHearingLocation)
                        .appendQueryParameter("addcomment", straddcoment)
                        .appendQueryParameter("appearance", getTextAppearance)
                        .appendQueryParameter("carseized", getTextSeized)
                        .appendQueryParameter("newsummon", uploadImagenewsummon)
                        .appendQueryParameter("timestampnewsummon", timestamp1)
                        .appendQueryParameter("compliance", uploadImageCompliance)
                        .appendQueryParameter("timestampcompliance", timestamp2)




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
                Intent intent = new Intent(NewSummon.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(NewSummon.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    class UploadImage extends AsyncTask<Bitmap,Void,String>{

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
            loading = ProgressDialog.show(NewSummon.this, "Uploading Image", "Please wait...",true,true);
            Log.d("bitmap one", "onPreExecute");
        }
        @Override
        protected String doInBackground(Bitmap... params) {
            // Bitmap bitmap = params[0];
            String uploadImagedriverlicecnce = getStringImage(bitmap1);


            HashMap<String,String> data = new HashMap<>();
            data.put("newsummon", uploadImagedriverlicecnce);
            data.put("timestampnewsummon",timestamp1);


            String result = rh.sendPostRequest(SERVER,data);
            Log.d("bitmap one", "doInBackground: "+result);
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null)
            {
                loading.dismiss();
                Toast.makeText(NewSummon.this, "Server not Responding", Toast.LENGTH_SHORT).show();
            }
            else if(s.equalsIgnoreCase("true"))
            {
                Toast.makeText(getApplicationContext(),"Image Sent Successfully",Toast.LENGTH_SHORT).show();
            }
            else if (s.equalsIgnoreCase("false")){

                loading.dismiss();
                Toast.makeText(NewSummon.this, "Some Problem with Image", Toast.LENGTH_LONG).show();

            }
            else if (s.equalsIgnoreCase("exception") || s.equalsIgnoreCase("unsuccessful")) {

                loading.dismiss();
                Toast.makeText(NewSummon.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }



            Log.d("bitmap one", "onPostExecute: ");
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                UploadImageone ui2 = new UploadImageone("IMG_"+timestamp);
//                ui2.execute(bitmap2);

        }

    }


    //send email aysnctask
    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(NewSummon.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {

                GMailSender sender = new GMailSender(fromEmail, password);
                sender.addAttachment(Environment.getExternalStorageDirectory()+Imagepath);
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



    private void setupIds() {

        summonno = (EditText) findViewById(R.id.summonnoNewSummon);
        summondate = (EditText) findViewById(R.id.summondateNewSummon);
        TlcLicenseHack = (EditText) findViewById(R.id.tlcDriverLicenseNumberNewSummon);
        PlateNumber = (EditText) findViewById(R.id.platenumbernewsummon);
        ShlPermitHolder = (EditText) findViewById(R.id.shlpermitholder);
        BaseNumber = (EditText) findViewById(R.id.basenubernewsummon);
        MedallionNo = (EditText) findViewById(R.id.medallionno);
        DiamondNo = (EditText) findViewById(R.id.diamondnumbernewsummon);
        MedallionOwner = (EditText) findViewById(R.id.medallionownername);
        FleetMedallion = (EditText) findViewById(R.id.fleetmadallionname);
        ShlPermit = (EditText) findViewById(R.id.shlpermitnumber);
        HearingLocation = (EditText) findViewById(R.id.hearinglocation);
        addcomment = (EditText) findViewById(R.id.addcoment);
        seizedCarSelectionNewSummon = (RadioGroup) findViewById(R.id.seizedCarSelectionNewSummon);
        AppearanceRequired = (RadioGroup) findViewById(R.id.apperanceradiogroup);
        carSeizedYesNewSummon = (RadioButton) findViewById(R.id.carSeizedYesNewSummon);
        carSeizedNoNewSummon = (RadioButton) findViewById(R.id.carSeizedNoNewSummon);

        SummonPicture = (Button) findViewById(R.id.takePictureNewSummon);
        CompliancePicture = (Button) findViewById(R.id.takePicturecompliance);
        submit = (Button) findViewById(R.id.submitNewSummon);
        takePictureNewSummonImageView = (ImageView) findViewById(R.id.takePictureNewSummonImageView);
        takePictureComplianceImageView = (ImageView) findViewById(R.id.takePicturecomplianceImageView);


        SummonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = summonimg;

                selectImage();
            }
        });

        CompliancePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = complianceimg;

                selectImage();
            }
        });


    }

//














//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//
//    }


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
        if (ContextCompat.checkSelfPermission(NewSummon.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(NewSummon.this,
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
        if (ContextCompat.checkSelfPermission(NewSummon.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(NewSummon.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Globals.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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




    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Phone Storage",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewSummon.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(NewSummon.this);

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
        if(btnClicked == summonimg) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_summonimgn);
        }
        else if(btnClicked == complianceimg) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_complianceimg);
        }
    }

    private void cameraIntent()
    {

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
        if(btnClicked == summonimg) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_summonimg);
        }

        else if(btnClicked == complianceimg) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_complianceimg);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_summonimgn)
                onSelectFromGalleryResult(data , SELECT_FILE_summonimgn);
            else if (requestCode == REQUEST_CAMERA_summonimg)
                onCaptureImageResult(data , REQUEST_CAMERA_summonimg);
            else if (requestCode == SELECT_FILE_complianceimg)
                onSelectFromGalleryResult(data , SELECT_FILE_complianceimg);
            else if (requestCode == REQUEST_CAMERA_complianceimg)
                onCaptureImageResult(data , REQUEST_CAMERA_complianceimg);
        }
    }

    private void onCaptureImageResult(Intent data , int requestCode) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);




        if(requestCode == REQUEST_CAMERA_summonimg ) {


//            ArticleImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ArticleImagepath);

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            takePictureNewSummonImageView.setVisibility(View.VISIBLE);
            takePictureNewSummonImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();


//            ArticleOrgnizationImageView.setVisibility(View.VISIBLE);
//
//            ArticleOrgnizationImageView.setImageBitmap(thumbnail);
        }


        if(requestCode == REQUEST_CAMERA_complianceimg ) {


//            ArticleImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ArticleImagepath);

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            takePictureComplianceImageView.setVisibility(View.VISIBLE);
            takePictureComplianceImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();



        }






//
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
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, out);
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
//
//        Imagepath = "/Pictures/TLC/"+fname;
//        Log.d("afdff", "onCaptureImageResult: "+Imagepath);


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

//        takePictureNewSummonImageView.setVisibility(View.VISIBLE);
//
//
//        takePictureNewSummonImageView.setImageBitmap(thumbnail);
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
//
//
//        String gallarypath = GetFilePathFromDevice.getPath(NewSummon.this, data.getData());
//        Log.d("ReportAccidentForm", "File Path: " + gallarypath);
//        String abc = gallarypath.substring(gallarypath.lastIndexOf("/") );
//        Log.d("ReportAccidentForm", "result right" + abc);
//        String [] str = gallarypath.split(abc);
//        String firstImagepath = str[0];
//        Log.d("ReportAccidentForm", "result left" + firstImagepath);
//        String pathback = firstImagepath.substring(firstImagepath.lastIndexOf("/") );
//        Log.d("ReportAccidentForm", "result back" + pathback);
//        String subfinal = pathback + abc;
//        Log.d("ReportAccidentForm", "result subfinal" + subfinal);
//        String [] left = firstImagepath.split(pathback);
//        String left1 = left[0];
//        Log.d("ReportAccidentForm", "result left1" + left1);
//        String finalpath = left1.substring(left1.lastIndexOf("/") );
//        Log.d("ReportAccidentForm", "result final" + finalpath);
//        Imagepath =finalpath +  pathback + abc;
//        Log.d("ReportAccidentForm", "result final path" + Imagepath );
        if(requestCode == SELECT_FILE_summonimgn) {


            Log.d("", "onSelectFromGalleryResult: license file");

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                takePictureNewSummonImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                takePictureNewSummonImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(requestCode == SELECT_FILE_complianceimg) {


            Log.d("", "onSelectFromGalleryResult: license file");

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                takePictureComplianceImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                takePictureComplianceImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        takePictureNewSummonImageView.setVisibility(View.VISIBLE);
//
//        takePictureNewSummonImageView.setImageBitmap(bm);
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
                                    NewSummon.this.finish();
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
                Intent intent = new Intent(NewSummon.this,ThankYouScreen.class);
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
