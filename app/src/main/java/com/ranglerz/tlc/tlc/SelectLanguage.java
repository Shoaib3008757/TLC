package com.ranglerz.tlc.tlc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class SelectLanguage extends AppCompatActivity {
    Button continuebtn;
    Spinner spin;
    Intent intent;
    int pos=0;
    SharedPreferences sharedPreferencesEnglish , sharedPreferencesSpanish , sharedPreferencesArabic , sharedPreferencesUrdu;
    private String[] languages = { "Select Language","English", "Spanish", "Arabic", "Urdu" };
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);




        spin=(Spinner)findViewById(R.id.spinner);

        selectLanguage();






    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        selectLanguage();
//    }
    public void selectLanguage()
    {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);



        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                Configuration config = new Configuration();
                Locale locale;
                switch (arg2) {
                    case 0:
                        break;
                    case 1:

                        int selectedPositionEng = spin.getSelectedItemPosition();
                        sharedPreferencesEnglish = getSharedPreferences("english" , 2);
                        SharedPreferences.Editor editorEnglish = sharedPreferencesEnglish.edit();
                        editorEnglish.putInt("sEnglish", selectedPositionEng);
                        editorEnglish.commit();
                        config.locale = Locale.ENGLISH;
                        intent = new Intent(SelectLanguage.this , CreateAccount.class);
                        startActivity(intent);

                        Log.d("case" , "English");


                        //spin.setSelection(pos);
                        finish();
                        break;
                    case 2:

                        int selectedPositionSpanish = spin.getSelectedItemPosition();
                        sharedPreferencesSpanish = getSharedPreferences("spanish" , 3);
                        SharedPreferences.Editor editorSpanish = sharedPreferencesSpanish.edit();
                        editorSpanish.putInt("sSpanish", selectedPositionSpanish);
                        editorSpanish.commit();
                        locale = new Locale("es");
                        config.locale =locale;
                        intent = new Intent(SelectLanguage.this , CreateAccount.class);
                        startActivity(intent);

                        Log.d("case" , "spanish");

                        //spin.setSelection(pos);
                        finish();
                        break;
                    case 3:

                        int selectedPositionArabic = spin.getSelectedItemPosition();
                        sharedPreferencesArabic = getSharedPreferences("arabic" , 4);
                        SharedPreferences.Editor editorarabic = sharedPreferencesArabic.edit();
                        editorarabic.putInt("sArabic", selectedPositionArabic);
                        editorarabic.commit();

                        locale = new Locale("ar");
                        config.locale =locale;
                        intent = new Intent(SelectLanguage.this , CreateAccount.class);
                        startActivity(intent);
                        Log.d("case" , "arabic");

                        //spin.setSelection(pos);
                        finish();
                        break;
                    case 4:

                        int selectedPositionUrdu = spin.getSelectedItemPosition();
                        sharedPreferencesUrdu = getSharedPreferences("urdu" , 5);
                        SharedPreferences.Editor editorudu = sharedPreferencesUrdu.edit();
                        editorudu.putInt("surdu", selectedPositionUrdu);
                        editorudu.commit();
                        locale = new Locale("ur");
                        config.locale =locale;
                        intent = new Intent(SelectLanguage.this , CreateAccount.class);
                        startActivity(intent);
                        Log.d("case" , "urdu");

                        //spin.setSelection(pos);
                        finish();
                        break;
                    default:
                        break;

                }

                getResources().updateConfiguration(config, null);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
