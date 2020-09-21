package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lab.adapter.CartAdapter;
import com.example.lab.dao.HoaDonChiTietDAO;
import com.example.lab.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class DsachHoaDonChiTietActivity extends AppCompatActivity {
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsach_hoa_don_chi_tiet);

        setTitle("HOÁ ĐƠN CHI TIẾT");
        lvCart = (ListView)findViewById(R.id.lvHoaDonChiTiet);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(DsachHoaDonChiTietActivity.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null){
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
        }
        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);
    }
}
