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

public class LLCApplicant extends AppCompatActivity {

    Button ArticleOrgnization , OperationAgreement , ListofMembers , Submit;
    ImageView ArticleOrgnizationImageView , OperationAgreementImageView , ListofMembersImageView;
    EditText AddComment;
    String strAddComment;

    private String userChoosenTask;
    public static int btnClicked=0;
    public static int ArticleOrgnizationButton = 1;
    public static int OperationAgreementButton = 2;
    public static int ListofMembersButton = 3;

    private int REQUEST_CAMERA_ArticleOrgnization = 11;
    private int REQUEST_CAMERA_OperationAgreement = 12;
    private int REQUEST_CAMERA_ListofMembers = 13;

    public int SELECT_FILE_ArticleOrgnization = 21;
    public int SELECT_FILE_OperationAgreement = 22;
    public int SELECT_FILE_ListofMembers = 23;

    public String toEmail = "tlcappnyc@gmail.com";
    public String fromEmail = "tlcapp16@gmail.com";
    public String password = "tlcapp@1234.";
    public String subject = "LLC Applicant";
    public String message = "";
    String Imagepath , fname;

    String ArticleImagepath , OperationImagepath , ListImagepath  ;
    SharedPreferences sharedPreferences;
    String emailkey = "emailkey";
    Uri imageUri;
    Bitmap thumbnail;
    Bitmap bitmap1 , bitmap2 , bitmap3 ;
    String timestamp1 , timestamp2 , timestamp3;
    String userID = "userID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llcapplicant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        checkWriteExternalPermission();
        checkReadExternalStoragePermission();

        ArticleOrgnization = (Button)findViewById(R.id.articleoforganization);
        OperationAgreement = (Button)findViewById(R.id.operationagreement);
        ListofMembers = (Button)findViewById(R.id.listofmembers);
        AddComment = (EditText) findViewById(R.id.llcaddcoment);
        Submit = (Button)findViewById(R.id.submitllcapplicant);

        ArticleOrgnizationImageView = (ImageView)findViewById(R.id.articleoforganizationImageView);
        OperationAgreementImageView = (ImageView)findViewById(R.id.operationagreementImageView);
        ListofMembersImageView = (ImageView)findViewById(R.id.listofmembersImageView);

        ArticleOrgnizationImageView.setVisibility(View.GONE);
        OperationAgreementImageView.setVisibility(View.GONE);
        ListofMembersImageView.setVisibility(View.GONE);



