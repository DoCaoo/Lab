package com.example.lab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab.dao.HoaDonChiTietDAO;
import com.example.lab.dao.HoaDonDAO;
import com.example.lab.dao.NguoiDungDAO;
import com.example.lab.dao.SachDAO;
import com.example.lab.dao.TheLoaiDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbBookManager";
    public static final int VERSION = 1;

    //tao DB
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,  VERSION);
    }

    //tao Bang
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NguoiDungDAO.SQL_NGUOI_DUNG);
        db.execSQL(TheLoaiDAO.SQL_THE_LOAI);
        db.execSQL(SachDAO.SQL_SACH);
        db.execSQL(HoaDonDAO.SQL_HOA_DON);
        db.execSQL(HoaDonChiTietDAO.SQL_HOA_DON_CHI_TIET);
    }

    //UPGRADE cap nhap bang ms
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NguoiDungDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TheLoaiDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SachDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HoaDonDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HoaDonChiTietDAO.TABLE_NAME);
        onCreate(db);
    }
}
