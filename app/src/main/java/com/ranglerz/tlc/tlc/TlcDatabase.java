package com.ranglerz.tlc.tlc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by Hafiz Adeel on 10/7/2016.
 */

public class TlcDatabase extends SQLiteOpenHelper {
    private static final String DATABASAE_NAME ="tlcDatabase";
    private static final String REGISTRATION_TABLE="registrationForm";
    private static final String TLC_INSURANCE_TABLE="tlcInsurance";
    private static final int DATABASE_VERSION= 6;



    public TlcDatabase(Context context) {
        super(context, DATABASAE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(" CREATE TABLE "+REGISTRATION_TABLE+"(ID INTEGER PRIMARY KEY AUTOINCREMENT , FULL_NAME TEXT, ADDRESS TEXT ,DOB TEXT , DRIVER_LICENSE_DMV_LICENSE TEXT , TLC_LICENSE TEXT , PHONE_NUMBER TEXT , EMAIL TEXT ,PASSWORD TEXT);");
    //db.execSQL("CREATE TABLE "+TLC_INSURANCE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , FULL_NAME TEXT, ADDRESS TEXT ,DOB TEXT , DRIVER_LICENSE_DMV_LICENSE TEXT , TLC_LICENSE TEXT , PHONE_NUMBER TEXT , EMAIL TEXT , VIN_NUMBER TAXT , SOCIAL_SECURITY_NUMBER TAXT , CAR_MAKE TAXT , CAR_MODEL , TAXT , CAR_YEAR TAXT , EIN_NUMBER TAXT , OWNER_NAME TAXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + REGISTRATION_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + TLC_INSURANCE_TABLE);
        onCreate(db);
    }
    //insert data in database from registration
    public boolean insertDataInRegistrationForm(String fullName , String address , String dob, String driverlicense , String tlcLicense , String  phone_number , String  email , String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FULL_NAME",fullName);
        contentValues.put("ADDRESS",address);
        contentValues.put("DOB",dob);
        contentValues.put("DRIVER_LICENSE_DMV_LICENSE",driverlicense);
        contentValues.put("TLC_LICENSE",tlcLicense);
        contentValues.put("PHONE_NUMBER",phone_number);
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);
        long result = db.insert(REGISTRATION_TABLE , null ,contentValues);
        db.close();

          if(result== -1)
            return false;
        else
            return true;

    }

//    public boolean insertDataInTlcInsurance(String fullName , String address , String dob, String driverlicense , String tlcLicense , String  phone_number , String  email , String vin_number , String social_security_number , String car_make , String car_model , String car_year , String ein_number , String ownername )
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("FULL_NAME",fullName);
//        contentValues.put("ADDRESS",address);
//        contentValues.put("DOB",dob);
//        contentValues.put("DRIVER_LICENSE_DMV_LICENSE",driverlicense);
//        contentValues.put("TLC_LICENSE",tlcLicense);
//        contentValues.put("PHONE_NUMBER",phone_number);
//        contentValues.put("EMAIL",email);
//        contentValues.put("VIN_NUMBER",vin_number);
//        contentValues.put("SOCIAL_SECURITY_NUMBER",social_security_number);
//        contentValues.put("CAR_MAKE", car_make);
//        contentValues.put("CAR_MODEL",car_model);
//        contentValues.put("CAR_YEAR",car_year);
//        contentValues.put("EIN_NUMBER",ein_number);
//        contentValues.put("OWNER_NAME",ownername);
//        long result = db.insert(TLC_INSURANCE_TABLE , null ,contentValues);
//
//
//
//        db.close();
//
//        if(result== -1)
//            return false;
//        else
//            return true;
//
//
//
//    }
    // use in login for signin
    public  String verification(String  username)
    {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT FULL_NAME , PASSWORD FROM "+REGISTRATION_TABLE ,null);
        String user ,pass;
        pass = "not found";

        if(cursor.moveToFirst())
        {
            do {
                user = cursor.getString(0);
                Log.d("geuser",user);
                if(user.equals(username))
                {
                    pass = cursor.getString(1);
                    Log.d("getpass",pass);
                    break;
                }

            }
            while(cursor.moveToNext());

        }

return pass;
    }
//in registration class for check username if already exist
    public String checkUserName(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT FULL_NAME FROM "+ REGISTRATION_TABLE ,null);
        String uname;
        uname ="";

        if(cursor.moveToFirst())
        {
            do {
                uname = cursor.getString(0);
                if(uname.equals(username))
                {

                    break;
                }

            }
            while (cursor.moveToNext());


        }


        return uname ;

    }
    //this function use in tlcInsurance class
//    public Cursor autoFill()
//    {
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + REGISTRATION_TABLE, null);
//
//
//        return cursor;
//
//    }



}
