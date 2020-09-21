package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lab.dao.HoaDonChiTietDAO;

public class ThongKeDoanhThuActivity extends AppCompatActivity {
    TextView tvNgay,tvThang, tvNam;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu);

        setTitle("DOANH THU");
        tvNgay = (TextView)findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView)findViewById(R.id.tvThongKeThang);
        tvNam = (TextView)findViewById(R.id.tvThongKeNam);

        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
        try {
            tvNgay.setText("Hôm nay: " + hoaDonChiTietDAO.getDoanhThuTheoNgay());
            tvThang.setText("Tháng này: " + hoaDonChiTietDAO.getDoanhThuTheoThang());
            tvNam.setText("Năm này: " + hoaDonChiTietDAO.getDoanhThuTheoNam());
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }
}
