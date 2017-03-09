package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.TLCDocument;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.Globals;
import com.ranglerz.tlc.tlc.R;
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

public class IndividualsCorporationPartnership extends AppCompatActivity {

    private String [] aplicantname = { "One of the following applicant’s name" , "Vehicle Certificate of Title" , "Vehicle Certificate of Origin" , "Bill of Sale" , "Vehicle Leasing Agreement" , "DMV Registration"};

    Button GovetIssued , AllCorporate , ShareholderOrPartner ,   ForHire , ExcessInsurance , VehcileCertificateOfTitle ,
            VehcileCertificateOfOrogin , BillOfSale
            , VehicleLeasing , DMVRegistrationn , Submit;
    ImageView GovetIssuedImageView , AllCorporateImageView , ShareholderOrPartnerImageView ,   ForHireImageView , ExcessInsuranceImageView , VehcileCertificateOfTitleImageView , VehcileCertificateOfOroginImageView , BillOfSaleImageView
            , VehicleLeasingImageView , DMVRegistrationnImageView ;
    EditText AddComment;
    String strAddComment , strOneofThefollowing;
    String GovtImagepath , AllCorporateImagepath , ShareholderImagepath , ForHireImagepath  , TitleImagepath ,
            OroginImagepath ,BillOfSaleImagepath, VehicleLeasingImagepath, DMVRegistrationnImagepath , ImagePath , strSpinnerApplicantname;


    String ExcessInsuranceImagepath = null;

    Spinner SpinnerApplicantname;

    TextView MustSubmit;
    Uri imageUri;
    Bitmap thumbnail;
    Bitmap bitmap1 , bitmap2 , bitmap3 , bitmap4 , bitmap5 , bitmap6, bitmap7 , bitmap8 , bitmap9 , bitmap10 ,bitmap11;
    String timestamp1 , timestamp2 , timestamp3, timestamp4, timestamp5, timestamp6, timestamp7, timestamp8 , timestamp9 , timestamp10 , timestamp11 ;
    String userID = "userID";


    private String userChoosenTask;
    public static int btnClicked=0;
    public static int GovetIssuedButton = 1;
    public static int AllCorporateButton = 2;
    public static int ShareholderOrPartnerButton = 3;
    public static int ForHireButton = 4;
    public static int ExcessInsurancebutton = 5;
    public static int VehcileCertificateOfTitlebutton = 6;
    public static int VehcileCertificateOfOroginbutton = 7;
    public static int BillOfSalebutton = 8;
    public static int VehicleLeasingbutton = 9;
    public static int DMVRegistrationnbutton = 10;


    private int REQUEST_CAMERA_GovetIssued = 11;
    private int REQUEST_CAMERA_AllCorporate = 12;
    private int REQUEST_CAMERA_ShareholderOrPartner = 13;
    private int REQUEST_CAMERA_ForHire = 14;
    private int REQUEST_CAMERA_ExcessInsurance = 15;
    private int REQUEST_CAMERA_VehcileCertificateOfTitle = 16;
    private int REQUEST_CAMERA_VehcileCertificateOfOrogin = 17;
    private int REQUEST_CAMERA_BillOfSale = 18;
    private int REQUEST_CAMERA_VehicleLeasing = 19;
    private int REQUEST_CAMERA_DMVRegistrationn = 20;



