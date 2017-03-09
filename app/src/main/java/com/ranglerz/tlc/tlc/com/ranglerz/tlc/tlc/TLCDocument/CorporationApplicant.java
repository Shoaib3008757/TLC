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
import android.widget.Button;
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

public class CorporationApplicant extends AppCompatActivity {

    Button CertificateIncorporation , CoprateMinutes , IrsIssued  , Submit;
    ImageView CertificateIncorporationImageView , CoprateMinutesImageView , IrsIssuedImageView ;
    EditText AddComment;
    String strAddComment;
    private String userChoosenTask;
    public static int btnClicked=0;
    public static int CertificateIncorporationButton = 1;
    public static int CoprateMinutesButton = 2;
    public static int IrsIssuedButton = 3;

    private int REQUEST_CAMERA_CertificateIncorporation = 11;
    private int REQUEST_CAMERA_CoprateMinutes = 12;
    private int REQUEST_CAMERA_IrsIssued = 13;

    public int SELECT_FILE_CertificateIncorporation = 21;
    public int SELECT_FILE_CoprateMinutesu = 22;
    public int SELECT_FILE_IrsIssued = 23;

    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "Corporation Applicant";
    public String message = "";
    String Imagepath , fname;
    String CertificateImagepath , CorporateImagepath , IrsImagepath  ;
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    String userID = "userID";
    Uri imageUri;
    Bitmap thumbnail;
    Bitmap bitmap1 , bitmap2 , bitmap3;
    String timestamp1 , timestamp2 , timestamp3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporation_applicant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();

        CertificateIncorporation = (Button)findViewById(R.id.certificateofincorporation);
        CoprateMinutes = (Button)findViewById(R.id.corporateminutes);
        IrsIssued = (Button)findViewById(R.id.anirs);
        AddComment = (EditText) findViewById(R.id.corporationaddcoment);
        Submit = (Button)findViewById(R.id.submitcorporationapplicant);

        CertificateIncorporationImageView = (ImageView) findViewById(R.id.certificateofincorporationImageView);
        CoprateMinutesImageView = (ImageView) findViewById(R.id.corporateminutesImageView);
        IrsIssuedImageView = (ImageView) findViewById(R.id.anirsImageView);

        CertificateIncorporationImageView.setVisibility(View.GONE);
        CoprateMinutesImageView.setVisibility(View.GONE);
        IrsIssuedImageView.setVisibility(View.GONE);

