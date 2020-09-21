package com.example.lab.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lab.database.DatabaseHelper;
import com.example.lab.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String SQL_NGUOI_DUNG = "CREATE TABLE NguoiDung (userName text primary key, userPass text, phone text, hoTen text);";
    public static final String TABLE_NAME = "NguoiDung";
    public static final String TAG = "NguoiDungDAO";


    //Phuong thuc khoi tao db
    public NguoiDungDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserNguoiDung(NguoiDung n){
        ContentValues values = new ContentValues();
        values.put("userName", n.getUserName());
        values.put("userPass", n.getUserPass());
        values.put("phone", n.getPhone());
        values.put("hoTen", n.getHoTen());
        try {
            if(db.insert(TABLE_NAME, null, values)==-1){//insert ko thanh cong
                return -1;
            }
        } catch (Exception ex){
            Log.e(TAG,ex.toString());

        }
        return 1;
    }

    //Getall
    public List<NguoiDung> getAllNguoiDung(){
        List<NguoiDung> dsNguoiDung = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,
                null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            NguoiDung ee = new NguoiDung();
            ee.setUserName(c.getString(0));
            ee.setUserPass(c.getString(1));
            ee.setPhone(c.getString(2));
            ee.setHoTen(c.getString(3));
            dsNguoiDung.add(ee);
            Log.d("//=====",ee.toString());
            c.moveToNext();
        }
        c.close();
        return dsNguoiDung;

    }

    //delete
    public int deleteNguoiDungByID(String userName){
        int result = db.delete(TABLE_NAME,"userName=?",new String[]{userName});
        if (result == 0){
            return  -1; //xoa that bai
        }
        return 1; //xoa thanh cong
    }

    public int updateNguoiDung(NguoiDung nd){
        ContentValues values = new ContentValues();
        values.put("userName",nd.getUserName());
        values.put("userPass",nd.getUserPass());
        values.put("phone",nd.getPhone());
        values.put("hoTen",nd.getHoTen());
        int result = db.update(TABLE_NAME,values,"userName=?",new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //update
    public int updateInfoNguoiDung(String userName, String phone, String name){
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        values.put("hoTen",name);
        int result = db.delete(TABLE_NAME,"userName=?",new String[]{userName});
        if (result == 0){
            return  -1; // update that bai
        }
        return 1; // update thanh cong
    }

    public int changePasswordNguoiDung(NguoiDung nd) {
        ContentValues values = new ContentValues();
        values.put("userName",nd.getUserName());
        values.put("userPass",nd.getUserPass());
        int result = db.update(TABLE_NAME,values,"userName=?",new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //check Login
    public int checkLogin(String userName, String userPass){
        int result = db.delete(TABLE_NAME,"userName=? AND userPass=?",new String[]{userName,userPass});
        if (result == 0){
            return -1;
        }
        return 1;
    }
}
