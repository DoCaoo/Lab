package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String strUserName, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Quản Lý Sách");
        /**if (checkLoginShap()<0) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }**/
    }

    /**public int checkLoginShap(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        boolean chk = pref.getBoolean("REMEMBER",false);
        if (chk){
            strUserName = pref.getString("USERNAME","");
            strPassword = pref.getString("PASSWORD","");
            return 1;
        }
        return -1;
    }**/
    public void viewNguoiDung(View v){
        Intent intent = new Intent(MainActivity.this,DsachNguoiDung.class);
        startActivity(intent);
    }

    public void viewTheLoai(View v){
        Intent intent = new Intent(MainActivity.this,DsachTheLoai.class);
        startActivity(intent);
    }

    public void viewListBookActivity(View v){
        Intent intent = new Intent(MainActivity.this,DsachBookActivity.class);
        startActivity(intent);
    }
    public void ViewListHoaDonActivity(View v){
         Intent intent = new Intent(MainActivity.this,DSachHoaDonActivity.class);
         startActivity(intent);
    }
    public void ViewTopSach(View v){
         Intent intent = new Intent(MainActivity.this,LuotSachBanChayActivity.class);
         startActivity(intent);
    }
    public void ViewThongKeActivity(View v){
        Intent intent = new Intent(MainActivity.this,ThongKeDoanhThuActivity.class);
        startActivity(intent);
    }
}
