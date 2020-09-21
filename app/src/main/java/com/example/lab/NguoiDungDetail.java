package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.dao.NguoiDungDAO;

public class NguoiDungDetail extends AppCompatActivity {
    EditText edFullName, edPhone;
    NguoiDungDAO nguoiDungDAO;
    String userName, fullname, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung_detail);

        setTitle("CHI TIẾT NGƯỜI DÙNG");

        edFullName = (EditText) findViewById(R.id.edFullName);
        edPhone = (EditText) findViewById(R.id.edPhone);

        nguoiDungDAO = new NguoiDungDAO(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        fullname = b.getString("FULLNAME");
        phone = b.getString("phone");
        userName = b.getString("userName");
        edFullName.setText(fullname);
        edPhone.setText(phone);

    }

    public void updateUser(View view) {
            if (nguoiDungDAO.updateInfoNguoiDung(userName, edPhone.getText().toString(), edFullName.getText().toString()) > 0) {
                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Lưu Thất bại", Toast.LENGTH_LONG).show();
            }

        }


    public void Huy(View view){
        finish();
    }
}
