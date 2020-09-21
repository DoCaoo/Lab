package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.dao.TheLoaiDAO;
import com.example.lab.model.TheLoai;

public class TheLoaiActivity extends AppCompatActivity {
    EditText edMaTheLoai, edTenTheLoai, edMoTa, edViTri;
    Button btnAdd, btnCancel, btnShow;
    TheLoaiDAO theLoaiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

        setTitle("THỂ LOẠI");
        edMaTheLoai = (EditText) findViewById(R.id.edMaTheLoai);
        edTenTheLoai = (EditText) findViewById(R.id.edTenTheLoai);
        edMoTa = (EditText) findViewById(R.id.edMoTa);
        edViTri = (EditText) findViewById(R.id.edViTri);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edMaTheLoai.setText(b.getString("MATHELOAI"));
            edTenTheLoai.setText(b.getString("TENTHELOAI"));
            edMoTa.setText(b.getString("MOTA"));
            edViTri.setText(b.getString("VITRI"));
        }
    }

    public void showTheLoai(View view) {
        finish();
    }

    public void addTheLoai(View view){
        theLoaiDAO = new TheLoaiDAO(TheLoaiActivity.this);

        try{
            if (validation()<0){
                Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
            }else {
                TheLoai theLoai = new TheLoai(edMaTheLoai.getText().toString(),edTenTheLoai.getText().toString(),edMoTa.getText().toString(),
                        Integer.parseInt(edViTri.getText().toString()));
                if (theLoaiDAO.inserTheLoai(theLoai)>0){
                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }

    public int validation(){
        int check = 1;
        if (edMaTheLoai.getText().length() == 0 || edTenTheLoai.getText().length() == 0 || edViTri.getText().length() == 0 ||
        edMoTa.getText().length() == 0) {
            check = -1;
        }
        return check;
    }
}
