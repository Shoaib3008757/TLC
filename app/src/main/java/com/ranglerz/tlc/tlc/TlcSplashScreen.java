package com.ranglerz.tlc.tlc;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.Locale;

public class TlcSplashScreen extends Activity {
    SharedPreferences sharedPreferencesEmail, sharedPreferencesEnglish , sharedPreferencesSpanish ,
            sharedPreferencesArabic , sharedPreferencesUrdu;
    String emailkey = "emailkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_tcl_splash_screen);




        Thread timerThread = new Thread(){
            public void run(){
                try
                {
                    sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    sharedPreferencesEmail = getSharedPreferences("email" , 1);
                    sharedPreferencesEnglish = getSharedPreferences("english" , 2);
                    sharedPreferencesSpanish = getSharedPreferences("spanish" , 3);
                    sharedPreferencesArabic = getSharedPreferences("arabic" , 4);
                    sharedPreferencesUrdu = getSharedPreferences("urdu" , 5);

                    String loginData = sharedPreferencesEmail.getString(emailkey , "0");

                    int isEnglish = sharedPreferencesEnglish.getInt("sEnglish", 0);
                    int isSpanish = sharedPreferencesSpanish.getInt("sSpanish", 0);
                    int isArabic = sharedPreferencesArabic.getInt("sArabic", 0);
                    int isUrdu = sharedPreferencesUrdu.getInt("surdu", 0);

                    Log.d("sharedPrefrence" , "email " +loginData);
                    Log.d("sharedPrefrence" , "english " +isEnglish);
                    Log.d("sharedPrefrence" , "spanish " +isSpanish);
                    Log.d("sharedPrefrence" , "arabic " +isArabic);
                    Log.d("sharedPrefrence" , "urdu " +isUrdu);

                    if (!loginData.equals("0") && isEnglish != 0){
                        Configuration config = new Configuration();
                        Locale locale;
                        config.locale = Locale.ENGLISH;
                        getResources().updateConfiguration(config, null);

                        Intent intent = new Intent(TlcSplashScreen.this, SelectOptionNavigation.class);
                        startActivity(intent);
                        finish();

                        Log.d("language" , "isEnglish");

                    }
                    else if (!loginData.equals("0") && isSpanish != 0){
                        Configuration config = new Configuration();
                        Locale locale;
                        locale = new Locale("es");
                        config.locale =locale;
                        getResources().updateConfiguration(config, null);
                        Intent intent = new Intent(TlcSplashScreen.this, SelectOptionNavigation.class);
                        startActivity(intent);
                        finish();

                        Log.d("language" , "isSpanish");

                    }
                    else if (!loginData.equals("0") && isArabic != 0){
                        Configuration config = new Configuration();
                        Locale locale;
                        locale = new Locale("ar");
                        config.locale =locale;
                        getResources().updateConfiguration(config, null);
                        Intent intent = new Intent(TlcSplashScreen.this, SelectOptionNavigation.class);

                        startActivity(intent);
                        finish();

                        Log.d("language" , "isArabic");


                    }
                    else if (!loginData.equals("0") && isUrdu != 0 ){
                        Configuration config = new Configuration();
                        Locale locale;
                        locale = new Locale("ur");
                        config.locale =locale;
                        getResources().updateConfiguration(config, null);
                        Intent intent = new Intent(TlcSplashScreen.this, SelectOptionNavigation.class);
                        startActivity(intent);
                        finish();

                        Log.d("language" , "isUrdu");


                    }
                    else {


                    Intent intent = new Intent(TlcSplashScreen.this,SelectLanguage.class);
                    startActivity(intent);
                    finish();
                }
                }
            }
        };
        timerThread.start();



    }


}
