package com.example.badhabits.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.badhabits.activities.LoginActivity;
import com.example.badhabits.models.BadHabitModel;
import com.example.badhabits.models.UserModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "badhabits.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users" + "(id integer primary key AUTOINCREMENT, username text, email text, password text)");
        sqLiteDatabase.execSQL("create table user_habits" + "(id_user integer references users(id), habit text, start_date date) ");
        sqLiteDatabase.execSQL("create table user_rewards" + "(id_reward integer references users(id), reward text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists user_habits");
        sqLiteDatabase.execSQL("drop table if exists user_rewards");
    }

    public boolean insertUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userModel.getUsername());
        contentValues.put("email", userModel.getEmail());
        contentValues.put("password", userModel.getPassword());
        db.insert("users", null, contentValues);
        return true;
    }

    public boolean insertHabit(int userId, String habit, LocalDate date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", userId);
        contentValues.put("habit", habit);
        contentValues.put("start_date", String.valueOf(date));
        db.insert("user_habits", null, contentValues);
        return true;
    }

    public boolean deleteHabit(BadHabitModel badHabitModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user_habits", "habit = ? and id_user = ?", new String[]{badHabitModel.getHabit(), String.valueOf(badHabitModel.getUserId())}) > 0;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> arrayList = new ArrayList<>();
        final String select = "Select * FROM users";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BadHabitModel> getAllHabits() {
        ArrayList<BadHabitModel> arrayList = new ArrayList<>();
        final String select = "Select * FROM user_habits";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocalDate localDate = LocalDate.parse(cursor.getString(2));
            arrayList.add(new BadHabitModel(cursor.getInt(0), cursor.getString(1), localDate));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public int updateUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (username != null) {
            contentValues.put("username", username);
            return db.update("users", contentValues, "id = ?", new String[]{Integer.toString(LoginActivity.currentUserId)});
        }
        if (email != null) {
            contentValues.put("email", email);
            return db.update("users", contentValues, "id = ?", new String[]{Integer.toString(LoginActivity.currentUserId)});
        }
        if (password != null) {
            contentValues.put("password", password);
            return db.update("users", contentValues, "id = ?", new String[]{Integer.toString(LoginActivity.currentUserId)});
        }

        return 0;
    }
}