        ArticleOrgnization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = ArticleOrgnizationButton;
             selectImage();
            }
        });

        OperationAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = OperationAgreementButton;
            selectImage();
            }
        });

        ListofMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClicked = ListofMembersButton;
            selectImage();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strAddComment = AddComment.getText().toString();
                if(ArticleOrgnizationImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Articles of Organization Image" , Toast.LENGTH_SHORT).show();
                }
                else if(OperationAgreementImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload Operation Agreement Image" , Toast.LENGTH_SHORT).show();
                }
                else if(ListofMembersImageView.getDrawable() == null)
                {
                    Toast.makeText(getApplicationContext() , "Please Upload List of Members Image" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences = getSharedPreferences("email" , 1);
                    String email = sharedPreferences.getString(emailkey , null);

                        message = "User Email: " +email + "\nLLC Applicant Images";

                   // new SendEmail().execute();
                    new UploadImage().execute();
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

    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LLCApplicant.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LLCApplicant.this,
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
        if (ContextCompat.checkSelfPermission(LLCApplicant.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LLCApplicant.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(LLCApplicant.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(LLCApplicant.this);

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
        if(btnClicked == ArticleOrgnizationButton) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ArticleOrgnization);
        }
        else if (btnClicked == OperationAgreementButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_OperationAgreement);
        }
        else if (btnClicked == ListofMembersButton)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ListofMembers);
        }
    }

    private void cameraIntent()
    {
        if(btnClicked == ArticleOrgnizationButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ArticleOrgnization);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ArticleOrgnization);
        }
        else if(btnClicked == OperationAgreementButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_OperationAgreement);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_OperationAgreement);
        }

        else if(btnClicked == ListofMembersButton) {

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA_ListofMembers);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CAMERA_ListofMembers);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_ArticleOrgnization)
                onSelectFromGalleryResult(data , SELECT_FILE_ArticleOrgnization);
            else if (requestCode == REQUEST_CAMERA_ArticleOrgnization)
                onCaptureImageResult(data , REQUEST_CAMERA_ArticleOrgnization);
            else if (requestCode == SELECT_FILE_OperationAgreement)
                onSelectFromGalleryResult(data , SELECT_FILE_OperationAgreement);
            else if (requestCode == REQUEST_CAMERA_OperationAgreement)
                onCaptureImageResult(data , REQUEST_CAMERA_OperationAgreement);
            else if (requestCode == SELECT_FILE_ListofMembers)
                onSelectFromGalleryResult(data , SELECT_FILE_ListofMembers);
            else if (requestCode == REQUEST_CAMERA_ListofMembers)
                onCaptureImageResult(data , REQUEST_CAMERA_ListofMembers);
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

        if(requestCode == REQUEST_CAMERA_ArticleOrgnization ) {


//            ArticleImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ArticleImagepath);

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArticleOrgnizationImageView.setVisibility(View.VISIBLE);
            ArticleOrgnizationImageView.setImageBitmap(bitmap1);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();


//            ArticleOrgnizationImageView.setVisibility(View.VISIBLE);
//
//            ArticleOrgnizationImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_OperationAgreement ) {

//            OperationImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+OperationImagepath);

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            OperationAgreementImageView.setVisibility(View.VISIBLE);
            OperationAgreementImageView.setImageBitmap(bitmap2);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();


//            OperationAgreementImageView.setVisibility(View.VISIBLE);
//
//            OperationAgreementImageView.setImageBitmap(thumbnail);
        }
        else if(requestCode == REQUEST_CAMERA_ListofMembers ) {

//            ListImagepath = "/Pictures/TLC/"+fname;
//            Log.d("afdff", "onCaptureImageResult: "+ListImagepath);

            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ListofMembersImageView.setVisibility(View.VISIBLE);
            ListofMembersImageView.setImageBitmap(bitmap3);
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();


//            ListofMembersImageView.setVisibility(View.VISIBLE);
//
//            ListofMembersImageView.setImageBitmap(thumbnail);
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
        if(requestCode == SELECT_FILE_ArticleOrgnization) {


//            String gallarypath = GetFilePathFromDevice.getPath(LLCApplicant.this, data.getData());
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
//            ArticleImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();
            try {
                ArticleOrgnizationImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ArticleOrgnizationImageView.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }


//            ArticleOrgnizationImageView.setVisibility(View.VISIBLE);
//
//            ArticleOrgnizationImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_OperationAgreement) {

//            String gallarypath = GetFilePathFromDevice.getPath(LLCApplicant.this, data.getData());
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
//            OperationImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();
            try {
                OperationAgreementImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                OperationAgreementImageView.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

//
//            OperationAgreementImageView.setVisibility(View.VISIBLE);
//
//            OperationAgreementImageView.setImageBitmap(bm);
        }
        if(requestCode == SELECT_FILE_ListofMembers) {

//            String gallarypath = GetFilePathFromDevice.getPath(LLCApplicant.this, data.getData());
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
//            ListImagepath =finalpath +  pathback + abc;
//            Log.d("ReportAccidentForm", "result final path" + Imagepath );

            Uri image = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();
            try {
                ListofMembersImageView.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ListofMembersImageView.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ListofMembersImageView.setVisibility(View.VISIBLE);
//
//            ListofMembersImageView.setImageBitmap(bm);
        }
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
            dailog = new ProgressDialog(LLCApplicant.this);
            dailog.setTitle("Please Wait.... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                //URL url = new URL("http://10.0.3.2/login/Registration.php");
                URL url = new URL("http://www.tlcapp.nyc/admin/services/llc_applicant.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                String uploadarticles = getStringImage(bitmap1);
                String uploadoperation = getStringImage(bitmap2);
                String uploadlistofmember = getStringImage(bitmap3);

                sharedPreferences = getSharedPreferences("getuserid" , 2);
                String user = sharedPreferences.getString(userID , null);

                Log.d("" , "userr" +user);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", user)
                        .appendQueryParameter("articles", uploadarticles)
                        .appendQueryParameter("timestamparticles", timestamp1)
                        .appendQueryParameter("operation", uploadoperation)
                        .appendQueryParameter("timestampoperation", timestamp2)
                        .appendQueryParameter("listofmember", uploadlistofmember)
                        .appendQueryParameter("timestamplistofmember", timestamp3)
                        .appendQueryParameter("addcomment", strAddComment);

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
//                Intent intent = new Intent(LLCApplicant.this,SelectOptionNavigation.class);
//                startActivity(intent);
                //Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                dataNotSent();
                //Toast.makeText(LLCApplicant.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                dailog.dismiss();
                Toast.makeText(LLCApplicant.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



    public class SendEmail extends AsyncTask<Void,Void,String>
    {
        public ProgressDialog dailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailog = new ProgressDialog(LLCApplicant.this);
            dailog.setTitle("Sending Email .... ");
            dailog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            String data = "test";
            try {

                GMailSender sender = new GMailSender(fromEmail, password);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ArticleImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+OperationImagepath);
                sender.addAttachment(Environment.getExternalStorageDirectory()+ListImagepath);

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
                                    LLCApplicant.this.finish();
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
                Intent intent = new Intent(LLCApplicant.this,ThankYouScreen.class);
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
