package com.example.lab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.dao.HoaDonDAO;
import com.example.lab.model.HoaDon;
import com.example.lab.model.HoaDonChiTiet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoaDonActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edNgayMua, edMaHoaDon;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        setTitle("THÊM HÓA ĐƠN");
        edNgayMua = (EditText)findViewById(R.id.edNgayMua);
        edMaHoaDon = (EditText)findViewById(R.id.edMaHoaDon);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(final Calendar calendar){
        edNgayMua.setText(sdf.format(calendar.getTime()));
    }

    public static class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year, month,day);
        }

    }

    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    public void ADDHoaDon(View view){
        hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
        try {
            if (validation()<0){
                Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
            }else{
                HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(),sdf.parse(edNgayMua.getText().toString()));
                if(hoaDonDAO.inserHoaDon(hoaDon)>0){
                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HoaDonActivity.this, HoaDonChiTiet.class);
                    Bundle b = new Bundle();
                    b.putString("MAHOADON", edMaHoaDon.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }

    public int validation(){
        if(edMaHoaDon.getText().toString().isEmpty() || edNgayMua.getText().toString().isEmpty()){
            return -1;
        }
        return 1;
    }
}
