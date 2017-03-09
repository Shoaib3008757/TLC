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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.Globals;
import com.ranglerz.tlc.tlc.R;
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
import java.util.Locale;

    public class AddDriver extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    EditText DriverName , DriverLicence , TlcLicence , PolicyNumber , PlateNumber ,  StartDriving , TicketRecived , FleetName , AddComment;
    Button UtilityBill , BaseLetter  , DriverLicensebtn , TlcLicensebtn , Submit;
    ImageView UtilityBillImageView , BaseLetterImageView  , DriverLicenseImageView , TlcLicenseImageView;
    String strDriverName ,strDriverLicence ,strTlcLicence ,strPolicyNumber , strPlateNumber ,strStartDriving ,strTicketRecived , strFleetname , strAddComment;
    String UtilityBillImagePath , BaseLetterImagePath , DriverLicensebtnImagePath , TlcLicensebtnImagePath;
    public static int btnClicked=0;
    public static int UtilityBillsButton = 1;
    public static int BaseLetterButton = 2;
    public static int DriverLicenseButton = 3;
    public static int TlcLicenseButton = 4;

    private int REQUEST_CAMERA_UtilityBills = 11;
    private int REQUEST_CAMERA_BaseLetter = 12;
    private int REQUEST_CAMERA_DriverLicensebtn = 13;
    private int REQUEST_CAMERA_TlcLicensebtn = 14;

    public int SELECT_FILE_UtilityBills = 21;
    public int SELECT_FILE_BaseLetter = 22;
    public int SELECT_FILE_DriverLicensebtn = 23;
    public int SELECT_FILE_TlcLicensebtn = 24;

    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "Add Driver";
    public String message;
        SharedPreferences sharedPreferences;
        String emailkey = "emailkey";
        String userID = "userID";
        Uri imageUri;
        Bitmap thumbnail;
        Calendar myCalendar = Calendar.getInstance();
        Bitmap bitmap1 ,bitmap2 , bitmap3 , bitmap4;
        String timestamp1 , timestamp2 , timestamp3 , timestamp4;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();

        DriverName = (EditText)findViewById(R.id.drivername);
        DriverLicence = (EditText)findViewById(R.id.driverlicense);
        TlcLicence = (EditText)findViewById(R.id.tlclicence);
        PolicyNumber = (EditText)findViewById(R.id.policynumber);
        PlateNumber = (EditText)findViewById(R.id.platanum);
        StartDriving = (EditText)findViewById(R.id.startdriving);
        TicketRecived = (EditText)findViewById(R.id.numberofticket);
        FleetName = (EditText)findViewById(R.id.fleetname);
        AddComment = (EditText)findViewById(R.id.adddriveraddcoment);


        UtilityBill= (Button)findViewById(R.id.utilitybills);
        BaseLetter = (Button)findViewById(R.id.baseletter);
        DriverLicensebtn = (Button)findViewById(R.id.driverlicenseadddriver);
        TlcLicensebtn = (Button)findViewById(R.id.tlclicenceadddriver);
        Submit = (Button)findViewById(R.id.submitadddriver);


//        Endorsmentform = (Button)findViewById(R.id.endorsmentform);

        UtilityBillImageView = (ImageView)findViewById(R.id.utilitybillsImageView);
        BaseLetterImageView = (ImageView)findViewById(R.id.baseletterImageView);
        DriverLicenseImageView = (ImageView)findViewById(R.id.driverlicenseadddriverImageView);
        TlcLicenseImageView = (ImageView)findViewById(R.id.tlclicenceadddriverImageView);