        CertificateIncorporation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = CertificateIncorporationButton;
                selectImage();
            }
        });

        CoprateMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = CoprateMinutesButton;
                selectImage();
            }
        });

        IrsIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = IrsIssuedButton;
                selectImage();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strAddComment = AddComment.getText().toString();
                if(CertificateIncorporationImageView.getDrawable() == null) {

                    Toast.makeText(getApplicationContext() , "Please Upload Certificate of incorporation Image" , Toast.LENGTH_SHORT).show();
                }
                else if(CoprateMinutesImageView.getDrawable() == null){


                    Toast.makeText(getApplicationContext() , "Please Upload Corporate minutes listing....... Image" , Toast.LENGTH_SHORT).show();
                }
                else if(IrsIssuedImageView.getDrawable() == null) {

                    Toast.makeText(getApplicationContext() , "Please Upload IRS issued CP-575 Notice or a 147-C letter Image" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                    message = "User Email: " +email + "\nCorporation Applicant images";

                    new UploadImage().execute();

                }
            }
        });




    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public class UploadImage extends  AsyncTask<String , Void ,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CorporationApplicant.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/corporation_applicant.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                String uploadCertificate = getStringImage(bitmap1);
                String uploadCorporate = getStringImage(bitmap2);
                String uploadIRS = getStringImage(bitmap3);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Log.d("" , "userr" +user);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("certificate", uploadCertificate)
                        .appendQueryParameter("timestampcertificate", timestamp1)
                        .appendQueryParameter("corporate", uploadCorporate)
                        .appendQueryParameter("timestampcorporate", timestamp2)
                        .appendQueryParameter("irsissued", uploadIRS)
                        .appendQueryParameter("timestampirsissued", timestamp3)
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
                ///Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CorporationApplicant.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(CorporationApplicant.this, "Data not sent!", Toast.LENGTH_LONG).show();

            }else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(CorporationApplicant.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(CorporationApplicant.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {

                GMailSender sender = new GMailSender(fromEmail, password);
                sender.addAttachment(Environment.getExternalStorageDirectory()+CertificateImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+CorporateImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+IrsImagepath);
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
        if (ContextCompat.checkSelfPermission(CorporationApplicant.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(CorporationApplicant.this,
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
        if (ContextCompat.checkSelfPermission(CorporationApplicant.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(CorporationApplicant.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(CorporationApplicant.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(CorporationApplicant.this);

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
        if(btnClicked == CertificateIncorporationButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_CertificateIncorporation);
        }
        else if (btnClicked == CoprateMinutesButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_CoprateMinutesu);
        }
        else if (btnClicked == IrsIssuedButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_IrsIssued);
        }
    }

    private void cameraIntent()
    {
        if(btnClicked == CertificateIncorporationButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_CertificateIncorporation);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_CertificateIncorporation);
        }
        else if(btnClicked == CoprateMinutesButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_CoprateMinutes);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_CoprateMinutes);
        }

        else if(btnClicked == IrsIssuedButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_IrsIssued);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_IrsIssued);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_CertificateIncorporation)
                onSelectFromGalleryResult(data , SELECT_FILE_CertificateIncorporation);
            else if (requestCode == REQUEST_CAMERA_CertificateIncorporation)
                onCaptureImageResult(data , REQUEST_CAMERA_CertificateIncorporation);
            else if (requestCode == SELECT_FILE_CoprateMinutesu)
                onSelectFromGalleryResult(data , SELECT_FILE_CoprateMinutesu);
            else if (requestCode == REQUEST_CAMERA_CoprateMinutes)
                onCaptureImageResult(data , REQUEST_CAMERA_CoprateMinutes);
            else if (requestCode == SELECT_FILE_IrsIssued)
                onSelectFromGalleryResult(data , SELECT_FILE_IrsIssued);
            else if (requestCode == REQUEST_CAMERA_IrsIssued)
                onCaptureImageResult(data , REQUEST_CAMERA_IrsIssued);
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

        if(requestCode == REQUEST_CAMERA_CertificateIncorporation ) {

//            CertificateImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+CertificateImagepath);

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CertificateIncorporationImageView.setVisibility(View.VISIBLE);
            CertificateIncorporationImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();


//            CertificateIncorporationImageView.setVisibility(View.VISIBLE);
//            CertificateIncorporationImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_CoprateMinutes ) {

//            CorporateImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+CorporateImagepath);

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CoprateMinutesImageView.setVisibility(View.VISIBLE);
            CoprateMinutesImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();

//            CoprateMinutesImageView.setVisibility(View.VISIBLE);
//
//            CoprateMinutesImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_IrsIssued ) {

//            IrsImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+IrsImagepath);

            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            IrsIssuedImageView.setVisibility(View.VISIBLE);
            IrsIssuedImageView.setImageBitmap(bitmap3);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();

//            IrsIssuedImageView.setVisibility(View.VISIBLE);
//
//            IrsIssuedImageView.setImageBitmap(thumbnail);
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
        if(requestCode == SELECT_FILE_CertificateIncorporation) {

//            String gallarypath = GetFilePathFromDevice.getPath(CorporationApplicant.this, data.getData());
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
//            CertificateImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                CertificateIncorporationImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                CertificateIncorporationImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            CertificateIncorporationImageView.setVisibility(View.VISIBLE);
//            CertificateIncorporationImageView.setImageBitmap(bm);

        }
        if(requestCode == SELECT_FILE_CoprateMinutesu) {


//            String gallarypath = GetFilePathFromDevice.getPath(CorporationApplicant.this, data.getData());
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
//            CorporateImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                CoprateMinutesImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                CoprateMinutesImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            CoprateMinutesImageView.setVisibility(View.VISIBLE);
//            CoprateMinutesImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_IrsIssued) {


//            String gallarypath = GetFilePathFromDevice.getPath(CorporationApplicant.this, data.getData());
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
//            IrsImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();
            try {
                IrsIssuedImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                IrsIssuedImageView.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            IrsIssuedImageView.setVisibility(View.VISIBLE);
//            IrsIssuedImageView.setImageBitmap(bm);
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
                                    CorporationApplicant.this.finish();
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
                Intent intent = new Intent(CorporationApplicant.this,ThankYouScreen.class);
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