    public int SELECT_FILE_GovetIssued = 21;
    public int SELECT_FILE_AllCorporate = 22;
    public int SELECT_FILE_ShareholderOrPartner = 23;
    public int SELECT_FILE_ForHire = 24;
    public int SELECT_FILE_ExcessInsurance = 25;
    public int SELECT_FILE_VehcileCertificateOfTitle = 26;
    public int SELECT_FILE_VehcileCertificateOfOrogin = 27;
    public int SELECT_FILE_BillOfSale = 28;
    public int SELECT_FILE_VehicleLeasing = 29;
    public int SELECT_FILE_DMVRegistrationn = 30;

    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "Individual Applicant";
    public String message = "";
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals_corporation_partnership);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();

        GovetIssued = (Button)findViewById(R.id.goveissued);
        AllCorporate = (Button)findViewById(R.id.allcoprateprincipals);
        ShareholderOrPartner = (Button)findViewById(R.id.shareholderorpartner);
        ForHire = (Button)findViewById(R.id.forhireinsurance);
        ExcessInsurance = (Button)findViewById(R.id.excessinsurance);
        VehcileCertificateOfTitle = (Button)findViewById(R.id.vehiclecertifiacteoftitle);
        VehcileCertificateOfOrogin = (Button)findViewById(R.id.vehiclecertifiacteoforigin);
        BillOfSale = (Button)findViewById(R.id.billofsale);
        VehicleLeasing = (Button)findViewById(R.id.vehicleleasing);
        DMVRegistrationn = (Button)findViewById(R.id.dmvregistration);
        Submit = (Button)findViewById(R.id.submitindividualcorporation);

        SpinnerApplicantname = (Spinner) findViewById(R.id.spinneraplicantname);
        MustSubmit  = (TextView) findViewById(R.id.mustsubmit);

        GovetIssuedImageView = (ImageView) findViewById(R.id.goveissuedImageView);
        AllCorporateImageView = (ImageView)findViewById(R.id.allcoprateprincipalsImageView);
        ShareholderOrPartnerImageView = (ImageView)findViewById(R.id.shareholderorpartnerImageView);
        ForHireImageView = (ImageView)findViewById(R.id.forhireinsuranceImageView);
        ExcessInsuranceImageView = (ImageView)findViewById(R.id.excessinsuranceImageView);
        VehcileCertificateOfTitleImageView = (ImageView)findViewById(R.id.vehiclecertifiacteoftitleImageView);
        VehcileCertificateOfOroginImageView = (ImageView)findViewById(R.id.vehiclecertifiacteoforiginImageView);
        BillOfSaleImageView = (ImageView)findViewById(R.id.billofsaleImageView);
        VehicleLeasingImageView = (ImageView)findViewById(R.id.vehicleleasingImageView);
        DMVRegistrationnImageView = (ImageView)findViewById(R.id.dmvregistrationImageView);
        AddComment = (EditText) findViewById(R.id.individualaddcoment);


        GovetIssuedImageView.setVisibility(View.GONE);
        AllCorporateImageView.setVisibility(View.GONE);
        ShareholderOrPartnerImageView.setVisibility(View.GONE);
        ForHireImageView.setVisibility(View.GONE);
        ExcessInsuranceImageView.setVisibility(View.GONE);
        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
        BillOfSaleImageView.setVisibility(View.GONE);
        VehicleLeasingImageView.setVisibility(View.GONE);
        DMVRegistrationnImageView.setVisibility(View.GONE);

        VehcileCertificateOfTitle.setVisibility(View.GONE);
        VehcileCertificateOfOrogin.setVisibility(View.GONE);
        BillOfSale.setVisibility(View.GONE);
        VehicleLeasing.setVisibility(View.GONE);
        DMVRegistrationn.setVisibility(View.GONE);
        MustSubmit.setVisibility(View.GONE);



        final String[] Purchase_rent_sale = getResources().getStringArray(R.array.Car_purchase_rent_sale);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, aplicantname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerApplicantname.setAdapter(adapter);


        SpinnerApplicantname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch (arg2) {
                    case 0:

                        VehcileCertificateOfTitle.setVisibility(View.GONE);
                        VehcileCertificateOfOrogin.setVisibility(View.GONE);
                        BillOfSale.setVisibility(View.GONE);
                        VehicleLeasing.setVisibility(View.GONE);
                        DMVRegistrationn.setVisibility(View.GONE);

                        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
                        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
                        BillOfSaleImageView.setVisibility(View.GONE);
                        VehicleLeasingImageView.setVisibility(View.GONE);
                        DMVRegistrationnImageView.setVisibility(View.GONE);

                        MustSubmit.setVisibility(View.GONE);

                        break;
                    case 1:

                        VehcileCertificateOfTitle.setVisibility(View.VISIBLE);
                        //VehcileCertificateOfTitleImageView.setVisibility(View.VISIBLE);

                        VehcileCertificateOfOrogin.setVisibility(View.GONE);
                        BillOfSale.setVisibility(View.GONE);
                        VehicleLeasing.setVisibility(View.GONE);
                        DMVRegistrationn.setVisibility(View.GONE);

                        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
                        BillOfSaleImageView.setVisibility(View.GONE);
                        VehicleLeasingImageView.setVisibility(View.GONE);
                        DMVRegistrationnImageView.setVisibility(View.GONE);

                        MustSubmit.setVisibility(View.GONE);


                        break;
                    case 2:

                        VehcileCertificateOfOrogin.setVisibility(View.VISIBLE);
                        //VehcileCertificateOfOroginImageView.setVisibility(View.VISIBLE);

                        VehcileCertificateOfTitle.setVisibility(View.GONE);
                        BillOfSale.setVisibility(View.GONE);
                        VehicleLeasing.setVisibility(View.GONE);
                        DMVRegistrationn.setVisibility(View.GONE);

                        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
                        BillOfSaleImageView.setVisibility(View.GONE);
                        VehicleLeasingImageView.setVisibility(View.GONE);
                        DMVRegistrationnImageView.setVisibility(View.GONE);

                        MustSubmit.setVisibility(View.GONE);


                        break;
                    case 3:

                        BillOfSale.setVisibility(View.VISIBLE);
                      //  BillOfSaleImageView.setVisibility(View.VISIBLE);

                        VehcileCertificateOfTitle.setVisibility(View.GONE);
                        VehcileCertificateOfOrogin.setVisibility(View.GONE);
                        VehicleLeasing.setVisibility(View.GONE);
                        DMVRegistrationn.setVisibility(View.GONE);

                        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
                        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
                        VehicleLeasingImageView.setVisibility(View.GONE);
                        DMVRegistrationnImageView.setVisibility(View.GONE);

                        MustSubmit.setVisibility(View.GONE);


                        break;
                    case 4:

                        VehicleLeasing.setVisibility(View.VISIBLE);
                       // VehicleLeasingImageView.setVisibility(View.VISIBLE);

                        VehcileCertificateOfTitle.setVisibility(View.GONE);
                        VehcileCertificateOfOrogin.setVisibility(View.GONE);
                        BillOfSale.setVisibility(View.GONE);
                        DMVRegistrationn.setVisibility(View.GONE);

                        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
                        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
                        BillOfSaleImageView.setVisibility(View.GONE);
                        DMVRegistrationnImageView.setVisibility(View.GONE);

                        MustSubmit.setVisibility(View.GONE);


                        break;

                    case 5:

                        MustSubmit.setVisibility(View.VISIBLE);
                        DMVRegistrationn.setVisibility(View.VISIBLE);
                        //DMVRegistrationnImageView.setVisibility(View.VISIBLE);

                        VehcileCertificateOfTitle.setVisibility(View.GONE);
                        VehcileCertificateOfOrogin.setVisibility(View.GONE);
                        BillOfSale.setVisibility(View.GONE);
                        VehicleLeasing.setVisibility(View.GONE);

                        VehcileCertificateOfTitleImageView.setVisibility(View.GONE);
                        VehcileCertificateOfOroginImageView.setVisibility(View.GONE);
                        BillOfSaleImageView.setVisibility(View.GONE);
                        VehicleLeasingImageView.setVisibility(View.GONE);



                        break;

                    default:

                        break;

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });





        GovetIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = GovetIssuedButton;
                selectImage();
            }
        });

        AllCorporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = AllCorporateButton;

                selectImage();
            }
        });

        ShareholderOrPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = ShareholderOrPartnerButton;

                selectImage();
            }
        });

        ForHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = ForHireButton;

                selectImage();
            }
        });

        ExcessInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = ExcessInsurancebutton;

                selectImage();
            }
        });

        VehcileCertificateOfTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = VehcileCertificateOfTitlebutton;

                selectImage();
            }
        });

        VehcileCertificateOfOrogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = VehcileCertificateOfOroginbutton;

                selectImage();
            }
        });


        BillOfSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = BillOfSalebutton;

                selectImage();
            }
        });


        VehicleLeasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = VehicleLeasingbutton;

                selectImage();
            }
        });


        DMVRegistrationn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = DMVRegistrationnbutton;

                selectImage();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strSpinnerApplicantname = SpinnerApplicantname.getSelectedItem().toString();
                strAddComment = AddComment.getText().toString();
                strOneofThefollowing = SpinnerApplicantname.getSelectedItem().toString();


                if(GovetIssuedImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Govt-Issued Photo ID Image" , Toast.LENGTH_SHORT).show();
                }
                else if(AllCorporateImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload All Coprate Principals Image" , Toast.LENGTH_SHORT).show();
                }
                else if(ShareholderOrPartnerImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Shareholder Or Partner Image" , Toast.LENGTH_SHORT).show();
                }
                else if(ForHireImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload For Hire Insurance Certificate (FH-1) Image" , Toast.LENGTH_SHORT).show();
                }
