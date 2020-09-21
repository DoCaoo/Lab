package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btnLogin, btnCancel;
    CheckBox chk;

    TextView tvResult;
    Button btnPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("ĐĂNG NHẬP");

        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        chk = findViewById(R.id.chkRememberPass);

        btnPHP = findViewById(R.id.btnPHP);
        tvResult = findViewById(R.id.tvResult);
    }

    //luu mk
    public void rememberUserPass(String u, String p, boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_LIFE",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//goi cong cu edit
        if (status == false){
            editor.clear();//truong hop ko luu ko tich vao checkbox
        }else{
            //luu du lieu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEBER",status);
        }
        editor.commit();
    }


    public int isLoginin(String u, String p){
        if(u.equals("admin")&& p.equals("admin")){
            return 1;//thanh cong
        }
        return -1;//ko thanh cong
    }

    String strU, strP;
    public void checkLogin(View view){
        strU = edUserName.getText().toString();
        strP = edPassword.getText().toString();
        if (strU.isEmpty() || strP.isEmpty()){
            Toast.makeText(getApplicationContext(),"Khong dc de trong U, P",Toast.LENGTH_LONG).show();
        }else{
            if (isLoginin(strU,strP)>0){
                Toast.makeText(getApplicationContext(),"Login thanh cong",Toast.LENGTH_LONG).show();
                //finish
                //goi den Main neu nhu Login thanh cong
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                }, 1500);
            }else{
                Toast.makeText(getApplicationContext(),"U hoac P ko dung",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void saveUP(View view){
        String u = edUserName.getText().toString();
        String p = edPassword.getText().toString();
        boolean status = chk.isChecked();
        rememberUserPass(u,p,status);
    }

    public void ReadData(View view) {
        HttpReadFromPHP httpReadFromPHP = new HttpReadFromPHP();
        httpReadFromPHP.execute();
    }

    public class HttpReadFromPHP extends AsyncTask<Void, Void, String>{
        //khai bao chuoiket noi API
        public static final String SERVER = "http:// 192.168.1.11/serverphp/readJSONapi.php";//cmd -> ipconfig

        //ket qua doc du lieu
        String result;
        String rline;

        //thuc hien lay du lieu tu API
        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(SERVER);//lay huoi ket noi tu server
                //mo ket noi
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();//thuc hien ket noi den server
                //doc du lieu
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while ((rline = bufferedReader.readLine())!= null){
                    stringBuilder.append(rline);
                }//tra ve result
                result = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        //tra ve ket qua
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dua du lieu ve client
            tvResult.setText(result);
        }
    }
}
