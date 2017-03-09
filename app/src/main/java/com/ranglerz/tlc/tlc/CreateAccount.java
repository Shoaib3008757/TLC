package com.ranglerz.tlc.tlc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/*import com.google.api.translate.Translate;*/

import java.util.Locale;

public class CreateAccount extends AppCompatActivity {
    Button login,createAccount;
    Spinner spin;
    private String[] languages = { "Select Language","English", "Spanish", "Arabic", "Urdu" };
    SharedPreferences sharedPreferencesEmail, sharedPreferencesEnglish , sharedPreferencesSpanish ,
            sharedPreferencesArabic , sharedPreferencesUrdu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Configuration config = new Configuration();
        Locale locale;
        locale = new Locale("ar");
        config.locale =locale;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



          login=(Button)findViewById(R.id.loginbutton);
          createAccount=(Button)findViewById(R.id.createaccount);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(CreateAccount.this,RegistrationForm.class);
                startActivity(createIntent);

            }

            });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccount.this,Login.class);
                startActivity(intent);
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent=new Intent(CreateAccount.this , SelectLanguage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CreateAccount.this , SelectLanguage.class);
        startActivity(intent);

        sharedPreferencesEmail = getSharedPreferences("email" , 1);
        sharedPreferencesEnglish = getSharedPreferences("english" ,2);
        sharedPreferencesSpanish = getSharedPreferences("spanish" , 3);
        sharedPreferencesArabic = getSharedPreferences("arabic" , 4);
        sharedPreferencesUrdu = getSharedPreferences("urdu" , 5);
        SharedPreferences.Editor editorEmail = sharedPreferencesEmail.edit();
        SharedPreferences.Editor editorEnglish = sharedPreferencesEnglish.edit();
        SharedPreferences.Editor editorSpanish = sharedPreferencesSpanish.edit();
        SharedPreferences.Editor editorArabic = sharedPreferencesArabic.edit();
        SharedPreferences.Editor editorUrdu = sharedPreferencesUrdu.edit();
        editorEmail.clear();
        editorEnglish.clear();
        editorSpanish.clear();
        editorArabic.clear();
        editorUrdu.clear();
        editorEmail.commit();
        editorEnglish.commit();
        editorSpanish.commit();
        editorArabic.commit();
        editorUrdu.commit();


    }

}
