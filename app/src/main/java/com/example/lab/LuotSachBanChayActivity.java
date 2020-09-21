package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab.adapter.BookAdapter;
import com.example.lab.dao.SachDAO;
import com.example.lab.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class LuotSachBanChayActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;
    EditText edThang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luot_sach_ban_chay);

        setTitle("TOP 10 SÁCH BÁN CHẠY");
        lvBook = (ListView)findViewById(R.id.lvBookTop);
        edThang = (EditText)findViewById(R.id.edThang);
    }

    public void VIEW_SACH_TOP_10 (View view) {
        try {
        if (Integer.parseInt(edThang.getText().toString()) > 13 || Integer.parseInt(edThang.getText().toString()) < 0) {
                Toast.makeText(getApplicationContext(), "Ko đúng định dạng tháng (1-12)", Toast.LENGTH_LONG).show();
        } else {
                sachDAO = new SachDAO(LuotSachBanChayActivity.this);
                dsSach = sachDAO.getSachTop10(edThang.getText().toString());

                adapter = new BookAdapter(this, dsSach);
                lvBook.setAdapter(adapter);
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }
}
