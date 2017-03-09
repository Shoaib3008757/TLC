package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ReportAccident extends AppCompatActivity {


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    Button getSignature,takePictureAccidentReport,uploadFromGalleryAccidentReport, submit;
    ImageView showSignature,takePictureReportAccidentImageView;
    EditText AddComment , date,afterOfficeRepresents,afterOfficeResiding,socialSecurityNumber,fullName,Address,telePhoneNumber,faxNumber;
    TextView officeRepresents,officeResiding,evidenceText;
    String strAddcommnet;
    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "TLC Report Accident";
    public String message = "";
    String Imagepath , fname;
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    Uri imageUri;
    Bitmap thumbnail;
    Bitmap bitmap1;
    String timestamp1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_accident2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();

        checkWriteExternalPermission();
        checkReadExternalStoragePermission();

//        getSignature = (Button) findViewById(R.id.getSignatureReportAccident);
        AddComment = (EditText) findViewById(R.id.reportaddcoment);
        submit = (Button) findViewById(R.id.submitReportAccident);
        takePictureAccidentReport = (Button) findViewById(R.id.takePictureReportAccidentInsurance);
//        showSignature = (ImageView) findViewById(R.id.showSignature);
        takePictureReportAccidentImageView = (ImageView) findViewById(R.id.takePictureReportAccidentImageView);
//        date = (EditText) findViewById(R.id.dateReportAccident);
//        officeRepresents = (TextView) findViewById(R.id.representsTextReportAccident);
//        afterOfficeRepresents = (EditText) findViewById(R.id.representsReportAccident);
//        officeResiding = (TextView) findViewById(R.id.residingTextReportAccident);
//        afterOfficeResiding = (EditText) findViewById(R.id.residingReportAccident);
//        socialSecurityNumber = (EditText) findViewById(R.id.socialSecurityNumberReportAccident);
//        evidenceText = (TextView) findViewById(R.id.evidenceTextReportAccident);
//        fullName = (EditText) findViewById(R.id.fullnameClientReportAccident);
//        Address = (EditText) findViewById(R.id.addressReportAccident);
//        telePhoneNumber = (EditText) findViewById(R.id.telePhoneNumberReportAccident);
//        faxNumber = (EditText) findViewById(R.id.faxNumberReportAccident);

//        getSignature.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSignatureDialog();
//            }
//        });
        takePictureReportAccidentImageView.setVisibility(View.GONE);

        takePictureAccidentReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strAddcommnet = AddComment.getText().toString();
                if(takePictureReportAccidentImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Report Accident Image" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email + "\n\nReport Accident Image";

                    //new SendEmail().execute();
                    new Insurance_Report_Accident().execute();
                }


            }
        });





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



//    private void showSignatureDialog() {
//        final AlertDialogBox.Builder builder = new AlertDialogBox.Builder(this);
//        Button clear, save;
//
//        View view = getLayoutInflater().inflate(R.layout.signatureview, null);
//        LinearLayout mContent = (LinearLayout) view.findViewById(R.id.mysignature);
//        // pass the LinearLayout which contain Touch View
//        final CaptureSignature mSignature = new CaptureSignature(mContent,ReportAccident.this, null);
//        mContent.addView(mSignature);
//
//        // two buttons for clear and save canvas
//        save = (Button) view.findViewById(R.id.save);
//        clear = (Button) view.findViewById(R.id.clear);
//
////        TextView text = (TextView) view.findViewById(R.id.textViewHelpText);
////
////        text.setText(Html.fromHtml(helpText));
//
//        builder.setView(view);
//
//        // create alert dialog
//        final AlertDialogBox alertDialog = builder.create();
//
//
//        save.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                try {
//                    // save the bitmap and then display in ImageView
//                    showSignature.setImageBitmap( mSignature.save());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // clear the canvas
//                mSignature.clear();
//                // dimiss the dialog
//                alertDialog.dismiss();
//            }
//        });
//        clear.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                mSignature.clear();
//
//            }
//        });
//
//
//
//
//
//
//        alertDialog.show();
//    }






    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident.this,
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
        if (ContextCompat.checkSelfPermission(com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportAccident.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(ReportAccident.this);

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);



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
        try {
            //bitmap1 = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            takePictureReportAccidentImageView.setVisibility(View.VISIBLE);
            takePictureReportAccidentImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        takePictureReportAccidentImageView.setVisibility(View.VISIBLE);
//
//        takePictureReportAccidentImageView.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        String gallarypath = GetFilePathFromDevice.getPath(ReportAccident.this, data.getData());
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

        Log.d("", "onSelectFromGalleryResult: license file");

        Uri image = data.getData();
        Long tsLong = System.currentTimeMillis() / 1000;
        timestamp1 = tsLong.toString();
        try {
            takePictureReportAccidentImageView.setVisibility(View.VISIBLE);

            //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            takePictureReportAccidentImageView.setImageBitmap(bitmap1);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        takePictureReportAccidentImageView.setVisibility(View.VISIBLE);
//
//        takePictureReportAccidentImageView.setImageBitmap(bm);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public class Insurance_Report_Accident extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(ReportAccident.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/insurance_report_accident.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadReportAccident = getStringImage(bitmap1);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);
                Log.d("" , "userr" +user);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("accidentreport", uploadReportAccident)
                        .appendQueryParameter("timestampaccidentreport", timestamp1)
                        .appendQueryParameter("addcomment", strAddcommnet )




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
//                Intent intent = new Intent(ReportAccident.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(ReportAccident.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(ReportAccident.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }




    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(ReportAccident.this);
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
                                    ReportAccident.this.finish();
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
                Intent intent = new Intent(ReportAccident.this,ThankYouScreen.class);
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
