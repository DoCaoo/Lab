package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.dao.NguoiDungDAO;
import com.example.lab.model.NguoiDung;

public class NguoiDungActivity extends AppCompatActivity {
    Button btnThemNguoiDung;
    EditText edUser, edPass,edRePass, edPhone, edFullName;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        setTitle("THÊM NGƯỜI DÙNG");
        btnThemNguoiDung = (Button) findViewById(R.id.btnAddUser);
        edUser = (EditText) findViewById(R.id.edUserName);
        edPass = (EditText) findViewById(R.id.edPassword);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edFullName = (EditText) findViewById(R.id.edFullName);
        edRePass = (EditText) findViewById(R.id.edRePassword);

        //lay ve itent
        /**try {
            Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getBundleExtra("bun");
            edUser.setText(bundle.getString("userName_key"));
            edPass.setText(bundle.getString("userPass_key"));
            edPhone.setText(bundle.getString("phone_key"));
            edFullName.setText(bundle.getString("hoTen_key"));
        }
    }catch (Exception e){
            e.printStackTrace();
        }**/
    }

    public void addUser(View view){
        nguoiDungDAO = new NguoiDungDAO(NguoiDungActivity.this);
        //lay du lieu tu form
        NguoiDung nguoidung = new NguoiDung(edUser.getText().toString(),edPass.getText().toString(),edPhone.getText().toString(),edFullName.getText().toString());
        try {
            if (validateForm() > 0) {
                if (nguoiDungDAO.inserNguoiDung(nguoidung) > 0) {
                    Toast.makeText(getApplicationContext(), "Them thanh cong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Them that bai", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Log.e("Error", ex.toString());
        }
    }

    //Validate
    public int validateForm(){
        int check = 1;
        if (edUser.getText().length() == 0 || edFullName.getText().length() == 0 || edPhone.getText().length() == 0
                || edPass.getText().length() == 0 || edRePass.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
            check = -1;
        }else {
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!pass.equals(rePass)){
                Toast.makeText(getApplicationContext(),"Mật khẩu ko trùng khớp",Toast.LENGTH_LONG).show();
                check = -1;
            }
        }
        return check;
    }

    /**public void updateUser(View view){
        nguoiDungDAO = new NguoiDungDAO(NguoiDungActivity.this);
        NguoiDung nguoiDung = new NguoiDung(edUser.getText().toString(), edPass.getText().toString(),edPhone.getText().toString(), edFullName.getText().toString());
        if (nguoiDungDAO.updateInfoNguoiDung(edUser.getText().toString(),edPhone.getText().toString(), edFullName.getText().toString())==1){
            Toast.makeText(getApplicationContext(),"Update thanh cong",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Update that bai",Toast.LENGTH_LONG).show();
        }
}**/

    public void showUsers(View view) {
        finish();
    }
}
