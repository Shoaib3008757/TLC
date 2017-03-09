package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;

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

public class FleetManagement extends AppCompatActivity {

    private int REQUEST_CAMERA_LICENSE = 200;
    private int REQUEST_CAMERA_BASELETTER = 201;

    public int SELECT_FILE_LICENSE = 100;
    public int SELECT_FILE_BASELETTER = 101;

    private String userChoosenTask;

    public static int licenseButton = 1;
    public static int baseLetterButton = 2;

    public static int btnClicked=0;

    Button takePictureOfLicense,submit,takePictureOfBaseLetter , Add_Driver , Remove_Driver ,Current_DriverList , AddVehicle  , RemoveVehicle , List_Of_Vehicle_Vin_Numbers ;
    EditText proofOfAddress;
    ImageView takePictureLicense,takePictureBaseLetter;

    SharedPreferences sharedPreferences;
    String userID = "userID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        takePictureOfLicense = (Button)findViewById(R.id.takePictureLicenseUpdateReplaceDriver);
//        takePictureOfBaseLetter = (Button)findViewById(R.id.takePictureBaseLetterUpdateReplaceDriver);
//        submit = (Button)findViewById(R.id.submitUpdateReplaceDriver);
//        proofOfAddress = (EditText)findViewById(R.id.proofAddressUpdateReplaceDriver);
//        takePictureLicense = (ImageView)findViewById(R.id.takePictureLicenseUpdateReplaceDriverImageView);
//        takePictureBaseLetter = (ImageView)findViewById(R.id.takePictureBaseLetterUpdateReplaceDriverImageView);

//        takePictureOfLicense.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                selectImage();
//                btnClicked = licenseButton;
//
//            }
//        });
//        takePictureOfBaseLetter.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                selectImage();
//                btnClicked = baseLetterButton;
//            }
//        });
        Add_Driver = (Button)findViewById(R.id.adddriver);
        //Remove_Driver = (Button)findViewById(R.id.removedriver);
        Current_DriverList = (Button)findViewById(R.id.crruntdriverlist);
        AddVehicle = (Button)findViewById(R.id.addvehicle);
        //RemoveVehicle = (Button)findViewById(R.id.removevehicle);
        List_Of_Vehicle_Vin_Numbers = (Button)findViewById(R.id.listofvehiclevinnum);

        Add_Driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FleetManagement.this , AddDriver.class);
                startActivity(intent);
            }
        });


//        Remove_Driver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(FleetManagement.this , RemoveDriver.class);
//                startActivity(intent);
//            }
//        });

        AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FleetManagement.this , AddVehicle.class);
                startActivity(intent);

            }
        });