//                else if(ExcessInsuranceImageView.getDrawable() == null)
//                {
//                    Toast.makeText(getApplicationContext() , "Please Upload Excess Insurance Policy Image" , Toast.LENGTH_SHORT).show();
//                }
                else if(strSpinnerApplicantname.equals("One of the following applicant’s name"))
                {
                    Toast.makeText(getApplicationContext() , "Please Select (One of the following....)Drop Down Menu" , Toast.LENGTH_SHORT).show();
                }
                else if(strSpinnerApplicantname.equals("Vehicle Certificate of Title"))
                {
                    if(VehcileCertificateOfTitleImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Vehcile Certificate Of Title ID Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("individual","abc 1");

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\nIndividual Applicant Image";

                        //new SendEmail().execute();
                        //new UploadImage().execute();
                        excessInsurance();

                    }
                }
                else if(strSpinnerApplicantname.equals("Vehicle Certificate of Origin"))
                {
                    if(VehcileCertificateOfOroginImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Vehicle Certificate of Origin Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("individual","abc 1");

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\nIndividual Applicant Image";

                        //new SendEmail().execute();
//                        new UploadImage().execute();
                        excessInsurance();


                    }
                }
                else if(strSpinnerApplicantname.equals("Bill of Sale"))
                {
                    if(BillOfSaleImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Bill of Sale Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("individual","abc 1");

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\nIndividual Applicant Image";

                        //new SendEmail().execute();
//                        new UploadImage().execute();
                        excessInsurance();


                    }
                }
                else if(strSpinnerApplicantname.equals("Vehicle Leasing Agreement") )
                {
                    if(VehicleLeasingImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload Vehicle Leasing Agreement Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("individual","abc 1");

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\nIndividual Applicant Image";

                        //new SendEmail().execute();
//                        new UploadImage().execute();
                        excessInsurance();


                    }
                }
                else if(strSpinnerApplicantname.equals("DMV Registration"))
                {
                    if(DMVRegistrationnImageView.getDrawable() == null)
                    {
                        Toast.makeText(getApplicationContext() , "Please Upload DMV Registration Image" , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("individual","abc 1");

                        sharedPreferences = getSharedPreferences("email" , 1);
                        String email = sharedPreferences.getString(emailkey , null);

                            message = "User Email: " +email + "\nIndividual Applicant Image";

                        //new SendEmail().execute();
//                        new UploadImage().execute();
                        excessInsurance();


                    }
                }
//                else
//                {
//                    Log.d("individual","abc 1");
//
//                    sharedPreferences = getSharedPreferences("email" , 1);
//                    String email = sharedPreferences.getString(emailkey , null);
//                    if(!email.equals(null))
//                    {
//                        message = "User Email: " +email + "\nIndividual Applicant Image";
//                    }
//                    new SendEmail().execute();
//                }
//

            }
        });





    }
    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(IndividualsCorporationPartnership.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(IndividualsCorporationPartnership.this,
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
        if (ContextCompat.checkSelfPermission(IndividualsCorporationPartnership.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(IndividualsCorporationPartnership.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Phone Storage"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Phone Storage",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualsCorporationPartnership.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(IndividualsCorporationPartnership.this);

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
        if(btnClicked == GovetIssuedButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_GovetIssued);

        }
        else if(btnClicked == AllCorporateButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_AllCorporate);
        }
        else if(btnClicked == ShareholderOrPartnerButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ShareholderOrPartner);
        }
        else if(btnClicked == ForHireButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ForHire);
        }
        else if(btnClicked == ExcessInsurancebutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ExcessInsurance);
        }
        else if(btnClicked == VehcileCertificateOfTitlebutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_VehcileCertificateOfTitle);
        }
        else if(btnClicked == VehcileCertificateOfOroginbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_VehcileCertificateOfOrogin);
        }
        else if(btnClicked == BillOfSalebutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_BillOfSale);
        }
        else if(btnClicked == VehicleLeasingbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_VehicleLeasing);
        }
        else if(btnClicked == DMVRegistrationnbutton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_DMVRegistrationn);
        }
    }

    private void cameraIntent()
    {
        if(btnClicked == GovetIssuedButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_GovetIssued);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_GovetIssued);
        }
        else if(btnClicked == AllCorporateButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_AllCorporate);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_AllCorporate);
        }
        else if(btnClicked == ShareholderOrPartnerButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ShareholderOrPartner);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ShareholderOrPartner);
        }
        else if(btnClicked == ForHireButton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ForHire);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ForHire);
        }
        else if(btnClicked == ExcessInsurancebutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ExcessInsurance);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ExcessInsurance);
        }
        else if(btnClicked == VehcileCertificateOfTitlebutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_VehcileCertificateOfTitle);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_VehcileCertificateOfTitle);
        }
        else if(btnClicked == VehcileCertificateOfOroginbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_VehcileCertificateOfOrogin );

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_VehcileCertificateOfOrogin);
        }
        else if(btnClicked == BillOfSalebutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_BillOfSale);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_BillOfSale);
        }
        else if(btnClicked == VehicleLeasingbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_VehicleLeasing);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_VehicleLeasing);
        }
        else if(btnClicked == DMVRegistrationnbutton) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_DMVRegistrationn);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_DMVRegistrationn);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_GovetIssued)
                onSelectFromGalleryResult(data,SELECT_FILE_GovetIssued);
            else if (requestCode == SELECT_FILE_AllCorporate)
                onSelectFromGalleryResult(data,SELECT_FILE_AllCorporate);
            else if (requestCode == SELECT_FILE_ShareholderOrPartner)
                onSelectFromGalleryResult(data,SELECT_FILE_ShareholderOrPartner);
            else if (requestCode == SELECT_FILE_ForHire)
                onSelectFromGalleryResult(data,SELECT_FILE_ForHire);
            else if (requestCode == SELECT_FILE_ExcessInsurance)
                onSelectFromGalleryResult(data,SELECT_FILE_ExcessInsurance);
            else if (requestCode == SELECT_FILE_VehcileCertificateOfTitle)
                onSelectFromGalleryResult(data,SELECT_FILE_VehcileCertificateOfTitle);
            else if (requestCode == SELECT_FILE_VehcileCertificateOfOrogin)
                onSelectFromGalleryResult(data,SELECT_FILE_VehcileCertificateOfOrogin);
            else if (requestCode == SELECT_FILE_BillOfSale)
                onSelectFromGalleryResult(data,SELECT_FILE_BillOfSale);
            else if (requestCode == SELECT_FILE_VehicleLeasing)
                onSelectFromGalleryResult(data,SELECT_FILE_VehicleLeasing);
            else if (requestCode == SELECT_FILE_DMVRegistrationn)
                onSelectFromGalleryResult(data,SELECT_FILE_DMVRegistrationn);
            else if (requestCode == REQUEST_CAMERA_GovetIssued)
                onCaptureImageResult(data , REQUEST_CAMERA_GovetIssued);
            else if (requestCode == REQUEST_CAMERA_AllCorporate)
                onCaptureImageResult(data , REQUEST_CAMERA_AllCorporate);
            else if (requestCode == REQUEST_CAMERA_ShareholderOrPartner)
                onCaptureImageResult(data , REQUEST_CAMERA_ShareholderOrPartner);
            else if (requestCode == REQUEST_CAMERA_ForHire)
                onCaptureImageResult(data , REQUEST_CAMERA_ForHire);
            else if (requestCode == REQUEST_CAMERA_ExcessInsurance)
                onCaptureImageResult(data , REQUEST_CAMERA_ExcessInsurance);
            else if (requestCode == REQUEST_CAMERA_VehcileCertificateOfTitle)
                onCaptureImageResult(data , REQUEST_CAMERA_VehcileCertificateOfTitle);
            else if (requestCode == REQUEST_CAMERA_VehcileCertificateOfOrogin)
                onCaptureImageResult(data , REQUEST_CAMERA_VehcileCertificateOfOrogin);
            else if (requestCode == REQUEST_CAMERA_BillOfSale)
                onCaptureImageResult(data , REQUEST_CAMERA_BillOfSale);
            else if (requestCode == REQUEST_CAMERA_VehicleLeasing)
                onCaptureImageResult(data , REQUEST_CAMERA_VehicleLeasing);
            else if (requestCode == REQUEST_CAMERA_DMVRegistrationn)
                onCaptureImageResult(data , REQUEST_CAMERA_DMVRegistrationn);
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

