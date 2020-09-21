package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lab.adapter.NguoiDungAdapter;
import com.example.lab.adapter.NguoiDungRecAdapter;
import com.example.lab.dao.NguoiDungDAO;
import com.example.lab.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class DsachNguoiDung extends AppCompatActivity {
    ListView lvNguoiDung;
    public static List<NguoiDung> dsNguoiDung = new ArrayList<>();
    NguoiDungAdapter adapter = null;
    NguoiDungDAO nguoiDungDAO;
    //RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("NGƯỜI DÙNG");
        setContentView(R.layout.activity_dsach_nguoi_dung);

        lvNguoiDung = findViewById(R.id.lvNguoiDung);
        nguoiDungDAO = new NguoiDungDAO(DsachNguoiDung.this);
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();

        adapter = new NguoiDungAdapter(dsNguoiDung,this);
        lvNguoiDung.setAdapter(adapter);
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(DsachNguoiDung.this, NguoiDungDetail.class);
                    Bundle b = new Bundle();
                    b.putString("userName", dsNguoiDung.get(position).getUserName());
                    b.putString("phone", dsNguoiDung.get(position).getPhone());
                    b.putString("FULLNAME", dsNguoiDung.get(position).getHoTen());
                    intent.putExtras(b);
                    startActivity(intent);
                }
                catch (Exception e){
                    Log.e("ERR: ",e.toString());
                }
            }
        });
        lvNguoiDung.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        //  recyclerView = findViewById(R.id.recyclerView);
      /*  try {
            nguoiDungDAO = new NguoiDungDAO(ListNguoiDungActivity.this);
            dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            NguoiDungRecyclerAdapter adapter = new NguoiDungRecyclerAdapter(ListNguoiDungActivity.this, dsNguoiDung);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsNguoiDung.clear();
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter.changeDataset(nguoiDungDAO.getAllNguoiDung());

    }
    public void startThemNguoiDung(View view) {
        Intent intent = new Intent(this, NguoiDungActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(DsachNguoiDung.this, NguoiDungActivity.class);
                startActivity(intent);
                return (true);
            case R.id.changePass:
                Intent intent1 = new Intent(DsachNguoiDung.this, ChangePasswordActivity.class);
                startActivity(intent1);
                return (true);
            case R.id.logOut:
                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                //xoa tinh trang luu tru truoc do
                edit.clear();
                edit.commit();
                intent = new Intent(DsachNguoiDung.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