//        RemoveVehicle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(FleetManagement.this , RemoveVehicle.class);
//                startActivity(intent);
//
//            }
//        });
        Current_DriverList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FleetManagement.this , CurrentDriverList.class);
                startActivity(intent);

                //new getDriverData().execute();

            }
        });

        List_Of_Vehicle_Vin_Numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FleetManagement.this , ListofVehicleandVinNumbers.class);
                startActivity(intent);

                //new getAddVehicleData().execute();

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

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(userChoosenTask.equals("Take Photo"))
//                        cameraIntent();
//                    else if(userChoosenTask.equals("Choose from Phone Storage"))
//                       galleryIntent();
//                } else {
//                    //code for deny
//                }
//                break;
//        }
//    }
//
//    private void selectImage() {
//        final CharSequence[] items = { "Take Photo", "Choose from Phone Storage",
//                "Cancel" };
//
//        AlertDialogBox.Builder builder = new AlertDialogBox.Builder(FleetManagement.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result= Utility.checkPermission(FleetManagement.this);
//
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
//                    if(result) {
//
//                        cameraIntent();
//                    }
//                } else if (items[item].equals("Choose from Phone Storage")) {
//                    userChoosenTask ="Choose from Phone Storage";
//                    if(result) {
//                        galleryIntent();
//                    }
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void galleryIntent()
//    {
//        if(btnClicked == licenseButton)
//        {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);//
//            startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE_LICENSE);
//
//        }
//        else if(btnClicked == baseLetterButton)
//        {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);//
//            startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE_BASELETTER);
//
//        }
//
//    }
//
//    private void cameraIntent()
//    {
//if(btnClicked == licenseButton)
//{
//    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    startActivityForResult(intent, REQUEST_CAMERA_LICENSE);
//}
//        else if(btnClicked == baseLetterButton)
//{
//    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    startActivityForResult(intent, REQUEST_CAMERA_BASELETTER);
//
//}
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE_LICENSE)
//                onSelectFromGalleryResult(data,SELECT_FILE_LICENSE);
//            else if(requestCode == SELECT_FILE_BASELETTER)
//                onSelectFromGalleryResult(data,SELECT_FILE_BASELETTER);
//            else if (requestCode == REQUEST_CAMERA_LICENSE)
//                onCaptureImageResult(data,REQUEST_CAMERA_LICENSE);
//            else if(requestCode == REQUEST_CAMERA_BASELETTER)
//                onCaptureImageResult(data,REQUEST_CAMERA_BASELETTER);
//        }
//    }
//
//    private void onCaptureImageResult(Intent data , int requestCode ) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
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
//
//
//        if(requestCode == REQUEST_CAMERA_LICENSE)
//        {
//            takePictureLicense.setImageBitmap(thumbnail);
//        }
//        else if(requestCode == REQUEST_CAMERA_BASELETTER) {
//            takePictureBaseLetter.setImageBitmap(thumbnail);
//        }
//
//
//
//    }
//
//
//    @SuppressWarnings("deprecation")
//    private void onSelectFromGalleryResult(Intent data,int requestCode) {
//
//        Bitmap bm=null;
//        if (data != null) {
//            try {
//                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if(requestCode == SELECT_FILE_LICENSE)
//        {
//            takePictureLicense.setImageBitmap(bm);
//        }
//        else if(requestCode == SELECT_FILE_BASELETTER)
//        {
//            takePictureBaseLetter.setImageBitmap(bm);
//        }
//
//
//
//    }
//public class getAddVehicleData extends AsyncTask<String , Void ,String>
//{
//    public ProgressDialog dailog;
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        dailog = new ProgressDialog(FleetManagement.this);
//        dailog.setTitle("Please Wait.... ");
//        dailog.show();
//    }
//
//    @Override
//    protected String doInBackground(String... strings) {
//        HttpURLConnection connection = null;
//        try {
//
//            //URL url = new URL("http://10.0.3.2/login/Registration.php");
//            URL url = new URL("http://www.tlcapp.nyc/admin/services/get_vehicle_data.php");
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
//            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//
//            sharedPreferences = getSharedPreferences("getuserid" , 2);
//            String user = sharedPreferences.getString(userID , null);
//
//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("user_id", user)
//
//
//                    ;
//            String query = builder.build().getEncodedQuery().toString();
//
//            // Open connection for sending data
//            OutputStream os = connection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();
//            connection.connect();
//
//            int response_code = connection.getResponseCode();
//
//            Log.d("tag", "doInBackground: code is  "+response_code);
//            // Check if successful connection made
//            if (response_code == HttpURLConnection.HTTP_OK) {
//
//
//                // Read data sent from server
//                InputStream input = connection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                StringBuilder result = new StringBuilder();
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                }
//
//                Log.d("tag", "doInBackground: Response is "+result.toString());
//                // Pass data to onPostExecute method
//                return(result.toString());
//
//            }else{
//
//                return("unsuccessful");
//            }
//
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return "exception";
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            connection.disconnect();
//        }
//
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//
//        if(result.equalsIgnoreCase("true"))
//        {
//                /* Here launching another activity when login successful. If you persist login state
//                use sharedPreferences of Android. and logout button to clear sharedPreferences.
//                 */
//            dailog.dismiss();
//            Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//
//            //Login.this.finish();
//
//        }else if (result.equalsIgnoreCase("false")){
//
//            // If username and password does not match display a error message
//            dailog.dismiss();
//            Toast.makeText(FleetManagement.this, "Something wrong", Toast.LENGTH_LONG).show();
//
//        } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
//
//            dailog.dismiss();
//            Toast.makeText(FleetManagement.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
//
//        }
//    }
//
//}
//    public class getDriverData extends AsyncTask<String , Void ,String>
//    {
//        public ProgressDialog dailog;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dailog = new ProgressDialog(FleetManagement.this);
//            dailog.setTitle("Please Wait.... ");
//            dailog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            HttpURLConnection connection = null;
//            try {
//
//                //URL url = new URL("http://10.0.3.2/login/Registration.php");
//                URL url = new URL("http://www.tlcapp.nyc/admin/services/get_insurance_driver_data.php");
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setReadTimeout(15000);
//                connection.setConnectTimeout(15000);
//                connection.setRequestMethod("POST");
//                connection.setDoInput(true);
//                connection.setDoOutput(true);
//
//                sharedPreferences = getSharedPreferences("getuserid" , 2);
//                String user = sharedPreferences.getString(userID , null);
//
//                Uri.Builder builder = new Uri.Builder()
//                        .appendQueryParameter("user_id", user)
//
//
//                        ;
//                String query = builder.build().getEncodedQuery().toString();
//
//                // Open connection for sending data
//                OutputStream os = connection.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(query);
//                writer.flush();
//                writer.close();
//                os.close();
//                connection.connect();
//
//                int response_code = connection.getResponseCode();
//
//                Log.d("tag", "doInBackground: code is  "+response_code);
//                // Check if successful connection made
//                if (response_code == HttpURLConnection.HTTP_OK) {
//
//
//                    // Read data sent from server
//                    InputStream input = connection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    Log.d("tag", "doInBackground: Response is "+result.toString());
//                    // Pass data to onPostExecute method
//                    return(result.toString());
//
//                }else{
//
//                    return("unsuccessful");
//                }
//
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return "exception";
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                connection.disconnect();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            if(result.equalsIgnoreCase("true"))
//            {
//                /* Here launching another activity when login successful. If you persist login state
//                use sharedPreferences of Android. and logout button to clear sharedPreferences.
//                 */
//                dailog.dismiss();
//                Toast.makeText(getApplicationContext(),"Data Sent Successfully",Toast.LENGTH_SHORT).show();
//
//                //Login.this.finish();
//
//            }else if (result.equalsIgnoreCase("false")){
//
//                // If username and password does not match display a error message
//                dailog.dismiss();
//                Toast.makeText(FleetManagement.this, "Something wrong", Toast.LENGTH_LONG).show();
//
//            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
//
//                dailog.dismiss();
//                Toast.makeText(FleetManagement.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
//
//            }
//        }
//
//    }
}
