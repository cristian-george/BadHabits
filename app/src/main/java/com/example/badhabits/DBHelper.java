package com.example.badhabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "badhabits.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users" + "(id integer primary key AUTOINCREMENT, username text, email text, password text)");
        sqLiteDatabase.execSQL("create table bad_habits" + "(id integer primary key AUTOINCREMENT, name text)");
        sqLiteDatabase.execSQL("create table user_habits" + "(id_user integer references users(id), id_habit integer references bad_habits(id), start_date date) ");
        sqLiteDatabase.execSQL("create table user_rewards" + "(id_reward integer references users(id), reward text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists bad_habits");
        sqLiteDatabase.execSQL("drop table if exists user_habits");
        sqLiteDatabase.execSQL("drop table if exists user_rewards");
    }

    public boolean insertUser (UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",userModel.getUsername());
        contentValues.put("email", userModel.getEmail());
        contentValues.put("password", userModel.getPassword());
        db.insert("users", null,contentValues);
        return true;
    }

    public boolean deleteUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        final String clause = "Delete FROM users WHERE id=" + (LoginActivity.currentUserId - 1);
        db.execSQL(clause);
        LoginActivity.currentUserId = -1;
        return false;
    }

    public ArrayList<UserModel> getAllUsers(){
        ArrayList<UserModel> arrayList = new ArrayList<>();
        final String select = "Select * FROM users";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(new UserModel(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }
}
