package com.example.studentsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseStudentet extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="studentet.db";
    private static final String TABLE_NAME="users";
    private static final String COL_NAME="name";
    private static final String COL_ID="user";
    private static final String COL_EMAIL="email";
    private static final String COL_PASS="pass";
    private static final String COL_SALT="salt";
    private static final String COL_PRIORITETI="tipi";
   SQLiteDatabase db;
    private static final String TABLE_CREATE="CREATE TABLE users(user integer PRIMARY KEY NOT NULL, name TEXT NOT NULL, "+
            "email TEXT NOT NULL, pass TEXT NOT NULL, salt TEXT NOT NULL, tipi TEXT CHECK(tipi IN('s','a')) NOT NULL DEFAULT 's');";

    public DatabaseStudentet(Context context){
     super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(TABLE_CREATE);
      this.db=db;
    }

    public void insertUser(UsersStudentet u){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_NAME, u.getNameUsers());
        values.put(COL_ID, u.getUsernameUsers());
        values.put(COL_EMAIL, u.getEmailUsers());
        values.put(COL_PASS, u.getPasswordUsers());
        values.put(COL_SALT,u.getSaltUsers());
        values.put(COL_PRIORITETI,u.getPrioritetiUsers());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public String seatchPass(String user){
        db=this.getWritableDatabase();
        String query="Select user, pass from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="nuk ekzsiton";
        if(cursor.moveToFirst()){
         do{
             a=cursor.getString(0);
             if(a.equals(user)){
                 b=cursor.getString(1);
                 break;
             }
         }while (cursor.moveToNext());
        }
        return b;
    }

    public String searchSalt(String user){
        db=this.getWritableDatabase();
        String query="Select user, salt from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="nuk ekziston";
        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.equals(user)){
                    b=cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }

    public String searchUser(String user){
        db=this.getWritableDatabase();
        String query="Select user from users";
        Cursor c=db.rawQuery(query,null);
        String a;
        a="0";
        if(c.moveToFirst()){
            do{
                a=c.getString(0);
                if(a.equals(user)){
                    a="1";
                    break;
                }
            }while (c.moveToNext());
        }
        return a;
    }

    public String prioriteti(String user){
        db=this.getWritableDatabase();
        String query="Select tipi from users Where user="+user;
        Cursor c=db.rawQuery(query,null);
        String tipi="s";
        if (c.moveToFirst()){
            do {
              tipi=c.getString(0);
            }while (c.moveToNext());
        }
        c.close();
        return tipi;
    }

    public void resetPass(String user,String pass,String salt){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Update users set pass='"+pass+"',"+"salt='"+salt+"' WHERE user='"+user+"'";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     String query="DROP TABLE IF EXISTS "+TABLE_NAME;
     db.execSQL(query);
     this.onCreate(db);
    }
}
