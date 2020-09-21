package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.adapter.CartAdapter;
import com.example.lab.dao.HoaDonChiTietDAO;
import com.example.lab.dao.SachDAO;
import com.example.lab.model.HoaDon;
import com.example.lab.model.HoaDonChiTiet;
import com.example.lab.model.Sach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonChiTietActivity extends AppCompatActivity {
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SachDAO sachDAO;

    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    double thanhTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        setTitle("CHI TIẾT HÓA ĐƠN");
        edMaSach = (EditText)findViewById(R.id.edMaSach);
        edMaHoaDon = (EditText)findViewById(R.id.edMaHoaDon);
        edSoLuong = (EditText)findViewById(R.id.edSoLuongMua);
        lvCart = (ListView)findViewById(R.id.lvCart);
        tvThanhTien = (TextView)findViewById(R.id.tvThanhTien);

        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null){
            edMaHoaDon.setText(b.getString("MAHOADON"));
        }
    }

    public void ADDHoaDonCHITIET(View view){
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonChiTietActivity.this);
        sachDAO = new SachDAO(HoaDonChiTietActivity.this);

        try {
            if (validation()<0){
                Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
            }else {
                Sach sach = sachDAO.getSachByID(edMaSach.getText().toString());
                if (sach != null) {
                    int pos = checkMaSach(dsHDCT, edMaSach.getText().toString());
                    HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(), new Date());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hoaDon, sach, Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos >= 0) {
                        int soluong = dsHDCT.get(pos).getSoLuongMua();
                        hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        dsHDCT.set(pos, hoaDonChiTiet);
                    } else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);
                } else {
                    Toast.makeText(getApplicationContext(), "Mã sách ko tồn tại", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }

    public void thanhToanHoaDon(View view){
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonChiTietActivity.this);
        //tinh tien
        thanhTien = 0;
        try {
            for (HoaDonChiTiet hd: dsHDCT){
                hoaDonChiTietDAO.inserHoaDonChiTiet(hd);
                thanhTien = thanhTien + hd.getSoLuongMua() * hd.getSach().getGiaBia();
            }
            tvThanhTien.setText("Tổng tiền: "+thanhTien);
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }

    public int checkMaSach(List<HoaDonChiTiet> lsHD, String maSach){
        int pos = -1;
        for (int i = 0; i< lsHD.size(); i++){
            HoaDonChiTiet hd = lsHD.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    public int validation(){
        if (edMaSach.getText().toString().isEmpty() || edSoLuong.getText().toString().isEmpty()){
            return -1;
        }
        return 1;
    }
}