//        EndorsmentformImageView = (ImageView)findViewById(R.id.endorsmentformImageView);

        UtilityBillImageView.setVisibility(View.GONE);
        BaseLetterImageView.setVisibility(View.GONE);
        DriverLicenseImageView.setVisibility(View.GONE);
        TlcLicenseImageView.setVisibility(View.GONE);


        UtilityBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = UtilityBillsButton;
            }
        });

        BaseLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            selectImage();
                btnClicked = BaseLetterButton;
            }
        });
        DriverLicensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = DriverLicenseButton;
            }
        });
        TlcLicensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                btnClicked = TlcLicenseButton;
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


            StartDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddDriver.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strDriverName =DriverName.getText().toString();
                strTlcLicence =TlcLicence.getText().toString();
                strDriverLicence =DriverLicence.getText().toString();
                strPolicyNumber =PolicyNumber.getText().toString();
                strPlateNumber =PlateNumber.getText().toString();
                strFleetname =FleetName.getText().toString();
                strStartDriving =StartDriving.getText().toString();
                strTicketRecived =TicketRecived.getText().toString();
                strAddComment = AddComment.getText().toString();
                Log.d("driver" , "" +strDriverName);
                Log.d("driver" , "" +strDriverLicence);

                if(strDriverName.equals(""))
                {
                    DriverName.setError("Please Enter Driver Name");
                }
                else if(strDriverLicence.equals(""))
                {
                    DriverLicence.setError("Please Enter Driver License");
                }
                else if(strTlcLicence.equals(""))
                {
                    TlcLicence.setError("Please Enter TLC License");
                }
                else if(strPlateNumber.equals(""))
                {
                    PlateNumber.setError("Please Enter Plate Number License");
                }
                else if(strPolicyNumber.equals(""))
                {
                    PolicyNumber.setError("Please Enter Policy Number");
                }
                else if(strFleetname.equals(""))
                {
                    FleetName.setError("Please Enter Corporation/Base... Number");
                }
                else if(strStartDriving.equals(""))
                {
                    StartDriving.setError("Please Enter When you start Driving");
                }
                else if(strTicketRecived.equals(""))
                {
                    TicketRecived.setError("Please Enter Number of Tickets Received");
                }
                else if(UtilityBillImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Utility BillI Image" , Toast.LENGTH_SHORT).show();
                }
                else if(BaseLetterImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Base Letter Image" , Toast.LENGTH_SHORT).show();
                }
                else if(DriverLicenseImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Driver License Image" , Toast.LENGTH_SHORT).show();
                }
                else if(TlcLicenseImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload TLC License Image" , Toast.LENGTH_SHORT).show();
                }
                else
                {



                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email +"\n\nDriver Name: " + strDriverName + "\nDriver License: " + strDriverLicence + "\nTLC License: " + strTlcLicence +
                                "\nPolicy Number: " + strPolicyNumber + "\nStart Driving: " + strStartDriving + "\nTicket Received: " + strTicketRecived ;

                    //new SendEmail().execute();
                    new AddDriverAsyncTask().execute();
                    Log.d("" , "driver" +strDriverName);
                    Log.d("" , "driver" +strDriverLicence);
                }

            }
        });


    }

        private void updateLabel() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            StartDriving.setText(sdf.format(myCalendar.getTime()));
        }

        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

        public class AddDriverAsyncTask extends  AsyncTask<String , Void ,String>
        {
            public ProgressDialog dailog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog = new ProgressDialog(AddDriver.this);
                dailog.setTitle("Please Wait.... ");
                dailog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                HttpURLConnection connection = null;
                try {

                    //URL url = new URL("http://10.0.3.2/login/Registration.php");
                    URL url = new URL("http://www.tlcapp.nyc/admin/services/insurance_add_driver.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(15000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    String uploadUtilityBills = getStringImage(bitmap1);
                    String uploadBaseLetter = getStringImage(bitmap2);
                    String uploadDriverLicense = getStringImage(bitmap3);
                    String uploadTlcLicense = getStringImage(bitmap4);
                    sharedPreferences = getSharedPreferences("getuserid" , 2);
                    String user = sharedPreferences.getString(userID , null);
                    Uri.Builder builder = new Uri.Builder()

                            .appendQueryParameter("user_id", user)
                            .appendQueryParameter("drivername", strDriverName)
                            .appendQueryParameter("driverlicense", strDriverLicence)
                            .appendQueryParameter("tlclicense", strTlcLicence)
                            .appendQueryParameter("platenumber", strPlateNumber)
                            .appendQueryParameter("policynumber", strPolicyNumber)
                            .appendQueryParameter("fleetname", strFleetname)
                            .appendQueryParameter("startdriving", strStartDriving)
                            .appendQueryParameter("ticketrecived", strTicketRecived)
                            .appendQueryParameter("addcomment", strAddComment)
                            .appendQueryParameter("utilitybills", uploadUtilityBills)
                            .appendQueryParameter("timestamputilitybills", timestamp1)
                            .appendQueryParameter("baseletter", uploadBaseLetter)
                            .appendQueryParameter("timestampbaseletter", timestamp2)
                            .appendQueryParameter("driverlicenseimg", uploadDriverLicense)
                            .appendQueryParameter("timestampdriverlicense", timestamp3)
                            .appendQueryParameter("tlclicenseimg", uploadTlcLicense)
                            .appendQueryParameter("timestamptlclicense", timestamp4)




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
//                    Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddDriver.this,SelectOptionNavigation.class);
//                    startActivity(intent);
                    //Login.this.finish();

                }else if (result.equalsIgnoreCase("false")){

                    // If username and password does not match display a error message
                    dailog.dismiss();
                    dataNotSent();
                   // Toast.makeText(AddDriver.this, "Data not sent!", Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    dailog.dismiss();
                    Toast.makeText(AddDriver.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }

        }




        public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(AddDriver.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {

                GMailSender sender = new GMailSender(fromEmail, password);
                sender.addAttachment(Environment.getExternalStorageDirectory()+UtilityBillImagePath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+BaseLetterImagePath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+DriverLicensebtnImagePath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+TlcLicensebtnImagePath);
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

    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(AddDriver.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(AddDriver.this,
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
        if (ContextCompat.checkSelfPermission(AddDriver.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(AddDriver.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(AddDriver.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(AddDriver.this);

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
        if(btnClicked == UtilityBillsButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_UtilityBills);
        }
        else if (btnClicked == BaseLetterButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_BaseLetter);
        }
        else if (btnClicked == DriverLicenseButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_DriverLicensebtn);
        }
        else if (btnClicked == TlcLicenseButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_TlcLicensebtn);
        }
    }

    private void cameraIntent()
    {
        if(btnClicked == UtilityBillsButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_UtilityBills);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_UtilityBills);
        }
        else if(btnClicked == BaseLetterButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_BaseLetter);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_BaseLetter);
        }
        else if(btnClicked == DriverLicenseButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_DriverLicensebtn);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_DriverLicensebtn);
        }
        else if(btnClicked == TlcLicenseButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_TlcLicensebtn);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_TlcLicensebtn);
        }

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_UtilityBills)
                onSelectFromGalleryResult(data , SELECT_FILE_UtilityBills);
            else if (requestCode == REQUEST_CAMERA_UtilityBills)
                onCaptureImageResult(data , REQUEST_CAMERA_UtilityBills);
            else if (requestCode == SELECT_FILE_BaseLetter)
                onSelectFromGalleryResult(data , SELECT_FILE_BaseLetter);
            else if (requestCode == REQUEST_CAMERA_BaseLetter)
                onCaptureImageResult(data , REQUEST_CAMERA_BaseLetter);
            else if (requestCode == SELECT_FILE_DriverLicensebtn)
                onSelectFromGalleryResult(data , SELECT_FILE_DriverLicensebtn);
            else if (requestCode == REQUEST_CAMERA_DriverLicensebtn)
                onCaptureImageResult(data , REQUEST_CAMERA_DriverLicensebtn);
            else if (requestCode == SELECT_FILE_TlcLicensebtn)
                onSelectFromGalleryResult(data , SELECT_FILE_TlcLicensebtn);
            else if (requestCode == REQUEST_CAMERA_TlcLicensebtn)
                onCaptureImageResult(data , REQUEST_CAMERA_TlcLicensebtn);
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
//        String fname = "Image-" + n + ".jpg";
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

        if(requestCode == REQUEST_CAMERA_UtilityBills ) {

//            UtilityBillImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+UtilityBillImagePath);
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UtilityBillImageView.setVisibility(View.VISIBLE);
            UtilityBillImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();

//            UtilityBillImageView.setVisibility(View.VISIBLE);
//
//            UtilityBillImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_BaseLetter ) {

//            BaseLetterImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+BaseLetterImagePath);
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BaseLetterImageView.setVisibility(View.VISIBLE);
            BaseLetterImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();

//            BaseLetterImageView.setVisibility(View.VISIBLE);
//
//            BaseLetterImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_DriverLicensebtn ) {

//            DriverLicensebtnImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+DriverLicensebtnImagePath);
            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DriverLicenseImageView.setVisibility(View.VISIBLE);
            DriverLicenseImageView.setImageBitmap(bitmap3);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();

//            DriverLicenseImageView.setVisibility(View.VISIBLE);
//
//            DriverLicenseImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_TlcLicensebtn ) {

//            TlcLicensebtnImagePath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+TlcLicensebtnImagePath);
            try {
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TlcLicenseImageView.setVisibility(View.VISIBLE);
            TlcLicenseImageView.setImageBitmap(bitmap4);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();

//            TlcLicenseImageView.setVisibility(View.VISIBLE);
//
//            TlcLicenseImageView.setImageBitmap(thumbnail);
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
        if(requestCode == SELECT_FILE_UtilityBills) {

//            String gallarypath = GetFilePathFromDevice.getPath(AddDriver.this, data.getData());
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
//            UtilityBillImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + UtilityBillImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                UtilityBillImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                UtilityBillImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            UtilityBillImageView.setVisibility(View.VISIBLE);
//
//            UtilityBillImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_BaseLetter) {


//            String gallarypath = GetFilePathFromDevice.getPath(AddDriver.this, data.getData());
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
//            BaseLetterImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + BaseLetterImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                BaseLetterImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                BaseLetterImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BaseLetterImageView.setVisibility(View.VISIBLE);

            BaseLetterImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_DriverLicensebtn) {

//            String gallarypath = GetFilePathFromDevice.getPath(AddDriver.this, data.getData());
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
//            DriverLicensebtnImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + DriverLicensebtnImagePath );


            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();
            try {
                DriverLicenseImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                DriverLicenseImageView.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }

            DriverLicenseImageView.setVisibility(View.VISIBLE);

            DriverLicenseImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_TlcLicensebtn) {

//            String gallarypath = GetFilePathFromDevice.getPath(AddDriver.this, data.getData());
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
//            TlcLicensebtnImagePath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + TlcLicensebtnImagePath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();
            try {
                TlcLicenseImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                TlcLicenseImageView.setImageBitmap(bitmap4);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TlcLicenseImageView.setVisibility(View.VISIBLE);

            TlcLicenseImageView.setImageBitmap(bm);
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
                                    AddDriver.this.finish();
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
                    Intent intent = new Intent(AddDriver.this,ThankYouScreen.class);
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
