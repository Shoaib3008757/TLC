package com.ranglerz.tlc.tlc;

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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.ReportAccident.ReportAccidentForm;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText userName,passWord;
    TextView ForgetPassWord;
    TextInputLayout UsernameWrapper , PassordWrapper;
    Button loginbtn;
    TextView LoginForm;
    TlcDatabase tlcDatabase;
    public ProgressDialog dailog;
    SharedPreferences sharedPreferences , sharedPreferencesEmail;
    String emailkey = "emailkey";
    String userID = "userID";
    String name , pass ;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String status;
    String CommmonUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createNetErrorDialog();
        tlcDatabase = new TlcDatabase(this);
        userName =(EditText)findViewById(R.id.emailaddress);
        passWord = (EditText)findViewById(R.id.password);
        ForgetPassWord = (TextView) findViewById(R.id.forgetpassword);
        loginbtn = (Button)findViewById(R.id.signin);
        LoginForm = (TextView)findViewById(R.id.loginform);
        UsernameWrapper = (TextInputLayout) findViewById(R.id.emailaddressWrapper);
        PassordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                name=  userName.getText().toString();
                pass =  passWord.getText().toString();


//                String pass = tlcDatabase.verification(userName.getText().toString());
//                Log.d("Userpassword", pass);
//
                if(!emailValidator(name))
                {
                    userName.setError("Please Enter Valid Email Address");
                }
                else if(passWord.getText().toString().equals("") || passWord.length() <= 5)
                {
                    passWord.setError("Please enter Password atleast 6 Cherecter");
                }
                else
                {

                    new loginTask().execute(name , pass);
                    userName.setText("");
                    passWord.setText("");

                }



                //Toast.makeText(Login.this , "Login successfully!" , Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(Login.this,SelectOptionNavigation.class);
//                startActivity(intent);

//                else {
//                    if (passWord.getText().toString().equals(pass)) {
//                       Intent intent = new Intent(Login.this, SelectOption.class);
//                        startActivity(intent);

//                        userName.setText("");
//                        passWord.setText("");

//                    }
//                    else {
//                        Toast.makeText(Login.this, "username and password incorrect", Toast.LENGTH_LONG).show();
//
//                    }
//                    }

      }
        });


        ForgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , ForgetPassword.class);
                startActivity(intent);
            }
        });

    }
    public class loginTask extends AsyncTask<String , Void ,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dailog = new ProgressDialog(Login.this);
            dailog.setTitle("Please Wait .... ");
            dailog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://www.tlcapp.nyc/admin/services/login.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", strings[0])
                        .appendQueryParameter("password", strings[1]);
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

                Log.d("tag", "doInBackground: Response is "+response_code);
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

                    Log.d("tag", "doInBackground: Response is result"+result.toString());
                    // Pass data to onPostExecute method
                    try {
                        String abc = result.toString();
                        jsonObject = new JSONObject(abc);
                        status = jsonObject.getString("status");
                        CommmonUserid = jsonObject.getString("user_id");

                        Log.d("sdfjad" ,"abc 1 " +status);
                        Log.d("adsfads" ,"abc 2 " +CommmonUserid);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //return(result.toString());
                    return status;

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
                Toast.makeText(Login.this, "Server not Responding", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity  when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                dailog.dismiss();

                sharedPreferencesEmail = getSharedPreferences("email" , 1);
                sharedPreferences = getSharedPreferences("getuserid" , 2);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SharedPreferences.Editor editorEmail = sharedPreferencesEmail.edit();
                Log.d("name" , "name"+ name);
                editorEmail.putString(emailkey , name);
                editor.putString(userID , CommmonUserid);
                editor.commit();
                editorEmail.commit();




                Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this,SelectOptionNavigation.class);
                startActivity(intent);
                finish();
                //Login.this.finish();

            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                dailog.dismiss();
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            }
            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();
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
                                    Login.this.finish();
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
        createNetErrorDialog();
    }


}