////        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
////        File myDir = new File(root + "/TLC");
////        myDir.mkdirs();
////        Random generator = new Random();
////        int n = 10000;
////        n = generator.nextInt(n);
////        fname = "Image-" + n + ".jpg";
////        File file = new File(myDir, fname);
////        if (file.exists())
////            file.delete();
////        try {
////            FileOutputStream out = new FileOutputStream(file);
////            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
////            out.flush();
////            out.close();
////        }
////        catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//        // Tell the media scanner about the new file so that it is
//        // immediately available to the user.
//
//        MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });


//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
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

        if(requestCode == REQUEST_CAMERA_GovetIssued) {

//            GovtImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+GovtImagepath);

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GovetIssuedImageView.setVisibility(View.VISIBLE);
            GovetIssuedImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();


//            GovetIssuedImageView.setVisibility(View.VISIBLE);
//            GovetIssuedImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_AllCorporate)
        {
//            AllCorporateImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+AllCorporateImagepath);

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AllCorporateImageView.setVisibility(View.VISIBLE);
            AllCorporateImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();


//            AllCorporateImageView.setVisibility(View.VISIBLE);
//
//            AllCorporateImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_ShareholderOrPartner)
        {
//            ShareholderImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ShareholderImagepath);

            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ShareholderOrPartnerImageView.setVisibility(View.VISIBLE);
            ShareholderOrPartnerImageView.setImageBitmap(bitmap3);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();


//            ShareholderOrPartnerImageView.setVisibility(View.VISIBLE);
//
//            ShareholderOrPartnerImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_ForHire)
        {

//            ForHireImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ForHireImagepath);

            try {
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ForHireImageView.setVisibility(View.VISIBLE);
            ForHireImageView.setImageBitmap(bitmap4);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();


//            ForHireImageView.setVisibility(View.VISIBLE);
//
//            ForHireImageView.setImageBitmap(thumbnail);
        }else if(requestCode == REQUEST_CAMERA_ExcessInsurance)
        {
//            ExcessInsuranceImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ExcessInsuranceImagepath);

            try {
                bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ExcessInsuranceImageView.setVisibility(View.VISIBLE);
            ExcessInsuranceImageView.setImageBitmap(bitmap5);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp5 = tsLong.toString();


//            ExcessInsuranceImageView.setVisibility(View.VISIBLE);
//
//            ExcessInsuranceImageView.setImageBitmap(thumbnail);


        }else if(requestCode == REQUEST_CAMERA_VehcileCertificateOfTitle)
        {

//            ImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ImagePath);

            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            VehcileCertificateOfTitleImageView.setVisibility(View.VISIBLE);
            VehcileCertificateOfTitleImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();



//            VehcileCertificateOfTitleImageView.setVisibility(View.VISIBLE);
//
//            VehcileCertificateOfTitleImageView.setImageBitmap(thumbnail);
        }else if(requestCode == REQUEST_CAMERA_VehcileCertificateOfOrogin)
        {

//            ImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ImagePath);

            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            VehcileCertificateOfOroginImageView.setVisibility(View.VISIBLE);
            VehcileCertificateOfOroginImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


//            VehcileCertificateOfOroginImageView.setVisibility(View.VISIBLE);
//
//            VehcileCertificateOfOroginImageView.setImageBitmap(thumbnail);
        }else if(requestCode == REQUEST_CAMERA_BillOfSale)
        {
//            ImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ImagePath);

            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BillOfSaleImageView.setVisibility(View.VISIBLE);
            BillOfSaleImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


//            BillOfSaleImageView.setVisibility(View.VISIBLE);
//
//            BillOfSaleImageView.setImageBitmap(thumbnail);
        }else if(requestCode == REQUEST_CAMERA_VehicleLeasing)
        {
//            ImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ImagePath);


            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            VehicleLeasingImageView.setVisibility(View.VISIBLE);
            VehicleLeasingImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


//            VehicleLeasingImageView.setVisibility(View.VISIBLE);
//
//            VehicleLeasingImageView.setImageBitmap(thumbnail);
        }else if(requestCode == REQUEST_CAMERA_DMVRegistrationn)
        {
//            ImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ImagePath);

            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DMVRegistrationnImageView.setVisibility(View.VISIBLE);
            DMVRegistrationnImageView.setImageBitmap(bitmap6);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


//            DMVRegistrationnImageView.setVisibility(View.VISIBLE);
//
//            DMVRegistrationnImageView.setImageBitmap(thumbnail);
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

        if(requestCode == SELECT_FILE_GovetIssued) {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            GovtImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + GovtImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                GovetIssuedImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                GovetIssuedImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            GovetIssuedImageView.setVisibility(View.VISIBLE);
//
//            GovetIssuedImageView.setImageBitmap(bm);
        }
        else if(requestCode == SELECT_FILE_AllCorporate)
        {
//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            AllCorporateImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + AllCorporateImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                AllCorporateImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                AllCorporateImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            AllCorporateImageView.setVisibility(View.VISIBLE);
//
//            AllCorporateImageView.setImageBitmap(bm);
        }
        else if(requestCode == SELECT_FILE_ShareholderOrPartner)
        {
//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ShareholderImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ShareholderImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();
            try {
                ShareholderOrPartnerImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ShareholderOrPartnerImageView.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ShareholderOrPartnerImageView.setVisibility(View.VISIBLE);
//
//            ShareholderOrPartnerImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_ForHire)
        {
//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ForHireImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ForHireImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();
            try {
                ForHireImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ForHireImageView.setImageBitmap(bitmap4);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ForHireImageView.setVisibility(View.VISIBLE);
//
//            ForHireImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_ExcessInsurance)
        {
//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ExcessInsuranceImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ExcessInsuranceImagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp5 = tsLong.toString();
            try {
                ExcessInsuranceImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ExcessInsuranceImageView.setImageBitmap(bitmap5);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ExcessInsuranceImageView.setVisibility(View.VISIBLE);
//
//            ExcessInsuranceImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_VehcileCertificateOfTitle)
        {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ImagePath );


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                VehcileCertificateOfTitleImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                VehcileCertificateOfTitleImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            VehcileCertificateOfTitleImageView.setVisibility(View.VISIBLE);
//
//            VehcileCertificateOfTitleImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_VehcileCertificateOfOrogin)
        {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                VehcileCertificateOfOroginImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                VehcileCertificateOfOroginImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            VehcileCertificateOfOroginImageView.setVisibility(View.VISIBLE);
//
//            VehcileCertificateOfOroginImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_BillOfSale)
        {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                BillOfSaleImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                BillOfSaleImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            BillOfSaleImageView.setVisibility(View.VISIBLE);
//
//            BillOfSaleImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_VehicleLeasing)
        {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                VehicleLeasingImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                VehicleLeasingImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            VehicleLeasingImageView.setVisibility(View.VISIBLE);
//
//            VehicleLeasingImageView.setImageBitmap(bm);
        }else if(requestCode == SELECT_FILE_DMVRegistrationn)
        {

//            String gallarypath = GetFilePathFromDevice.getPath(IndividualsCorporationPartnership.this, data.getData());
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
//            ImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + ImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();
            try {
                DMVRegistrationnImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                DMVRegistrationnImageView.setImageBitmap(bitmap6);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            DMVRegistrationnImageView.setVisibility(View.VISIBLE);
//
//            DMVRegistrationnImageView.setImageBitmap(bm);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public class UploadImagewithexcessinsurance extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(IndividualsCorporationPartnership.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/individuals_applicant.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadgovtissued = getStringImage(bitmap1);
                String uploadallcoprate = getStringImage(bitmap2);
                String uploadshareholderpartner = getStringImage(bitmap3);
                String uploadforhire = getStringImage(bitmap4);
                String uploadExcessInsurance = getStringImage(bitmap5);
                String OneofTheFollowing = getStringImage(bitmap6);
//                String uploadIRS = getStringImage(bitmap7);
//                String uploadIRS = getStringImage(bitmap8);
//                String uploadIRS = getStringImage(bitmap9);
//                String uploadIRS = getStringImage(bitmap10);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Log.d("" , "userr" +user);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("govtissued", uploadgovtissued)
                        .appendQueryParameter("timestampgovtissued", timestamp1)
                        .appendQueryParameter("allcorporate", uploadallcoprate)
                        .appendQueryParameter("timestampallcorporate", timestamp2)
                        .appendQueryParameter("shareholderandpartner", uploadshareholderpartner)
                        .appendQueryParameter("timestampshareholderandpartner", timestamp3)
                        .appendQueryParameter("forhire", uploadforhire)
                        .appendQueryParameter("timestampforhire", timestamp4)
                        .appendQueryParameter("excessinsurance", uploadExcessInsurance)
                        .appendQueryParameter("timestampexcessinsurance", timestamp5)
                        .appendQueryParameter("selecteditemoneofthefollowing", strOneofThefollowing)
                        .appendQueryParameter("oneofthefollowing", OneofTheFollowing)
                        .appendQueryParameter("timestamponeofthefollowing", timestamp6)
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
//                Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(IndividualsCorporationPartnership.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(IndividualsCorporationPartnership.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(IndividualsCorporationPartnership.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public class UploadImagewithoutexcessinsurance extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(IndividualsCorporationPartnership.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/individuals_applicant.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadgovtissued = getStringImage(bitmap1);
                String uploadallcoprate = getStringImage(bitmap2);
                String uploadshareholderpartner = getStringImage(bitmap3);
                String uploadforhire = getStringImage(bitmap4);
                String OneofTheFollowing = getStringImage(bitmap6);
//                String uploadIRS = getStringImage(bitmap7);
//                String uploadIRS = getStringImage(bitmap8);
//                String uploadIRS = getStringImage(bitmap9);
//                String uploadIRS = getStringImage(bitmap10);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Log.d("" , "userr" +user);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("govtissued", uploadgovtissued)
                        .appendQueryParameter("timestampgovtissued", timestamp1)
                        .appendQueryParameter("allcorporate", uploadallcoprate)
                        .appendQueryParameter("timestampallcorporate", timestamp2)
                        .appendQueryParameter("shareholderandpartner", uploadshareholderpartner)
                        .appendQueryParameter("timestampshareholderandpartner", timestamp3)
                        .appendQueryParameter("forhire", uploadforhire)
                        .appendQueryParameter("timestampforhire", timestamp4)
                        .appendQueryParameter("selecteditemoneofthefollowing", strOneofThefollowing)
                        .appendQueryParameter("oneofthefollowing", OneofTheFollowing)
                        .appendQueryParameter("timestamponeofthefollowing", timestamp6)
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

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                dailog.dismiss();
                Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IndividualsCorporationPartnership.this,SelectOptionNavigation.class);
                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(IndividualsCorporationPartnership.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(IndividualsCorporationPartnership.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    public void excessInsurance()
    {
        if(ExcessInsuranceImageView.getDrawable()==null)
        {
            new UploadImagewithoutexcessinsurance().execute();
        }
        else
        {
            new UploadImagewithexcessinsurance().execute();
        }
    }



    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(IndividualsCorporationPartnership.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {



                GMailSender sender = new GMailSender(fromEmail, password);
                sender.addAttachment(Environment.getExternalStorageDirectory()+GovtImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+AllCorporateImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ShareholderImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ForHireImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ExcessInsuranceImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ImagePath);
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
                                    IndividualsCorporationPartnership.this.finish();
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
                Intent intent = new Intent(IndividualsCorporationPartnership.this,ThankYouScreen.class);
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
